package com.matthewglover.core;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class InputStreamDecoratorTest {
    @Test
    public void transfersDataFromInputToOutputStream() throws IOException {
        String testInput = "test input";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes("UTF-8"));
        InputStreamDecorator inputStreamDecorator = new InputStreamDecorator(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        inputStreamDecorator.transferTo(outputStream);
        assertEquals(testInput, outputStream.toString());
    }
}
