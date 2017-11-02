package com.matthewglover.socket;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.SimpleFormatter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class HttpServerSocketTest {

    private final String rootDirectoryPath = "/path/to/public";
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final RequestRouter requestRouter = new RequestRouter(rootDirectoryPath, fileAccessorDouble);
    ServerSocketAdapterDouble serverSocketAdapterDouble = new ServerSocketAdapterDouble();
    HttpServerSocket httpServerSocket = new HttpServerSocket(serverSocketAdapterDouble, requestRouter);

    public HttpServerSocketTest() throws IOException {
    }

    @Test
    public void connectCallsAcceptOnSocket() {
        httpServerSocket.connect();
        assertTrue(serverSocketAdapterDouble.isSocketConnected());
    }

    @Test
    public void connectLogsSocketAcceptErrors() {
        httpServerSocket.connect();
        assertTrue(serverSocketAdapterDouble.isSocketConnected());
    }

    @Test
    public void logsExceptionsOnSocketCreated() throws IOException {
        AppLogger appLogger = AppLogger.getInstance();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TestStreamHandler testStreamHandler = new TestStreamHandler(outputStream, new SimpleFormatter());
        appLogger.setHandler(testStreamHandler);
        String socketAcceptExceptionMessage = "Socket connect exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocketAdapterDouble.setTestException(testException);

        httpServerSocket.connect();

        assertThat(outputStream.toString(), containsString("Socket connect exception"));
    }
}