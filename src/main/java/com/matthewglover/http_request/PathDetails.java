package com.matthewglover.http_request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class PathDetails {
    private final String rawPath;
    private String basePath;
    private String queryString;
    private HashMap<String, String> queryParams = new HashMap<>();
    private String rawQueryString;

    public PathDetails(String rawPath) {
        this.rawPath = rawPath;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getQueryParam(String param) {
        return queryParams.get(param);
    }

    public void parse() {
        String[] pathElements = rawPath.split("\\?");
        buildBasePath(pathElements);
        buildQueryString(pathElements);
        if (isQueryString()) parseQueryParams();
    }

    private void buildBasePath(String[] pathElements) {
        basePath = pathElements[0].trim();
    }

    private void buildQueryString(String[] pathElements) {
        rawQueryString = pathElements.length > 1 ? pathElements[1] : "";
        queryString = decodeString(rawQueryString);
    }

    private String decodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private boolean isQueryString() {
        return rawQueryString.length() > 0;
    }

    private void parseQueryParams() {
        for (String keyValuePair : rawQueryString.split("&")) {
            addQueryParam(keyValuePair.split("="));
        }
    }

    private void addQueryParam(String[] keyValue) {
        queryParams.put(keyValue[0], decodeString(keyValue[1]));
    }
}
