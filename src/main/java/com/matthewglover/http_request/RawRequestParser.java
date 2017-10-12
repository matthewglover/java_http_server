package com.matthewglover.http_request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RawRequestParser {

    private final ArrayList<String> rawRequest;

    public RawRequestParser(ArrayList<String> rawRequest) {
        this.rawRequest = rawRequest;
    }

    public String getMethod() {
        return getRequestElements()[0];
    }

    public String getPath() {
        return getRequestElements()[1];
    }

    public String getVersion() {
        return getRequestElements()[2];
    }

    public List<String[]> getRawRequestHeaders() {
        return getHeaderArray()
                .stream()
                .map(this::parseHeader)
                .collect(Collectors.toList());
    }

    private String[] getRequestElements() {
        return getRequestLine().split("\\s+");
    }

    private String getRequestLine() {
        return rawRequest.get(0);
    }

    private ArrayList<String> getHeaderArray() {
        return new ArrayList<>(rawRequest.subList(1, rawRequest.size()));
    }

    private String[] parseHeader(String header) {
        return header.split(":\\s+");
    }
}
