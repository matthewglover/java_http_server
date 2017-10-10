package com.matthewglover.socket;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.*;

public class ServerSocketFactoryTest {
    @Test
    public void createsServerSocketOnSpecifiedPort() throws IOException {
        ServerSocketFactory serverSocketFactory = new ServerSocketFactory();
        int testPort = 5050;
        ServerSocket serverSocket = serverSocketFactory.getServerSocket(testPort);
        assertEquals(testPort, serverSocket.getLocalPort());
    }
}