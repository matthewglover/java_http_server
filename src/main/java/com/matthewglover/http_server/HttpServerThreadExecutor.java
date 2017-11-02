package com.matthewglover.http_server;

import com.matthewglover.socket.HttpServerSocket;

import java.util.concurrent.ExecutorService;

public class HttpServerThreadExecutor {
    private final HttpServerSocket httpServerSocket;
    private final ExecutorService threadPool;

    public HttpServerThreadExecutor(HttpServerSocket httpServerSocket, ExecutorService threadPool) {
        this.httpServerSocket = httpServerSocket;
        this.threadPool = threadPool;
    }

    public void execute() {
        httpServerSocket.connect();
        threadPool.execute(httpServerSocket::run);
    }
}
