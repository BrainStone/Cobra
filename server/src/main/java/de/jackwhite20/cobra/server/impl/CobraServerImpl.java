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

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
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

    public CobraServerImpl(CobraConfig cobraConfig) {

        this.cobraConfig = cobraConfig;
    }

    @Override
    public void start() {

        if(running)
            throw new IllegalStateException("server is already running");

        try {
            serverSocket = new ServerSocket(cobraConfig.port, 25, InetAddress.getByName(cobraConfig.host));

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

    public boolean isRunning() {

        return running;
    }

    private class CobraServerThread implements Runnable {

        @Override
        public void run() {

            // TODO: 30.01.2016
        }
    }
}
