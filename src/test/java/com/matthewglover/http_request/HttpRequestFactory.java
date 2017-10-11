package com.matthewglover.http_request;

public class HttpRequestFactory {
    public static HttpRequest get(HttpRequestType httpRequestType) {
        switch (httpRequestType) {
            case SIMPLE_GET:
            default:
                return buildSimpleGet();
        }
    }

    private static HttpRequest buildSimpleGet() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpRequestMethod.GET);
        httpRequest.setPath("/");
        httpRequest.setVersion("HTTP/1.1");
        httpRequest.setHeader("Host", "server:port");
        httpRequest.setHeader("Connection", "Keep-Alive");
        httpRequest.setHeader("User-Agent", "Apache-HttpClient/4.3.5 (java 1.5)");
        httpRequest.setHeader("Accept-Encoding", "gzip,deflate");
        return httpRequest;
    }
}
