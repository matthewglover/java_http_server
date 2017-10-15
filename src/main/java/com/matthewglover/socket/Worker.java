package com.matthewglover.socket;

public class Worker implements Runnable {
    private final HttpServerSocket server;

    public Worker(HttpServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        server.run();
    }
}
