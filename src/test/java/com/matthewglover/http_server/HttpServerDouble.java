package com.matthewglover.http_server;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.net.ServerSocket;

public class HttpServerDouble extends HttpServer {
    public int connectCallCount = 0;
    public int runCallCount = 0;

    public HttpServerDouble(ServerSocket serverSocket, RequestRouter requestRouter, LoggerFactory loggerFactory) {
        super(serverSocket, requestRouter, loggerFactory);
    }

    @Override
    public void connect() {
        connectCallCount += 1;
    }

    @Override
    public void run() {
        runCallCount += 1;
    }
}
