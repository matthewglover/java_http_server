package com.matthewglover.core;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class ServerSocketAdapterTest {

    @Test
    public void getsRequestFromSocket() throws IOException {
        String testInput = "Test input message\n" +
                "multi-line message\n";

        ServerSocketDouble serverSocket = new ServerSocketDouble();
        serverSocket.setInputStream(testInput);

        ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
        serverSocketFactory.setServerSocket(serverSocket);

        int port = 0;
        ServerSocketAdapter serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
        serverSocketAdapter.accept();

        assertEquals(testInput, serverSocketAdapter.getRequest().toString());
    }


    @Test
    public void sendsResponseToSocket() throws IOException {
        String testOutput = "Test output message\n" +
                "multi-line message\n";

        ServerSocketDouble serverSocket = new ServerSocketDouble();

        ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
        serverSocketFactory.setServerSocket(serverSocket);


        int port = 0;
        ServerSocketAdapter serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
        serverSocketAdapter.accept();

        TextWriterAdapter textWriterAdapter = new TextWriterAdapter();
        textWriterAdapter.setInput(testOutput);
        serverSocketAdapter.sendResponse(textWriterAdapter);

        assertEquals(testOutput, serverSocket.getOutput());
    }
}