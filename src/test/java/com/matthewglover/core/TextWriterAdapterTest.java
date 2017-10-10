package com.matthewglover.core;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class TextWriterAdapterTest {
    @Test
    public void writesTextInputToOutputStream() throws IOException {
        TextWriterAdapter textWriterAdapter = new TextWriterAdapter();

        String input = "Test input";
        textWriterAdapter.setInput(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        textWriterAdapter.writeTo(outputStream);

        assertEquals(input, outputStream.toString());
    }
}