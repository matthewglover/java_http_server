package com.matthewglover.http_server;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerRunner {
    private final ArgumentParser argumentParser;
    private final LoggerFactory loggerFactory;

    public HttpServerRunner(ArgumentParser argumentParser, LoggerFactory loggerFactory) {
        this.argumentParser = argumentParser;
        this.loggerFactory = loggerFactory;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(argumentParser.getPort());
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            RequestRouter router = new RequestRouter(argumentParser.getFilePath(), new FileAccessor());

            while (true) {
                HttpServerThreadExecutor serverExecutor =
                        new HttpServerThreadExecutor(new HttpServer(serverSocket, router, loggerFactory), threadPool);
                serverExecutor.execute();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
