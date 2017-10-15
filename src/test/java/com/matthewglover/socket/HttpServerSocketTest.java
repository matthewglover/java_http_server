package com.matthewglover.socket;

import com.matthewglover.DefaultRouterBuilder;
import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
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
    private final HttpRequest httpRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactory);
    private final RequestRouter requestRouter = new DefaultRouterBuilder().build(rootDirectoryPath, fileAccessorDouble);
    private HttpServerSocket httpServerSocket;

    public HttpServerSocketTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        httpRequest.setPath("/log");
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

    @Test
    public void logsHttpRequests() {
        serverSocket.setInputStream(httpRequest.toString());
        httpServerSocket.connect();
        httpServerSocket.run();
        assertEquals(httpRequest.toString(), logger.popFromMessageType(LoggerDouble.INFO));
    }

    @Test
    public void givenSimpleGetRequestReturns200() throws IOException {
        serverSocket.setInputStream(httpRequest.toString());
        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        httpResponse.setContent(httpRequest.requestLineToString());
        httpResponse.setContentLengthHeader();

        httpServerSocket.connect();
        httpServerSocket.run();
        assertEquals(httpResponse.toString(), serverSocket.getOutput().toString());
    }
}