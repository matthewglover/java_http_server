package com.matthewglover.http_server;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.*;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class HttpServerTest {

    private final String rootDirectoryPath = "/path/to/public";
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final RequestRouter requestRouter = new RequestRouter(rootDirectoryPath, fileAccessorDouble);
    private final ServerSocketDouble serverSocketDouble = new ServerSocketDouble();
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpServer httpServer = new HttpServer(serverSocketDouble, requestRouter, loggerFactoryDouble);

    public HttpServerTest() throws IOException {
    }

    @Test
    public void connectCallsAcceptOnSocket() {
        httpServer.connect();
        assertTrue(serverSocketDouble.isSocketConnected());
    }

    @Test
    public void connectLogsExceptionsOnSocketCreated() throws IOException {
        String socketAcceptExceptionMessage = "Socket connect exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketDouble.setTestException(testException);

        httpServer.connect();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Socket connect exception"));
    }

    @Test
    public void logsExceptionsOnSocketCreated2() throws IOException {
        String socketAcceptExceptionMessage = "Socket connect exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketDouble.setTestException(testException);

        httpServer.connect();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Socket connect exception"));
    }

    @Test
    public void logsExceptionsOnGetRequest() throws IOException {
        String socketAcceptExceptionMessage = "Socket read/write exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketDouble.setConnectedSocketTestException(testException);

        httpServer.connect();
        httpServer.run();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Socket read/write exception"));
    }

    @Test
    public void takesRequestAndResponds() {
        SocketDouble socketDouble = serverSocketDouble.getSocket();
        socketDouble.setInputString("GET /tea HTTP/1.0\r\n" +
                "Host: localhost:5000\r\n\r\n");
        httpServer.connect();
        httpServer.run();
        assertThat(socketDouble.getOutput(), containsString("HTTP/1.1 200 OK"));
    }
}