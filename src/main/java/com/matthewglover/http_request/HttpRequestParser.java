package com.matthewglover.http_request;


import com.matthewglover.util.LoggerFactory;

import java.util.ArrayList;

public class HttpRequestParser {

    private final LoggerFactory loggerFactory;
    private final RawRequestParser rawRequestParser;
    private HttpRequest httpRequest;

    public HttpRequestParser(ArrayList<String> rawRequest, LoggerFactory loggerFactory) {
        rawRequestParser = new RawRequestParser(rawRequest);
        this.loggerFactory = loggerFactory;
    }

    public void parse() {
        HttpRequestMethod requestMethod = HttpRequestMethod.parse(rawRequestParser.getMethod());
        System.out.println(requestMethod instanceof HttpRequestMethod);
//        HttpRequestMethod requestMethod = HttpRequestMethod.GET;
        httpRequest = HttpRequestFactory.get(requestMethod, loggerFactory);
        httpRequest.parse(rawRequestParser);
    }

    public HttpRequest getRequest() {
        return httpRequest;
    }
}
