package com.matthewglover.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDouble extends ServerSocket {

    private IOException ioException;
    private final SocketDouble socketDouble = new SocketDouble();
    private boolean socketConnected;

    public ServerSocketDouble() throws IOException {
    }

    @Override
    public Socket accept() throws IOException {
        if (shouldThrow()) throw ioException;
        socketConnected = true;
        return socketDouble;
    }

    public void setTestException(IOException ioException) {
        this.ioException = ioException;
    }

    public String getOutput() {
        return socketDouble.getOutput();
    }

    private boolean shouldThrow() {
        return ioException != null;
    }

    public void setInputStream(String inputStream) {
        socketDouble.setInputString(inputStream);
    }

    public boolean isSocketConnected() {
        return socketConnected;
    }
}
