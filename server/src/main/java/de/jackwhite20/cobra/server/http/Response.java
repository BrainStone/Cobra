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

package de.jackwhite20.cobra.server.http;

import de.jackwhite20.cobra.shared.Status;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {

    private String version = "HTTP/1.1";

    private int responseCode = 200;

    private String responseReason = "OK";

    private Map<String, String> headers = new LinkedHashMap<>();

    private byte[] content;

    public void addDefaultHeaders() {

        headers.put("Date", new Date().toString());
        headers.put("Server", "Cobra 0.1");
        headers.put("Connection", "close");
        //headers.put("Content-Type", "text/html; charset=utf-8");
        if(content != null)
            headers.put("Content-Length", Integer.toString(content.length));
    }

    public String version() {

        return version;
    }

    public int responseCode() {

        return responseCode;
    }

    public String responseReason() {

        return responseReason;
    }

    public Map<String, String> headers() {

        return headers;
    }

    public byte[] content() {

        return content;
    }

    public void version(String version) {

        this.version = version;
    }

    public void responseCode(int responseCode) {

        this.responseCode = responseCode;
    }

    public void responseReason(String responseReason) {

        this.responseReason = responseReason;
    }

    public void headers(Map<String, String> headers) {

        this.headers = headers;
    }

    public void content(byte[] content) {

        this.content = content;
    }

    public static Builder ok() {

        return status(Status.OK);
    }

    public static Builder status(Status status) {

        return new Builder(status.status(), status.reason());
    }

    public static class Builder {

        private int status;

        private String reason = "";

        private byte[] content;

        private HashMap<String, String> headers = new HashMap<>();

        public Builder(int status, String statusReason) {

            this.status = status;
            this.reason = statusReason;
        }

        public Builder content(byte[] content) {

            this.content = content;

            return this;
        }

        public Builder content(String content) {

            return content(content.getBytes());
        }


        public Builder header(String key, String value) {

            headers.put(key, value);

            return this;
        }

        public Response build() {

            Response response = new Response();
            response.responseCode = status;
            response.responseReason = reason;
            response.content = content;
            response.headers(headers);

            return response;
        }
    }
}