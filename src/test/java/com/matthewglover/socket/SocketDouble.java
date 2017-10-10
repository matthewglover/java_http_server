package com.matthewglover.socket;

import java.io.*;
import java.net.Socket;

public class SocketDouble extends Socket {
    private static final String ENCODING = "UTF-8";

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayInputStream inputStream;

    public void setInputStream(String data) {
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
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }
}
