package com.matthewglover.socket;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class SocketDoubleTest {
    @Test
    public void recordsDataWrittenToOutput() {
        String testOutput = "Test output";
        SocketDouble socketDouble = new SocketDouble();
        PrintStream printStream = new PrintStream(socketDouble.getOutputStream());
        printStream.println(testOutput);
        assertEquals(testOutput, socketDouble.getOutput().trim());
    }

    @Test
    public void setsInputStreamToProvidedStringData() throws IOException {
        String testInput = "TestInput";
        SocketDouble socketDouble = new SocketDouble();
        socketDouble.setInputString(testInput);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketDouble.getInputStream()));
        assertEquals(testInput, bufferedReader.readLine());
    }
}
