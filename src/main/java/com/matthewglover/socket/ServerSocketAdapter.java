package com.matthewglover.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAdapter {

    private final ServerSocket serverSocket;
    private Socket socket;

    public ServerSocketAdapter(ServerSocketFactory serverSocketFactory, int port) throws IOException {
        serverSocket = serverSocketFactory.getServerSocket(port);
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
        serverSocket.close();
    }
}
