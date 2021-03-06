package com.matthewglover.http_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAdapter {

    private final ServerSocket serverSocket;
    private Socket socket;

    public ServerSocketAdapter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void accept() throws IOException {
        socket = serverSocket.accept();
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }
}
