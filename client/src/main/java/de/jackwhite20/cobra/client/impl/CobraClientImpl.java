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

import com.google.common.base.Preconditions;
import de.jackwhite20.cobra.client.CobraClient;
import de.jackwhite20.cobra.shared.Status;
import de.jackwhite20.cobra.shared.http.Body;
import de.jackwhite20.cobra.shared.http.Headers;
import de.jackwhite20.cobra.shared.http.Response;
import de.jackwhite20.cobra.shared.util.URLUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

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

    @Override
    public Response post(URL url, Body body, Headers headers) throws IOException {

        return post(url, null, body, headers);
    }

    @Override
    public Response post(URL url, Proxy proxy, Body body, Headers headers) throws IOException {

        if(proxy == null)
            proxy = Proxy.NO_PROXY;

        Preconditions.checkNotNull(url, "url cannot be null");
        Preconditions.checkNotNull(body, "body cannot be null");
        Preconditions.checkNotNull(headers, "headers cannot be null");

        HttpURLConnection connection = URLUtil.connection(url, proxy, connectTimeout, headers, "POST");

        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.write(body.bytes());
        writer.flush();
        writer.close();

        byte[] response = URLUtil.readResponse(connection);

        return new Response(Status.valueOf(connection.getResponseCode()), URLUtil.filterHeaders(connection.getHeaderFields()), Body.of(response));
    }

    @Override
    public Response put(URL url, Headers headers) throws IOException {

        return put(url, null, headers);
    }

    @Override
    public Response put(URL url, Proxy proxy, Headers headers) throws IOException {

        return request(url, proxy, headers, "PUT");
    }

    @Override
    public Response delete(URL url, Headers headers) throws IOException {

        return delete(url, null, headers);
    }

    @Override
    public Response delete(URL url, Proxy proxy, Headers headers) throws IOException {

        return request(url, proxy, headers, "DELETE");
    }

    @Override
    public Response get(URL url, Headers headers) throws IOException {

        return get(url, null, headers);
    }

    @Override
    public Response get(URL url, Proxy proxy, Headers headers) throws IOException {

        return request(url, proxy, headers, "GET");
    }

    @Override
    public Response download(URL url, Headers headers, String folderToSaveTo) throws IOException {

        return download(url, null, headers, folderToSaveTo);
    }

    @Override
    public Response download(URL url, Proxy proxy, Headers headers, String folderToSaveTo) throws IOException {

        Preconditions.checkNotNull(url, "url cannot be null");
        Preconditions.checkNotNull(headers, "headers cannot be null");
        Preconditions.checkNotNull(folderToSaveTo, "folderToSaveTo cannot be null");
        Preconditions.checkArgument(!folderToSaveTo.isEmpty(), "folderToSaveTo cannot be empty");

        HttpURLConnection connection = URLUtil.connection(url, proxy, connectTimeout, headers, "GET");

        String disposition = connection.getHeaderField("Content-Disposition");

        if(disposition == null)
            throw new IllegalArgumentException("no 'Content-Disposition' header present");

        String fileName = null;

        int index = disposition.indexOf("filename=");
        if (index > 0) {
            fileName = disposition.substring(index + 10, disposition.length() - 1);
        }

        if(fileName == null)
            throw new IllegalStateException("unable to get the file name from the disposition header");

        FileOutputStream fileOutputStream = new FileOutputStream(folderToSaveTo + File.separator + fileName);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            byte[] chunk = new byte[URLUtil.CHUNK_SIZE];

            int i;
            while ( (i = inputStream.read(chunk)) > 0 ) {
                fileOutputStream.write(chunk, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileOutputStream.close();

            if(inputStream != null) {
                inputStream.close();
            }
        }

        return new Response(Status.valueOf(connection.getResponseCode()), URLUtil.filterHeaders(connection.getHeaderFields()), Body.of("".getBytes()));
    }

    private Response request(URL url, Proxy proxy, Headers headers, String method) throws IOException {

        Preconditions.checkNotNull(url, "url cannot be null");
        Preconditions.checkNotNull(headers, "headers cannot be null");

        HttpURLConnection connection = URLUtil.connection(url, proxy, connectTimeout, headers, method);

        byte[] response = URLUtil.readResponse(connection);

        return new Response(Status.valueOf(connection.getResponseCode()), URLUtil.filterHeaders(connection.getHeaderFields()), Body.of(response));
    }

    public int connectTimeout() {

        return connectTimeout;
    }
}
