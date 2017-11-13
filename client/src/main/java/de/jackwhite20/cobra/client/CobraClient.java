/*
 * Copyright (c) 2017 "JackWhite20"
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

import com.google.gson.Gson;
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

    /**
     * Executes a POST request to the given URL with the given body and headers.
     *
     * @param url The URL to request.
     * @param body The body to post.
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(URL url, Body body, Headers headers) throws IOException;

    /**
     * Executes a POST request to the given relative path with the given body and headers.
     *
     * The relative path will be added to the base URL when CobraClientFactory.create(String baseUrl) was used.
     * Given the following values:
     * baseUrl = https://some.api/
     * relativePath = users/add
     *
     * The following URL will be requested: https://some.api/users/add
     *
     * @param relativePath The relative path to request.
     * @param body The body to post.
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(String relativePath, Body body, Headers headers) throws IOException;

    /**
     * Executes a POST request to the URL, trough the proxy and with the given body and headers.
     *
     * @param url The URL to request.
     * @param proxy The proxy to use.
     * @param body The body to post.
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(URL url, Proxy proxy, Body body, Headers headers) throws IOException;

    /**
     * Executes a POST request to the relative path, through the proxy with the given body and headers.
     *
     * The relative path will be added to the base URL when CobraClientFactory.create(String baseUrl) was used.
     * Given the following values:
     * baseUrl = https://some.api/
     * relativePath = users/add
     *
     * The following URL will be requested: https://some.api/users/add
     *
     * @param relativePath The relative path to request.
     * @param proxy The proxy to use.
     * @param body The body to post.
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(String relativePath, Proxy proxy, Body body, Headers headers) throws IOException;

    /**
     * Executes a POST request to the URL with the given object, which will be converted to JSON, and headers.
     *
     * @param url The URL to request.
     * @param body The body object to post (will be converted to JSON).
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(URL url, Object body, Headers headers) throws IOException;

    /**
     * Executes a POST request to the relative path with the given object, which will be converted to JSON, and headers.
     *
     * The relative path will be added to the base URL when CobraClientFactory.create(String baseUrl) was used.
     * Given the following values:
     * baseUrl = https://some.api/
     * relativePath = users/add
     *
     * The following URL will be requested: https://some.api/users/add
     *
     * @param relativePath The relative path to request.
     * @param body The body object to post (will be converted to JSON).
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(String relativePath, Object body, Headers headers) throws IOException;

    /**
     * Executes a POST request to the URL, through the proxy, with the given object, which will be converted to JSON, and headers.
     *
     * @param url The URL to request.
     * @param proxy The proxy to use.
     * @param body The body object to post (will be converted to JSON).
     * @param headers The headers for the request.
     * @return The response containing the status, response headers and body.
     * @throws IOException If something went wrong during the request.
     */
    Response post(URL url, Proxy proxy, Object body, Headers headers) throws IOException;

    Response post(String relativePath, Proxy proxy, Object body, Headers headers) throws IOException;

    Response put(URL url, Headers headers) throws IOException;

    Response put(String relativePath, Headers headers) throws IOException;

    Response put(URL url, Proxy proxy, Headers headers) throws IOException;

    Response put(String relativePath, Proxy proxy, Headers headers) throws IOException;

    Response patch(URL url, Headers headers) throws IOException;

    Response patch(String relativePath, Headers headers) throws IOException;

    Response patch(URL url, Proxy proxy, Headers headers) throws IOException;

    Response patch(String relativePath, Proxy proxy, Headers headers) throws IOException;

    Response delete(URL url, Headers headers) throws IOException;

    Response delete(String relativePath, Headers headers) throws IOException;

    Response delete(URL url, Proxy proxy, Headers headers) throws IOException;

    Response delete(String relativePath, Proxy proxy, Headers headers) throws IOException;

    Response get(URL url, Proxy proxy, Headers headers) throws IOException;

    Response get(String relativePath, Proxy proxy, Headers headers) throws IOException;

    Response get(URL url, Headers headers) throws IOException;

    Response get(String relativePath, Headers headers) throws IOException;

    <T> T get(URL url, Proxy proxy, Headers headers, Class<T> clazz) throws IOException;

    <T> T get(String relativePath, Proxy proxy, Headers headers, Class<T> clazz) throws IOException;

    <T> T get(URL url, Headers headers, Class<T> clazz) throws IOException;

    <T> T get(String relativePath, Headers headers, Class<T> clazz) throws IOException;

    Response download(URL url, Headers headers, String folderToSaveTo) throws IOException;

    Response download(String relativePath, Headers headers, String folderToSaveTo) throws IOException;

    Response download(URL url, Proxy proxy, Headers headers, String folderToSaveTo) throws IOException;

    Response download(String relativePath, Proxy proxy, Headers headers, String folderToSaveTo) throws IOException;

    /**
     * Returns the connect timeout milliseconds used by the cobra client.
     *
     * @return The connect timeout in milliseconds.
     */
    int connectTimeout();

    /**
     * Returns the GSON instance used by the cobra client.
     *
     * @return The GSON instance.
     */
    Gson gson();
}
