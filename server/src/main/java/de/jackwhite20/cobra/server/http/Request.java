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

package de.jackwhite20.cobra.server.http;

import de.jackwhite20.cobra.shared.RequestMethod;
import de.jackwhite20.cobra.shared.http.Body;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class Request {

    private final String raw;

    private RequestMethod method;

    protected String location;

    private String version;

    private Map<String, String> headers = new HashMap<>();

    private Body body = Body.of("");

    private Map<String, String> postForm = new HashMap<>();

    public Request(String raw) {
        this.raw = raw;

        parse();
    }

    private void parse() {
        StringTokenizer tokenizer = new StringTokenizer(raw);

        method = RequestMethod.valueOf(tokenizer.nextToken());
        location = tokenizer.nextToken();
        // Remove last slash
        if (location.endsWith("/")) {
            location = location.substring(0, location.lastIndexOf('/'));
        }
        version = tokenizer.nextToken();

        String[] lines = raw.split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] keyVal = lines[i].split(":", 2);

            if (keyVal.length == 2) {
                headers.put(keyVal[0].trim(), keyVal[1].trim());
            }
        }
    }

    public RequestMethod method() {
        return method;
    }

    public String location() {
        return location;
    }

    public String version() {
        return version;
    }

    public String header(String key) {
        return headers.get(key);
    }

    void postData(String data) {
        String[] splitted = data.split("&");
        for (String aSplitted : splitted) {
            String[] keyVal = aSplitted.split("=");
            if (keyVal.length == 2) {
                postForm.put(keyVal[0], keyVal[1]);
            }
        }
    }

    protected void body(byte[] bytes) {
        body = Body.of(bytes);
    }

    /**
     * Returns the post form value from the given key.
     *
     * @param key The key.
     * @return The form value from the key.
     */
    public String postForm(String key) {
        return postForm.get(key);
    }

    /**
     * Returns the post data from the request.
     *
     * @return The post data as form key value data.
     */
    public Map<String, String> postForm() {
        return Collections.unmodifiableMap(postForm);
    }

    /**
     * Get the raw body of this request.
     *
     * @return The raw body.
     */
    public Body body() {
        return body;
    }

    /**
     * Returns the headers from the request.
     *
     * @return The headers from the request.
     */
    public Map<String, String> headers() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public String toString() {
        return "HTTPRequest{" +
                ", method=" + method +
                ", location='" + location + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                '}';
    }
}
