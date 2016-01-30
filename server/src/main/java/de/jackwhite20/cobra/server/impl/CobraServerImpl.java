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
