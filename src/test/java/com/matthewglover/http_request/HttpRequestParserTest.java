package com.matthewglover.http_request;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpRequestParserTest {

    @Test
    public void parsesRawGetRequest() {
        HttpRequest testRequest = HttpRequestFactory.get(HttpRequestType.SIMPLE_GET);
        ArrayList<String> rawRequest = testRequest.toRaw();

        HttpRequestParser httpRequestParser = new HttpRequestParser(rawRequest);
        httpRequestParser.parse();
        HttpRequest request = httpRequestParser.getRequest();

        assertFalse(httpRequestParser.hasErrors());
        assertEquals(HttpRequestMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.1", request.getVersion());
        assertEquals("localhost:5000", request.getHeader("Host"));
        assertEquals("Keep-Alive", request.getHeader("Connection"));
        assertEquals("gzip,deflate", request.getHeader("Accept-Encoding"));
    }


    @Test
    public void invalidMethod() {
        ArrayList<String> rawRequest = new ArrayList<>(Arrays.asList(
                "GQMZUUMG /file1 HTTP/1.1"
        ));
        HttpRequestParser httpRequestParser = new HttpRequestParser(rawRequest);
        httpRequestParser.parse();
        assertTrue(httpRequestParser.hasErrors());
    }
}