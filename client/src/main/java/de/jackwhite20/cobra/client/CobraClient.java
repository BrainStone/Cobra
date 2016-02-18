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

import de.jackwhite20.cobra.shared.http.Body;
import de.jackwhite20.cobra.shared.http.Headers;
import de.jackwhite20.cobra.shared.http.Response;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by JackWhite20 on 17.02.2016.
 */
public interface CobraClient {

    Response post(URL url, Body body, Headers headers) throws IOException;

    Response post(URL url, Proxy proxy, Body body, Headers headers) throws IOException;

    Response put(URL url, Headers headers) throws IOException;

    Response put(URL url, Proxy proxy, Headers headers) throws IOException;

    Response delete(URL url, Headers headers) throws IOException;

    Response delete(URL url, Proxy proxy, Headers headers) throws IOException;

    Response get(URL url, Proxy proxy, Headers headers) throws IOException;

    Response get(URL url, Headers headers) throws IOException;

    Response download(URL url, Headers headers, String folderToSaveTo) throws IOException;

    Response download(URL url, Proxy proxy, Headers headers, String folderToSaveTo) throws IOException;
}
