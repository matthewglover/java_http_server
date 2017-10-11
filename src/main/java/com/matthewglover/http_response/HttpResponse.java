package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static String CRLF = "\r\n";

    private HttpResponseType responseType;
    private String content = "";
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse() throws UnsupportedEncodingException {
        setContentLengthHeader();
    }

    public void setResponseType(HttpResponseType responseType) {
        this.responseType = responseType;
    }

    public HttpResponseType getResponseType() {
        return responseType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    private int getContentLength() throws UnsupportedEncodingException {
        return content.getBytes("UTF-8").length;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setContentLengthHeader() throws UnsupportedEncodingException {
        setHeader("Content-Length", getContentLength() + "");
    }

    @Override
    public String toString() {
        ArrayList<String> responseElements = new ArrayList<>();
        responseElements.add(responseType.toHeader());
        responseElements.add(headersToString());
        if (!content.isEmpty()) responseElements.add(content);
        return String.join(CRLF, responseElements) + CRLF;
    }

    private String headersToString() {
        String headersString = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            headersString += header.getKey() + ": " + header.getValue() + CRLF;
        }
        return headersString;
    }
}
