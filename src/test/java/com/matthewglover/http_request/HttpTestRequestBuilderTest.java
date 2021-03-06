package com.matthewglover.http_request;

import com.matthewglover.http_server.SocketDouble;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpTestRequestBuilderTest {

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
                    "\"My\"=\"Data\"";

    private final String putRequest =
            "PUT /form HTTP/1.0\r\n" +
                    "Content-Length: 11\r\n" +
                    "Host: localhost:5000\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                    "Accept-Encoding: gzip,deflate\r\n" +
                    "\r\n" +
                    "\"My\"=\"Data\"";

    private final String putRequestNoContent =
            "PUT /form HTTP/1.0\r\n" +
                    "Content-Length: 0\r\n" +
                    "Host: localhost:5000\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                    "Accept-Encoding: gzip,deflate\r\n\r\n";

    private final SocketDouble socketDouble = new SocketDouble();

    @Test
    public void buildsGetRequestFromStream() throws IOException {
        socketDouble.setInputString(getRequest);
        HttpRequestParser builder = new HttpRequestParser(socketDouble.getInputStream());
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
        HttpRequestParser builder = new HttpRequestParser(socketDouble.getInputStream());
        HttpRequest request = builder.build();
        assertEquals(HttpRequestMethod.PUT, request.getMethod());
        assertEquals("/form", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("localhost:5000", request.getHeader("Host"));
        assertFalse(request.hasContent());
    }

    private void isValidFormRequestOfType(HttpRequestMethod requestMethod) throws IOException {
        HttpRequestParser builder = new HttpRequestParser(socketDouble.getInputStream());
        HttpRequest request = builder.build();
        assertEquals(requestMethod, request.getMethod());
        assertEquals("/form", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("localhost:5000", request.getHeader("Host"));
        assertTrue(request.hasContent());
        assertEquals("\"My\"=\"Data\"", request.getContent());
    }
}