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

package de.jackwhite20.cobra.server.impl;

import de.jackwhite20.cobra.server.http.HTTPRequest;
import de.jackwhite20.cobra.server.http.HTTPResponse;
import de.jackwhite20.cobra.shared.RequestMethod;
import de.jackwhite20.cobra.shared.Status;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class ResourceInfo {

    private Object object;

    private String rootPath;

    private HashMap<String, Entry> methods = new HashMap<>();

    public ResourceInfo(Object object, String rootPath) {

        this.object = object;
        this.rootPath = rootPath;
    }

    public HTTPResponse execute(String path, HTTPRequest httpRequest) {

        Entry entry = methods.get(path);
        if(entry != null) {
            if(!entry.acceptContentType.equals("*/*") && !entry.acceptContentType.equals(httpRequest.header("Content-Type")))
                return HTTPResponse.status(Status.UNSUPPORTED_MEDIA_TYPE).build();

            if(!entry.requestMethod.equals(httpRequest.method()))
                return HTTPResponse.status(Status.METHOD_NOT_ALLOWED).build();

            try {
                Object[] objects = { httpRequest };

                // TODO: 04.02.2016
                if(httpRequest.method() == RequestMethod.POST) {
                    objects = new Object[entry.postParameters.size() + 1];
                    objects[0] = httpRequest;
                    int i = 1;
                    for (String parameter : entry.postParameters) {
                        objects[i] = httpRequest.post(parameter);
                        i++;
                    }
                }

                HTTPResponse response = ((HTTPResponse) entry.method.invoke(object, objects));
                response.headers().put("Content-Type", entry.contentType);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return HTTPResponse.status(Status.NOT_FOUND).build();
    }

    public void add(String path, Entry entry) {

        methods.put(path, entry);
    }

    public String rootPath() {

        return rootPath;
    }

    public Method method(String path) {

        return methods.get(path).method;
    }

    public static class Entry {

        private Method method;

        private String contentType;

        private String acceptContentType;

        private RequestMethod requestMethod;

        private List<String> postParameters = new ArrayList<>();

        public Entry(Method method, String contentType, String acceptContentType, RequestMethod requestMethod) {

            this.method = method;
            this.contentType = contentType;
            this.acceptContentType = acceptContentType;
            this.requestMethod = requestMethod;
        }

        public Entry(Method method) {

            this(method, "text/html; charset=utf-8", "*/*", RequestMethod.GET);
        }

        public void addPostKey(String postKey) {

            postParameters.add(postKey);
        }

        public Method method() {

            return method;
        }

        public String contentType() {

            return contentType;
        }

        public String acceptContentType() {

            return acceptContentType;
        }

        public RequestMethod requestMethod() {

            return requestMethod;
        }

        public List<String> postParameters() {

            return postParameters;
        }
    }
}
