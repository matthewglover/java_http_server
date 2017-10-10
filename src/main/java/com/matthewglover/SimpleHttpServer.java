package com.matthewglover;

import com.matthewglover.util.ArgumentParser;
import com.matthewglover.socket.HttpServerSocket;
import com.matthewglover.util.LoggerFactory;
import com.matthewglover.socket.ServerSocketFactory;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimpleHttpServer {

    private final ArgumentParser argumentParser;
    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final LoggerFactory loggerFactory;

    public SimpleHttpServer(ArgumentParser argumentParser, ServerSocketFactory serverSocketFactory, LoggerFactory loggerFactory) {
        this.argumentParser = argumentParser;
        this.serverSocketFactory = serverSocketFactory;
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
        while (true) {
            HttpServerSocket httpServerSocket = new HttpServerSocket(argumentParser.getPort(), serverSocketFactory, loggerFactory);
            httpServerSocket.run();
        }
    }

    public static void main(String[] args) throws IOException {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);

        ServerSocketFactory serverSocketFactory = new ServerSocketFactory();

        FileHandler fileHandler = new FileHandler("simple_http_server.log");
        fileHandler.setFormatter(new SimpleFormatter());

        LoggerFactory loggerFactory = new LoggerFactory();
        loggerFactory.setHandler(fileHandler);

        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(argumentParser, serverSocketFactory, loggerFactory);
        simpleHttpServer.run();
    }
}
