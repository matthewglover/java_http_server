package com.matthewglover.core;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactory {
    public ServerSocket getServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}
