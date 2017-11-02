package com.matthewglover.socket;


public class HttpServerSocketDouble extends HttpServerSocket {
    public int connectCallCount = 0;
    public int runCallCount = 0;

    public HttpServerSocketDouble() {
        super(null, null);
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
