package com.matthewglover;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.socket.ServerSocketRunner;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.LoggerFactory;
import com.matthewglover.socket.ServerSocketFactory;

import java.net.ServerSocket;
import java.util.logging.Logger;

class SimpleHttpServer {

    private final ArgumentParser argumentParser;
    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final LoggerFactory loggerFactory;
    private final RouterBuilder routerBuilder;
    private final FileAccessor fileAccessor;

    public SimpleHttpServer(ArgumentParser argumentParser,
                            ServerSocketFactory serverSocketFactory,
                            RouterBuilder routerBuilder,
                            FileAccessor fileAccessor,
                            LoggerFactory loggerFactory) {
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
            new ServerSocketRunner(getRouter(), getServerSocket(), loggerFactory);
        }
    }

    private void logArgumentErrors() {
        argumentParser.getErrors().forEach(logger::warning);
    }

    private RequestRouter getRouter() {
        return routerBuilder.build(argumentParser.getFilePath(), fileAccessor);
    }

    private ServerSocket getServerSocket() {
        return serverSocketFactory.getServerSocket(argumentParser.getPort());
    }
}
