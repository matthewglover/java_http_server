package com.matthewglover.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpRequestTest {

    private final HttpRequest httpRequest = new HttpRequest();

    @Test
    public void buildsRequestForGivenRequestType() {
        httpRequest.setRequestType(HttpRequestType.GET);
        httpRequest.setHeader("Host", "host:port");
        httpRequest.setHeader("User-Agent", "Apache-HttpClient/4.3.5 (java 1.5)");
        String output = HttpRequestType.GET.toHeader() + "\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Host: host:port\r\n" +
                "\r\n";

        assertEquals(output, httpRequest.toString());
    }
}