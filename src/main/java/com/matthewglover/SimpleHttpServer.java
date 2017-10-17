package com.matthewglover;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.socket.HttpServerSocket;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.LoggerFactory;
import com.matthewglover.socket.ServerSocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SimpleHttpServer {

    private final ArgumentParser argumentParser;
    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final LoggerFactory loggerFactory;
    private final RouterBuilder routerBuilder;
    private final FileAccessor fileAccessor;
    private ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(20);
    private RequestRouter requestRouter;

    public SimpleHttpServer(ArgumentParser argumentParser, ServerSocketFactory serverSocketFactory, RouterBuilder routerBuilder, FileAccessor fileAccessor, LoggerFactory loggerFactory) {
        this.argumentParser = argumentParser;
        this.serverSocketFactory = serverSocketFactory;
        this.routerBuilder = routerBuilder;
        this.fileAccessor = fileAccessor;
        this.loggerFactory = loggerFactory;
        this.logger = loggerFactory.getLogger(SimpleHttpServer.class.getName());
    }

    public void run() {
        if (argumentParser.hasErrors()) {
            logArgumentErrors();
        } else {
            runHttpSocketListener();
        }
    }

    private void logArgumentErrors() {
        argumentParser.getErrors().forEach(logger::warning);
    }

    private void runHttpSocketListener() {
        try {
            buildSocket();
            buildRouter();
            while (true) {
                HttpServerSocket httpServerSocket = buildHttpServerSocket();
                httpServerSocket.connect();
                threadPool.execute(() -> httpServerSocket.run());
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    private HttpServerSocket buildHttpServerSocket() {
        return new HttpServerSocket(serverSocket, requestRouter, loggerFactory);
    }

    private void buildSocket() throws IOException {
        serverSocket = serverSocketFactory.getServerSocket(argumentParser.getPort());
    }

    private void buildRouter() {
        requestRouter = routerBuilder.build(argumentParser.getFilePath(), fileAccessor);
    }
}
