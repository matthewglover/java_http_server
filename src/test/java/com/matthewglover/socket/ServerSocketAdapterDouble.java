package com.matthewglover.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerSocketAdapterDouble extends ServerSocketAdapter {

    private final SocketDouble socketDouble = new SocketDouble();
    private IOException ioException;
    private boolean socketConnected;

    public ServerSocketAdapterDouble() {
        super(null);
    }

    @Override
    public void accept() throws IOException {
        if (shouldThrow()) throw ioException;
        socketConnected = true;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (shouldThrow()) throw ioException;
        return socketDouble.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (shouldThrow()) throw ioException;
        return socketDouble.getOutputStream();
    }

    public void setTestException(IOException ioException) {
        this.ioException = ioException;
    }

    private boolean shouldThrow() {
        return ioException != null;
    }

    public boolean isSocketConnected() {
        return socketConnected;
    }
}
