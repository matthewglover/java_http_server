package com.matthewglover.socket;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpRequestStreamAdapterTest {
    @Test
    public void convertsRequestStreamToList() throws IOException {
        ArrayList<String> rawRequest = new ArrayList<>(Arrays.asList(
                "GET / HTTP/1.1",
                "Host: localhost:5000",
                "Connection: Keep-Alive",
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)",
                "Accept-Encoding: gzip,deflate"));
        String CRLF = "\r\n";
        String requestString =
                "GET / HTTP/1.1" + CRLF +
                "Host: localhost:5000" + CRLF +
                "Connection: Keep-Alive" + CRLF +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)" + CRLF +
                "Accept-Encoding: gzip,deflate" + CRLF + CRLF;
        SocketDouble socketDouble = new SocketDouble();
        socketDouble.setInputString(requestString);

        HttpRequestStreamAdapter httpRequestStreamAdapter = new HttpRequestStreamAdapter(socketDouble.getInputStream());
        assertEquals(rawRequest, httpRequestStreamAdapter.getRequest());
    }
}