package com.matthewglover.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static String CRLF = "\r\n";

    private HttpRequestType requestType;
    private Map<String, String> headers = new HashMap<>();

    public void setRequestType(HttpRequestType requestType) {
        this.requestType = requestType;
    }

    public HttpRequestType getRequestType() {
        return requestType;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return requestType.toHeader() + CRLF + headersToString() + CRLF;
    }

    private String headersToString() {
        String headersString = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            headersString += header.getKey() + ": " + header.getValue() + CRLF;
        }
        return headersString;
    }
}
