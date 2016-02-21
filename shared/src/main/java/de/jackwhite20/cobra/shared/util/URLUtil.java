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

package de.jackwhite20.cobra.shared.util;

import de.jackwhite20.cobra.shared.http.Headers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by JackWhite20 on 17.02.2016.
 */
public class URLUtil {

    public static final int CHUNK_SIZE = 2048;

    public static Headers filterHeaders(Map<String, List<String>> headers) {

        Headers headersReturn = Headers.empty();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            headersReturn.header(entry.getKey(), String.join(";", entry.getValue()));
        }

        return headersReturn;
    }

    public static byte[] readResponse(HttpURLConnection connection) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            byte[] chunk = new byte[CHUNK_SIZE];

            int i;
            while ((i = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                inputStream.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static HttpURLConnection connection(URL url, Proxy proxy, int connectTimeout, Headers headers, String method) throws IOException {

        if (proxy == null)
            proxy = Proxy.NO_PROXY;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod(method);
        connection.setRequestProperty("User-Agent", "Cobra v0.1");

        for (Map.Entry<String, String> entry : headers.headers().entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        return connection;
    }
}
