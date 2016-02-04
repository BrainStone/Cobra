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

package de.jackwhite20.cobra.client;

import de.jackwhite20.cobra.shared.ContentType;
import de.jackwhite20.cobra.shared.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JackWhite20 on 01.02.2016.
 */
public class CobraClient {

    private String host;

    private int port;

    private String rootPath;

    private long timeout;

    public CobraClient(String host, int port, String rootPath, long timeout) {

        this.host = host;
        this.port = port;
        this.rootPath = rootPath;
        this.timeout = timeout;
    }

    public CobraClient(String host, int port, String rootPath) {

        this(host, port, rootPath, 1000);
    }

    public String get(String path, RequestMethod requestMethod, ContentType contentType) {

        try {
            URL url = new URL(host + port + rootPath + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod.name());
            conn.setRequestProperty("Accept", contentType.type());
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n\r");
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String host() {

        return host;
    }

    public int port() {

        return port;
    }

    public String rootPath() {

        return rootPath;
    }

    public long timeout() {

        return timeout;
    }
}
