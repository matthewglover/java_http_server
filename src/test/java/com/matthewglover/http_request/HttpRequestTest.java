package com.matthewglover.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpRequestTest {

    private final HttpRequest httpRequest = new HttpRequest();

    @Test
    public void buildsRequestForGivenRequestType() {
        httpRequest.setMethod(HttpRequestMethod.GET);
        httpRequest.setPath("/path/to/get");
        httpRequest.setVersion("HTTP/1.1");
        httpRequest.setHeader("Host", "host:port");
        httpRequest.setHeader("User-Agent", "Apache-HttpClient/4.3.5 (java 1.5)");
        String output = "GET /path/to/get HTTP/1.1" + "\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Host: host:port\r\n" +
                "\r\n";

        assertEquals(output, httpRequest.toString());
    }
}