package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class HttpServerSocketTest {

    private final int port = 5050;
    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final ServerSocketDouble serverSocket = new ServerSocketDouble();
    private final ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
    private final HttpRequest httpRequest = new HttpRequest();
    private HttpServerSocket httpServerSocket;

    public HttpServerSocketTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        httpRequest.setMethod(HttpRequestMethod.GET);
        httpRequest.setPath("/");
        httpRequest.setVersion("HTTP/1.1");
        httpRequest.setHeader("Host", "server:port");
        loggerFactory.setLogger(logger);
        httpServerSocket = new HttpServerSocket(port, serverSocketFactory, loggerFactory);
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

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.OK);
        httpResponse.setContent("<html><head></head><body></body></html>");
        httpResponse.setContentLengthHeader();
        httpServerSocket.run();
        assertEquals(httpResponse.toString(), serverSocket.getOutput().toString());
    }

    @Test
    public void givenInvalidRequestMethodReturns400BadRequest() throws UnsupportedEncodingException {
        serverSocket.setInputStream("GQMZUUMG /file1 HTTP/1.1\r\n\r\n");
        serverSocketFactory.setServerSocket(serverSocket);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.BAD_REQUEST);
        httpResponse.setHeader("Content-Length", "0");
        httpServerSocket.run();
        assertEquals(httpResponse.toString(), serverSocket.getOutput().toString());
    }
}