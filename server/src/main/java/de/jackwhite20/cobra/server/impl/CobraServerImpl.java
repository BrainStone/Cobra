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

import de.jackwhite20.cobra.server.CobraServer;
import de.jackwhite20.cobra.server.filter.FilteredRequest;
import de.jackwhite20.cobra.server.filter.RequestFilter;
import de.jackwhite20.cobra.server.http.*;
import de.jackwhite20.cobra.server.http.annotation.*;
import de.jackwhite20.cobra.shared.ContentType;
import de.jackwhite20.cobra.shared.RequestMethod;
import de.jackwhite20.cobra.shared.Status;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class CobraServerImpl implements CobraServer {

    private boolean running;
    
    private CobraConfig cobraConfig;

    private ServerSocket serverSocket;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private HashMap<String, ResourceInfo> resourceInfo = new HashMap<>();

    private List<RequestFilter> filters = new ArrayList<>();

    public CobraServerImpl(CobraConfig cobraConfig) {

        this.cobraConfig = cobraConfig;
        for (Class<?> aClass : cobraConfig.classes) {
            scanClass(aClass);
        }

        for (Class<? extends RequestFilter> filter : cobraConfig.filters) {
            try {
                filters.add(filter.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void scanClass(Class<?> clazz) {

        ResourceInfo resourceInfo = null;
        try {
            resourceInfo = new ResourceInfo(clazz.newInstance(), clazz.getAnnotation(Path.class).value());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(resourceInfo == null)
            return;

        for (Method method : clazz.getMethods()) {
            if(method.isAnnotationPresent(Path.class)/* && method.getParameterCount() == 1*/) {
                if (method.getParameterTypes()[0].isAssignableFrom(Request.class)) {
                    RequestMethod requestMethod = RequestMethod.GET;
                    if(method.isAnnotationPresent(POST.class))
                        requestMethod = RequestMethod.POST;

                    ResourceInfo.Entry resEntry;

                    // TODO: 04.02.2016
                    // TODO: Maybe allow Produces annotation only on POST requests
                    resourceInfo.add(method.getAnnotation(Path.class).value(), (method.isAnnotationPresent(Produces.class)) ? resEntry = new ResourceInfo.Entry(method, method.getAnnotation(Produces.class).value().type(), (method.isAnnotationPresent(Consumes.class)) ? method.getAnnotation(Consumes.class).value().type() : ContentType.ALL.type(), requestMethod) : (resEntry = new ResourceInfo.Entry(method)));

                    if(method.getParameterCount() > 1) {
                        for (Parameter parameter : method.getParameters()) {
                            if(parameter.isAnnotationPresent(FormParam.class)) {
                                String postKey = parameter.getAnnotation(FormParam.class).value();
                                resEntry.addPostKey(postKey);
                            }
                        }
                    }
                }
            }
        }

        this.resourceInfo.put(resourceInfo.rootPath(), resourceInfo);
    }

    @Override
    public void start() {

        if(running)
            throw new IllegalStateException("server is already running");

        try {
            serverSocket = new ServerSocket(cobraConfig.port, cobraConfig.backLog, InetAddress.getByName(cobraConfig.host));

            running = true;

            executorService.execute(new CobraServerThread());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

        if(!running)
            throw new IllegalStateException("server is already stopped");

        running = false;

        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    public void filter(FilteredRequest filteredRequest) {

        for (RequestFilter filter : filters) {
            filter.filter(filteredRequest);
        }
    }

    public Response handleRequest(Request request) {

        for (Map.Entry<String, ResourceInfo> entry : resourceInfo.entrySet()) {
            if(request.location().startsWith(entry.getKey())) {

                return entry.getValue().execute(request.location().replace(entry.getKey(), ""), request);
            }
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    public boolean isRunning() {

        return running;
    }

    private class CobraServerThread implements Runnable {

        @Override
        public void run() {

            while (running) {
                try {
                    executorService.execute(new HTTPHandler(serverSocket.accept(), CobraServerImpl.this));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
