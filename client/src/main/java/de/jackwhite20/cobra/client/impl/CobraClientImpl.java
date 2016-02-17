/*
 * Copyright (c) 2016 "JackWhite20"
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

package de.jackwhite20.cobra.client.impl;

import de.jackwhite20.cobra.client.CobraClient;
import de.jackwhite20.cobra.shared.Status;
import de.jackwhite20.cobra.shared.http.Body;
import de.jackwhite20.cobra.shared.http.Headers;
import de.jackwhite20.cobra.shared.http.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by JackWhite20 on 01.02.2016.
 */
public class CobraClientImpl implements CobraClient {

    private int connectTimeout;

    public CobraClientImpl() {

        this(2000);
    }

    public CobraClientImpl(int connectTimeout) {

        this.connectTimeout = connectTimeout;
    }

    private Headers filterHeaders(Map<String, List<String>> headers) {

        Headers headersReturn = Headers.empty();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            headersReturn.header(entry.getKey(), String.join(";", entry.getValue()));
        }

        return headersReturn;
    }

    @Override
    public Response post(URL url, Body body, Headers headers) throws IOException {

        return post(url, Proxy.NO_PROXY, body, headers);
    }

    @Override
    public Response post(URL url, Proxy proxy, Body body, Headers headers) throws IOException {

        if (proxy == null)
            proxy = Proxy.NO_PROXY;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod("POST");

        for (Map.Entry<String, String> entry : headers.headers().entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.write(body.bytes());
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.isEmpty() || line.contains(":"))
                continue;

            response.append(line);
            response.append('\n');
        }

        reader.close();

        return Response.status(Status.valueOf(connection.getResponseCode())).headers(filterHeaders(connection.getHeaderFields())).content(new Body(response.toString())).build();
    }

    @Override
    public Response get(URL url, Headers headers) throws IOException {

        return get(url, Proxy.NO_PROXY, headers);
    }

    @Override
    public Response get(URL url, Proxy proxy, Headers headers) throws IOException {

        if (proxy == null)
            proxy = Proxy.NO_PROXY;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod("GET");

        for (Map.Entry<String, String> entry : headers.headers().entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.isEmpty() || line.contains(":"))
                continue;

            response.append(line);
            response.append('\n');
        }

        reader.close();

        return Response.status(Status.valueOf(connection.getResponseCode())).headers(filterHeaders(connection.getHeaderFields())).content(new Body(response.toString())).build();
    }

    public int connectTimeout() {

        return connectTimeout;
    }
}
