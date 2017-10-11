package com.matthewglover.http_request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static String CRLF = "\r\n";

    private HttpRequestMethod method;
    private Map<String, String> headers = new HashMap<>();
    private String path;
    private String version;

    public void setMethod(HttpRequestMethod method) {
        this.method = method;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public ArrayList<String> toRaw() {
        ArrayList<String> rawRequest = new ArrayList<>();
        rawRequest.add(requestLineToString());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            rawRequest.add(headerToString(header));
        }
        return rawRequest;
    }

    @Override
    public String toString() {
        return requestLineToString() + CRLF + headersToString() + CRLF;
    }

    private String requestLineToString() {
        return getMethod().toString() + " " + getPath() + " " + getVersion();
    }

    private String headersToString() {
        String headersString = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            headersString += headerToString(header) + CRLF;
        }
        return headersString;
    }

    private String headerToString(Map.Entry<String, String> header) {
        return header.getKey() + ": " + header.getValue();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
