package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class HttpRequest {

    private static String CRLF = "\r\n";
    public final Logger logger;

    private HttpRequestMethod method;
    private Map<String, String> headers = new HashMap<>();
    private String path;
    private String version;

    public HttpRequest(LoggerFactory loggerFactory) {
        logger = loggerFactory.getLogger(HeadRequest.class.getName());
        setVersion("HTTP/1.1");
        setup();
    }

    public abstract void setup();
    public abstract HttpResponse buildResponse() throws UnsupportedEncodingException;

    public void setMethod(HttpRequestMethod method) {
        this.method = method;
    }

    public HttpRequestMethod getMethod() {
        return method;
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

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void parse(ArrayList<String> rawRequest) {
        RawRequestParser parser = new RawRequestParser(rawRequest);
        setPath(parser.getPath());
        setVersion(parser.getVersion());
        parser.getRawRequestHeaders().forEach(this::setHeaderFromPair);
    }

    public String getMethodString() {
        return getMethod().toString();
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
        return getMethodString() + " " + getPath() + " " + getVersion();
    }

    private void setHeaderFromPair(String[] headerPair) {
        this.setHeader(headerPair[0], headerPair[1]);
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
}