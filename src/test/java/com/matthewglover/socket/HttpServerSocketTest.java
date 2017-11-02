package com.matthewglover.socket;

import com.matthewglover.DefaultRouterBuilder;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.FileAccessorDouble;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpServerSocketTest {

    private final String rootDirectoryPath = "/path/to/public";
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final ServerSocketDouble serverSocket = new ServerSocketDouble();
    private final RequestRouter requestRouter = new DefaultRouterBuilder().build(rootDirectoryPath, fileAccessorDouble);
    private HttpServerSocket httpServerSocket;


    public HttpServerSocketTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        loggerFactory.setLogger(logger);
        httpServerSocket = new HttpServerSocket(serverSocket, requestRouter, loggerFactory);
    }

    @Test
    public void logsSocketCreationExceptions() throws IOException {
        String socketCreationExceptionMessage = "Socket accept exception";
        IOException testException = new IOException(socketCreationExceptionMessage);
        serverSocket.setTestException(testException);
        httpServerSocket.connect();
        assertEquals(socketCreationExceptionMessage, logger.popFromMessageType(LoggerDouble.WARNING));
    }

    @Test
    public void logsSocketAcceptExceptions() throws IOException {
        String socketAcceptExceptionMessage = "Socket accept exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocket.setTestException(testException);
        httpServerSocket.connect();
        assertEquals(socketAcceptExceptionMessage, logger.popFromMessageType(LoggerDouble.WARNING));
    }
}