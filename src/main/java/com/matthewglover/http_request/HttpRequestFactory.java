package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class HttpRequestFactory {
    public static HttpRequest get(HttpRequestMethod httpRequestMethod, LoggerFactory loggerFactory) {
        switch (httpRequestMethod) {
            case OPTIONS: return buildOptionsRequest(loggerFactory);
            case HEAD: return buildHeadRequest(loggerFactory);
            case GET:
            default: return buildGet(loggerFactory);
        }
    }

    private static HttpRequest buildOptionsRequest(LoggerFactory loggerFactory) {
        OptionsRequest optionsRequest = new OptionsRequest(loggerFactory);
        return optionsRequest;
    }

    private static HttpRequest buildHeadRequest(LoggerFactory loggerFactory) {
        HeadRequest headRequest = new HeadRequest(loggerFactory);
        return headRequest;
    }

    private static HttpRequest buildGet(LoggerFactory loggerFactory) {
        HttpRequest httpRequest = new HttpRequest(loggerFactory);
        httpRequest.setMethod(HttpRequestMethod.GET);
        return httpRequest;
    }
}
