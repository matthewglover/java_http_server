package com.matthewglover.http_response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpResponse {

    private static String CRLF = "\r\n";

    private HttpResponseType responseType;
    private String content = "";
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse() {
        setContentLengthHeader();
        setup();
    }

    public abstract void setup();

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

    private int getContentLength() {
        try {
            return content.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void setContentLengthHeader() {
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

    public void sendResponseOverSocket(OutputStream outputStream) throws IOException {
        DataOutputStream dataStream = new DataOutputStream(outputStream);
        dataStream.writeBytes(toString());
    }
}
