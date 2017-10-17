package com.matthewglover.socket;

import java.net.ServerSocket;

public class ServerSocketFactory {
    public ServerSocket getServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
