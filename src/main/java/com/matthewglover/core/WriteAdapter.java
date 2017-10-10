package com.matthewglover.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WriteAdapter {
    private InputStream inputStream;

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        InputStreamDecorator inputStreamDecorator = new InputStreamDecorator(inputStream);
        inputStreamDecorator.transferTo(outputStream);
    }
}
