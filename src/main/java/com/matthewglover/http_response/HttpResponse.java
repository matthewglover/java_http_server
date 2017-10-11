package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponseType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static String CRLF = "\r\n";

    private HttpResponseType responseType;
    private String content;
    private Map<String, String> headers = new HashMap<>();

    public void setResponseType(HttpResponseType responseType) {
        this.responseType = responseType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentLengthHeader() throws UnsupportedEncodingException {
       setHeader("Content-Length", getContentLength() + "");
    }

    private int getContentLength() throws UnsupportedEncodingException {
        return content.getBytes("UTF-8").length;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        ArrayList<String> responseElements = new ArrayList<>();
        responseElements.add(responseType.toHeader());
        if (!headers.isEmpty()) responseElements.add(headersToString());
        responseElements.add(content == null ? "" : content);
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
