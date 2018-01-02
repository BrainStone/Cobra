/*
 * Copyright (c) 2018 "JackWhite20"
 *
 * This file is part of Cobra.
 *
 * Cobra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jackwhite20.cobra.server.http;

import de.jackwhite20.cobra.server.filter.FilteredRequest;
import de.jackwhite20.cobra.server.impl.CobraServerImpl;
import de.jackwhite20.cobra.shared.RequestMethod;
import de.jackwhite20.cobra.shared.http.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class ConnectionHandler implements Runnable {

    private static final byte[] NEW_LINE = "\n".getBytes();

    private Socket socket;

    private CobraServerImpl cobraServer;

    private HttpReader httpReader;

    private OutputStream outputStream;

    public ConnectionHandler(Socket socket, CobraServerImpl cobraServer) {
        this.socket = socket;
        this.cobraServer = cobraServer;
        try {
            this.httpReader = new HttpReader(socket.getInputStream(), "UTF-8");
            this.outputStream = socket.getOutputStream();
        } catch (IOException ignore) {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    @Override
    public void run() {
        try {
            StringBuilder lines = new StringBuilder(260);

            int c;
            while ((c = httpReader.read()) != -1) {
                if ((char) c == '\r') {
                    lines.append("\r");

                    char next = (char) httpReader.read();
                    lines.append(next);
                    if (next == '\n') {
                        next = (char) httpReader.read();
                        lines.append(next);
                        if (next == '\r') {
                            next = (char) httpReader.read();
                            lines.append(next);
                            if (next == '\n') {
                                // POST data start, break
                                break;
                            }
                        }
                    }
                } else {
                    lines.append((char) c);
                }
            }

            String request = lines.toString();

            if (!request.isEmpty()) {
                Request httpRequest = new Request(request);

                if (httpRequest.method() == RequestMethod.POST) {
                    int length = Integer.parseInt(httpRequest.header("Content-Length"));
                    if (length > 0) {
                        httpReader.buffer().position(httpReader.buffer().position() - 1);

                        byte[] bytes = new byte[length];
                        httpReader.buffer().get(bytes);

                        // Set body
                        httpRequest.body(bytes);
                        // Set POST data from the body content
                        httpRequest.postData(httpRequest.body().content());
                    }
                }

                if (httpRequest.location().isEmpty()) {
                    httpRequest.location = "*";
                }

                // TODO: 04.02.2016
                FilteredRequest filteredRequest = new FilteredRequest(httpRequest);
                cobraServer.filter(filteredRequest);

                Response response = (filteredRequest.response() == null) ? cobraServer.handleRequest(httpRequest) : filteredRequest.response();
                response.addDefaultHeaders();
                outputStream.write((Response.VERSION + " " + response.responseCode() + " " + response.responseReason()).getBytes());
                outputStream.write(NEW_LINE);
                for (Map.Entry<String, String> header : response.headers().headers().entrySet()) {
                    outputStream.write((header.getKey() + ": " + header.getValue()).getBytes());
                    outputStream.write(NEW_LINE);
                }
                outputStream.write(NEW_LINE);
                if (response.body() != null) {
                    outputStream.write(response.body().bytes());
                }
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {}
        }
    }
}
