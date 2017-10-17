package com.matthewglover.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactoryDouble extends ServerSocketFactory {

    private ServerSocket serverSocket;
    private IOException ioException;

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public ServerSocket getServerSocket(int port) {
        if (shouldThrow()) throw new RuntimeException(ioException);
        return serverSocket;
    }

    public void setTestException(IOException ioException) {
        this.ioException = ioException;
    }

    private boolean shouldThrow() {
        return ioException != null;
    }
}
