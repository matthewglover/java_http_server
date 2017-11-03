package com.matthewglover.http_server;

import java.util.concurrent.ExecutorService;

public class HttpServerThreadExecutor {
    private final HttpServer httpServer;
    private final ExecutorService threadPool;

    public HttpServerThreadExecutor(HttpServer httpServer, ExecutorService threadPool) {
        this.httpServer = httpServer;
        this.threadPool = threadPool;
    }

    public void execute() {
        httpServer.connect();
        threadPool.execute(httpServer::run);
    }
}
