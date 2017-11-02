package com.matthewglover.http_server;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.socket.HttpServerSocket;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.FileAccessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerRunner {
    private final ArgumentParser argumentParser;

    public HttpServerRunner(ArgumentParser argumentParser) {
        this.argumentParser = argumentParser;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(argumentParser.getPort());
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            RequestRouter router = new RequestRouter(argumentParser.getFilePath(), new FileAccessor());

            while (true) {
                HttpServerThreadExecutor serverExecutor =
                        new HttpServerThreadExecutor(new HttpServerSocket(serverSocket, router), threadPool);
                serverExecutor.execute();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
