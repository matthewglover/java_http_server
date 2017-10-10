package com.matthewglover.core;

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

    public OutputStream getRequest() throws IOException {
        InputStreamDecorator inputStreamDecorator = new InputStreamDecorator(socket.getInputStream());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        inputStreamDecorator.transferTo(outputStream);
        return outputStream;
    }

    public void sendResponse(WriteAdapter writeAdapter) throws IOException {
        writeAdapter.writeTo(socket.getOutputStream());
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }
}
