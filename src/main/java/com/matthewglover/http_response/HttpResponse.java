package com.matthewglover.http_response;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpResponse {

    public static String CRLF = "\r\n";

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

    public long getContentLength() {
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

    public void sendResponseOverSocket(OutputStream outputStream) throws Exception {
        DataOutputStream dataStream = new DataOutputStream(outputStream);
        dataStream.writeBytes(toString());
    }

    public String getStatusLine() {
        return responseType.toHeader() + CRLF;
    }

    public String headersToString() {
        String headersString = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            headersString += header.getKey() + ": " + header.getValue() + CRLF;
        }
        return headersString;
    }

    @Override
    public String toString() {
        String output = getStatusLine() + headersToString();
        if (!content.isEmpty()) output += CRLF + content;
        output += CRLF;
        return output;
    }
}
