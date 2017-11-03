package com.matthewglover.socket;

import java.io.*;
import java.net.Socket;

public class SocketDouble extends Socket {
    private static final String ENCODING = "UTF-8";

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayInputStream inputStream;

    private IOException ioException;

    public void setInputString(String data) {
        try {
            inputStream = new ByteArrayInputStream(data.getBytes(ENCODING));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getOutput() {
        return outputStream.toString();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (shouldThrow()) throw ioException;
        return outputStream;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (shouldThrow()) throw ioException;
        return inputStream;
    }

    public void setTestException(IOException ioException) {
        this.ioException = ioException;
    }

    private boolean shouldThrow() {
        return ioException != null;
    }
}
