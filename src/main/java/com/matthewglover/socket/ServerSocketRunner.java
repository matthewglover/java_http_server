package com.matthewglover.socket;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerSocketRunner {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(20);
    private final Logger logger;
    private final ServerSocket serverSocket;
    private final RequestRouter router;

    public ServerSocketRunner(
            RequestRouter router, ServerSocket serverSocket, LoggerFactory loggerFactory) {
        this.logger = loggerFactory.getLogger(ServerSocketRunner.class.getName());
        this.serverSocket = serverSocket;
        this.router = router;
    }

    public void run() {
        try {
            while (true) {
                HttpServerSocket httpServerSocket = buildHttpServerSocket();
                httpServerSocket.connect();
                threadPool.execute(httpServerSocket::run);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    private HttpServerSocket buildHttpServerSocket() {
        return new HttpServerSocket(serverSocket, router);
    }
}
