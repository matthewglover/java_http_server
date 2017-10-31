package com.matthewglover.socket;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerSocketRunner {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(20);

    public ServerSocketRunner(RequestRouter router, ServerSocket serverSocket, LoggerFactory loggerFactory) {
        Logger logger = loggerFactory.getLogger(ServerSocketRunner.class.getName());

        try {
            while (true) {
                HttpServerSocket httpServerSocket = new HttpServerSocket(serverSocket, router, loggerFactory);
                httpServerSocket.connect();
                threadPool.execute(httpServerSocket::run);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}
