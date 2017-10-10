package com.matthewglover.core;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static junit.framework.TestCase.assertEquals;

public class ServerSocketAdapterTest {

    @Test
    public void getsInputFromSocket() throws IOException {
        String testInput = "Test input message\n" +
                "multi-line message\n";

        ServerSocketDouble serverSocket = new ServerSocketDouble();
        serverSocket.setInputStream(testInput);

        ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
        serverSocketFactory.setServerSocket(serverSocket);

        int port = 0;
        ServerSocketAdapter serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
        serverSocketAdapter.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(serverSocketAdapter.getInputStream()));
        String line;
        String request = "";
        while ((line = br.readLine()) != null) {
            request += line + "\n";
        }
        assertEquals(testInput, request);
    }


    @Test
    public void sendsOutputToSocket() throws IOException {
        String testOutput = "Test output message\n" +
                "multi-line message\n";

        ServerSocketDouble serverSocket = new ServerSocketDouble();

        ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
        serverSocketFactory.setServerSocket(serverSocket);


        int port = 0;
        ServerSocketAdapter serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
        serverSocketAdapter.accept();

        DataOutputStream outputStream = new DataOutputStream(serverSocketAdapter.getOutputStream());
        outputStream.writeBytes(testOutput);

        assertEquals(testOutput, serverSocket.getOutput());
    }
}