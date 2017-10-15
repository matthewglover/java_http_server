package com.matthewglover;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.socket.HttpServerSocket;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.LoggerFactory;
import com.matthewglover.socket.ServerSocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimpleHttpServer {

    private final ArgumentParser argumentParser;
    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final LoggerFactory loggerFactory;
    private final RouterBuilder routerBuilder;
    private final FileAccessor fileAccessor;

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
            RequestRouter requestRouter = routerBuilder.build(argumentParser.getFilePath(), fileAccessor);
            ServerSocket serverSocket = serverSocketFactory.getServerSocket(argumentParser.getPort());
            while (true) {
                HttpServerSocket httpServerSocket = new HttpServerSocket(
                        serverSocket,
                        requestRouter,
                        loggerFactory);
                httpServerSocket.connect();
                httpServerSocket.run();
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);

        ServerSocketFactory serverSocketFactory = new ServerSocketFactory();
        RouterBuilder routerBuilder = new DefaultRouterBuilder();
        FileAccessor fileAccessor = new FileAccessor();
        LoggerFactory loggerFactory = getLoggerFactory();

        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(
                argumentParser, serverSocketFactory, routerBuilder, fileAccessor, loggerFactory);
        simpleHttpServer.run();
    }

    public static LoggerFactory getLoggerFactory() throws IOException {
        FileHandler fileHandler = new FileHandler("simple_http_server.log");
        fileHandler.setFormatter(new SimpleFormatter());

        LoggerFactory loggerFactory = new LoggerFactory();
        loggerFactory.setHandler(fileHandler);
        return loggerFactory;
    }
}
