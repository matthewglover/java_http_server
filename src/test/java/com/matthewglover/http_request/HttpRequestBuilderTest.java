package com.matthewglover.http_request;

import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpRequestBuilderTest {

    private final String getRequest =
            "GET /form HTTP/1.0\r\n" +
                    "Host: localhost:5000\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                    "Accept-Encoding: gzip,deflate\r\n\r\n";

    private final String postRequest =
            "POST /form HTTP/1.0\r\n" +
                    "Content-Length: 11\r\n" +
                    "Host: localhost:5000\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                    "Accept-Encoding: gzip,deflate\r\n" +
                    "\r\n" +
                    "\"My\"=\"Data\"\r\n\r\n";

    private final String putRequest =
            "PUT /form HTTP/1.0\r\n" +
                    "Content-Length: 11\r\n" +
                    "Host: localhost:5000\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                    "Accept-Encoding: gzip,deflate\r\n" +
                    "\r\n" +
                    "\"My\"=\"Data\"\r\n\r\n";

    private final String putRequestNoContent =
            "PUT /form HTTP/1.0\r\n" +
                    "Content-Length: 0\r\n" +
                    "Host: localhost:5000\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                    "Accept-Encoding: gzip,deflate\r\n\r\n";

    private final LoggerDouble loggerDouble = new LoggerDouble("com.matthewglover", "");
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final SocketDouble socketDouble = new SocketDouble();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    @Ignore
    public void buildsGetRequestFromStream() throws IOException {
        socketDouble.setInputString(getRequest);
        HttpRequestBuilder builder = new HttpRequestBuilder(socketDouble.getInputStream(), loggerFactoryDouble);
        HttpRequest request = builder.build();
        assertEquals(HttpRequestMethod.GET, request.getMethod());
        assertEquals("/form", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("localhost:5000", request.getHeader("Host"));
        assertFalse(request.hasContent());
    }

    @Test
    public void buildsPostRequestFromStream() throws IOException {
        socketDouble.setInputString(postRequest);
        isValidFormRequestOfType(HttpRequestMethod.POST);
    }

    @Test
    public void buildsPutRequestFromStream() throws IOException {
        socketDouble.setInputString(putRequest);
        isValidFormRequestOfType(HttpRequestMethod.PUT);
    }

    @Test
    public void buildsPutRequestWithNoContentFromStream() throws IOException {
        socketDouble.setInputString(putRequestNoContent);
        HttpRequestBuilder builder = new HttpRequestBuilder(socketDouble.getInputStream(), loggerFactoryDouble);
        HttpRequest request = builder.build();
        assertEquals(HttpRequestMethod.PUT, request.getMethod());
        assertEquals("/form", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("localhost:5000", request.getHeader("Host"));
        assertFalse(request.hasContent());
    }

    private void isValidFormRequestOfType(HttpRequestMethod requestMethod) throws IOException {
        HttpRequestBuilder builder = new HttpRequestBuilder(socketDouble.getInputStream(), loggerFactoryDouble);
        HttpRequest request = builder.build();
        assertEquals(requestMethod, request.getMethod());
        assertEquals("/form", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("localhost:5000", request.getHeader("Host"));
        assertTrue(request.hasContent());
        assertEquals("\"My\"=\"Data\"", request.getContent());
    }
}