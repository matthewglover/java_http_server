package com.matthewglover.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamDecorator extends InputStream {
    private final int bufferSize = 1024;
    private final InputStream decoratedInputStream;

    public InputStreamDecorator(InputStream decoratedInputStream) {
        this.decoratedInputStream = decoratedInputStream;
    }

    public void transferTo(OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[bufferSize];
        while (writeBuffer(outputStream, buffer));
    }

    @Override
    public int read() throws IOException {
        return decoratedInputStream.read();
    }

    private boolean writeBuffer(OutputStream outputStream, byte[] buffer) throws IOException {
        int len = read(buffer);
        if (len == -1) {
            return false;
        } else {
            outputStream.write(buffer, 0, len);
            return true;
        }
    }
}
