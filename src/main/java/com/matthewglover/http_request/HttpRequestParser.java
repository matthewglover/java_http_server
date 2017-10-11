package com.matthewglover.http_request;


import java.util.ArrayList;

public class HttpRequestParser {
    private final ArrayList<String> rawRequest;
    private final HttpRequest httpRequest = new HttpRequest();
    private HttpRequest request;
    private Exception parseException;

    public HttpRequestParser(ArrayList<String> rawRequest) {
        this.rawRequest = rawRequest;
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
        setRequestMethod(requestElements[0]);
        setRequestPath(requestElements[1]);
        setRequestVersion(requestElements[2]);
    }

    private String getRawRequestLine() {
        return rawRequest.get(0);
    }

    private void setRequestMethod(String method) {
        httpRequest.setMethod(HttpRequestMethod.valueOf(method));
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
