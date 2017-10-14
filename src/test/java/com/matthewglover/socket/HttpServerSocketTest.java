package com.matthewglover.socket;

import com.matthewglover.DefaultRouter;
import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.FileAccessorDouble;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class HttpServerSocketTest {

    private final int port = 5050;
    private final String rootDirectoryPath = "/path/to/public";
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final ServerSocketDouble serverSocket = new ServerSocketDouble();
    private final ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
    private final HttpRequest httpRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactory);
    private final RequestRouter requestRouter = new DefaultRouter().build(rootDirectoryPath, fileAccessorDouble);
    private HttpServerSocket httpServerSocket;
    private final FileAccessor fileAccessor = new FileAccessorDouble();

    public HttpServerSocketTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        httpRequest.setPath("/log");
        loggerFactory.setLogger(logger);
        httpServerSocket = new HttpServerSocket(port, serverSocketFactory, requestRouter, loggerFactory);
    }

    @Test
    public void logsSocketCreationExceptions() throws IOException {
        String socketCreationExceptionMessage = "Socket accept exception";
        IOException testException = new IOException(socketCreationExceptionMessage);
        serverSocketFactory.setTestException(testException);
        serverSocketFactory.setServerSocket(serverSocket);
        httpServerSocket.run();
        assertEquals(socketCreationExceptionMessage, logger.popFromMessageType(LoggerDouble.WARNING));
    }

    @Test
    public void logsSocketAcceptExceptions() throws IOException {
        String socketAcceptExceptionMessage = "Socket accept exception";
        IOException testException = new IOException(socketAcceptExceptionMessage);
        serverSocket.setTestException(testException);
        serverSocketFactory.setServerSocket(serverSocket);
        httpServerSocket.run();
        assertEquals(socketAcceptExceptionMessage, logger.popFromMessageType(LoggerDouble.WARNING));
    }

    @Test
    @Ignore
    public void logsHttpRequests() {
        serverSocket.setInputStream(httpRequest.toString());
        serverSocketFactory.setServerSocket(serverSocket);

        httpServerSocket.run();
        assertEquals(httpRequest.toString(), logger.popFromMessageType(LoggerDouble.INFO));
    }

    @Test
    public void givenSimpleGetRequestReturns200() throws IOException {
        serverSocket.setInputStream(httpRequest.toString());
        serverSocketFactory.setServerSocket(serverSocket);

        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        httpResponse.setContent(httpRequest.requestLineToString());
        httpResponse.setContentLengthHeader();
        httpServerSocket.run();
        assertEquals(httpResponse.toString(), serverSocket.getOutput().toString());
    }

    @Test
    public void givenInvalidRequestMethodReturns400BadRequest() throws UnsupportedEncodingException {
        serverSocket.setInputStream("GQMZUUMG /file1 HTTP/1.1\r\n\r\n");
        serverSocketFactory.setServerSocket(serverSocket);

        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST);
        httpServerSocket.run();
        assertEquals(httpResponse.toString(), serverSocket.getOutput().toString());
    }
}