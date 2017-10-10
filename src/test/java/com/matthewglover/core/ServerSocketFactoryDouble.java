package com.matthewglover.core;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactoryDouble extends ServerSocketFactory {

    private ServerSocket serverSocket;
    private IOException ioException;

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public ServerSocket getServerSocket(int port) throws IOException {
        if (shouldThrow()) throw ioException;
        return serverSocket;
    }

    public void setTestException(IOException ioException) {
        this.ioException = ioException;
    }

    private boolean shouldThrow() {
        return ioException != null;
    }
}
