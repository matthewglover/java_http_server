package com.matthewglover.http_request;


import com.matthewglover.util.LoggerFactory;

import java.util.ArrayList;

public class HttpRequestParser {

    private final ArrayList<String> rawRequest;
    private final LoggerFactory loggerFactory;
    private HttpRequest httpRequest;

    public HttpRequestParser(ArrayList<String> rawRequest, LoggerFactory loggerFactory) {
        this.rawRequest = rawRequest;
        this.loggerFactory = loggerFactory;
    }

    public void parse() {
        HttpRequestMethod requestMethod = HttpRequestMethod.parse(getRequestMethodName());
        httpRequest = HttpRequestFactory.get(requestMethod, loggerFactory);
        httpRequest.parse(rawRequest);
    }

    public HttpRequest getRequest() {
        return httpRequest;
    }

    private String getRequestMethodName() {
        return getRequestElements()[0];
    }

    private String[] getRequestElements() {
        return getRawRequestLine().split("\\s+");
    }

    private String getRawRequestLine() {
        return rawRequest.get(0);
    }
}
