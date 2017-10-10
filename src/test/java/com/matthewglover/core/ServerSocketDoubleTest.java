package com.matthewglover.core;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerSocketDoubleTest {
    @Test
    public void recordsDataWrittenToOutput() throws IOException {
        String testOutput = "Test output";
        ServerSocketDouble serverSocketDouble = new ServerSocketDouble();
        Socket socket = serverSocketDouble.accept();
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        printStream.println(testOutput);
        assertEquals(testOutput, serverSocketDouble.getOutput().trim());
    }

    @Test
    public void setsInputStreamToProvidedStringData() throws IOException {
        String testInput = "TestInput";
        ServerSocketDouble serverSocketDouble = new ServerSocketDouble();
        serverSocketDouble.setInputStream(testInput);
        Socket socket = serverSocketDouble.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertEquals(testInput, bufferedReader.readLine());
    }
}
