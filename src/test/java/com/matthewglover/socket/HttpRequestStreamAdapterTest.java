package com.matthewglover.socket;

import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
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
        LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
        LoggerDouble logger = new LoggerDouble(null, null);
        loggerFactory.setLogger(logger);
        SocketDouble socketDouble = new SocketDouble();
        socketDouble.setInputString(requestString);

        HttpRequestStreamAdapter httpRequestStreamAdapter = new HttpRequestStreamAdapter(socketDouble.getInputStream(), loggerFactory);
        assertEquals(rawRequest, httpRequestStreamAdapter.getRequest());
    }
}