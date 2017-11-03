package com.matthewglover.socket;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.*;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class HttpServerSocketTest {

    private final String rootDirectoryPath = "/path/to/public";
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final RequestRouter requestRouter = new RequestRouter(rootDirectoryPath, fileAccessorDouble);
    private final ServerSocketDouble serverSocketDouble = new ServerSocketDouble();
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpServerSocket httpServerSocket = new HttpServerSocket(serverSocketDouble, requestRouter, loggerFactoryDouble);

    public HttpServerSocketTest() throws IOException {
    }

    @Test
    public void connectCallsAcceptOnSocket() {
        httpServerSocket.connect();
        assertTrue(serverSocketDouble.isSocketConnected());
    }

    @Test
    public void connectLogsExceptionsOnSocketCreated() throws IOException {
        String socketAcceptExceptionMessage = "Socket connect exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketDouble.setTestException(testException);

        httpServerSocket.connect();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Socket connect exception"));
    }

    @Test
    public void logsExceptionsOnSocketCreated2() throws IOException {
        String socketAcceptExceptionMessage = "Socket connect exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketDouble.setTestException(testException);

        httpServerSocket.connect();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Socket connect exception"));
    }

    @Test
    public void logsExceptionsOnGetRequest() throws IOException {
        String socketAcceptExceptionMessage = "Socket read/write exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketDouble.setConnectedSocketTestException(testException);

        httpServerSocket.connect();
        httpServerSocket.run();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Socket read/write exception"));
    }
}