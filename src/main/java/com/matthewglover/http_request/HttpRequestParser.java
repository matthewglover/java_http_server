package com.matthewglover.http_request;


import com.matthewglover.util.LoggerFactory;

import java.util.ArrayList;

public class HttpRequestParser {

    private final ArrayList<String> rawRequest;
    private final LoggerFactory loggerFactory;
    private HttpRequest httpRequest;
    private Exception parseException;

    public HttpRequestParser(ArrayList<String> rawRequest, LoggerFactory loggerFactory) {
        this.rawRequest = rawRequest;
        this.loggerFactory = loggerFactory;
    }

    public void parse() {
        try {
            parseRequestLine();
            parseRequestHeaders();
        } catch (Exception parseException) {
            this.parseException = parseException;
        }
    }

    public HttpRequest getRequest() {
        return httpRequest;
    }

    private void parseRequestLine() {
        String[] requestElements = getRawRequestLine().split("\\s+");
        createRequest(requestElements[0]);
        setRequestPath(requestElements[1]);
        setRequestVersion(requestElements[2]);
    }

    private String getRawRequestLine() {
        return rawRequest.get(0);
    }

    private void createRequest(String method) {
        httpRequest = HttpRequestFactory.get(HttpRequestMethod.valueOf(method), loggerFactory);
    }

    private void setRequestPath(String path) {
        httpRequest.setPath(path);
    }

    public void setRequestVersion(String version) {
        httpRequest.setVersion(version);
    }

    private void parseRequestHeaders() {
        getRawRequestHeaders().forEach(this::parseRequestHeader);
    }

    private ArrayList<String> getRawRequestHeaders() {
        return new ArrayList<>(rawRequest.subList(1, rawRequest.size()));
    }

    private void parseRequestHeader(String header) {
        String[] headerElements = header.split(":\\s+");
        httpRequest.setHeader(headerElements[0], headerElements[1]);
    }

    public boolean hasErrors() {
        return parseException != null;
    }
}
