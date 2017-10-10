package com.matthewglover.core;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class WriteAdapterTest {
    @Test
    public void writesInputStreamToOutputStream() throws IOException {
        WriteAdapter writeAdapter = new WriteAdapter();
        String testInput = "Test input message";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes("UTF-8"));
        writeAdapter.setInputStream(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeAdapter.writeTo(outputStream);

        assertEquals(testInput, outputStream.toString());
    }
}