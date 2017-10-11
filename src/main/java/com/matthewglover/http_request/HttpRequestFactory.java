package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class HttpRequestFactory {
    public static HttpRequest get(HttpRequestMethod httpRequestMethod, LoggerFactory loggerFactory) {
        switch (httpRequestMethod) {
            case HEAD: return buildHeadRequest(loggerFactory);
            case GET:
            default: return buildGet(loggerFactory);
        }
    }

    private static HttpRequest buildHeadRequest(LoggerFactory loggerFactory) {
        HttpHeadRequest httpHeadRequest = new HttpHeadRequest(loggerFactory);
        return httpHeadRequest;
    }

    private static HttpRequest buildGet(LoggerFactory loggerFactory) {
        HttpRequest httpRequest = new HttpRequest(loggerFactory);
        httpRequest.setMethod(HttpRequestMethod.GET);
        return httpRequest;
    }
}
