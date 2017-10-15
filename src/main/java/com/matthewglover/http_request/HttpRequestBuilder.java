package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestBuilder {
    private final LoggerFactory loggerFactory;
    private final BufferedReader bufferedReader;
    private String firstLine;
    private HttpRequest request;

    public HttpRequestBuilder(InputStream inputStream, LoggerFactory loggerFactory) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.loggerFactory = loggerFactory;
    }

    public HttpRequest build() throws IOException {
        buildBasicRequest();
        processHeaderLines();
        if (request.getHeader("Content-Length") != null) processContent("");
        return request;
    }

    private void processContent(String content) throws IOException {
        String line = bufferedReader.readLine();
        if (line.length() > 0) {
            processContent(content + line);
        } else {
            request.setContent(content);
        }
    }


    private void buildBasicRequest() throws IOException {
        processFirstLine();
        request = HttpRequestFactory.get(getMethod(), loggerFactory);
        request.setPath(getPath());
        request.setVersion(getVersion());
    }

    private void processFirstLine() throws IOException {
        firstLine = bufferedReader.readLine();
    }

    public HttpRequestMethod getMethod() {
        return HttpRequestMethod.parse(getRequestElements()[0]);
    }

    public String getPath() {
        return getRequestElements()[1];
    }

    public String getVersion() {
        return getRequestElements()[2];
    }

    private String[] getRequestElements() {
        return firstLine.split("\\s+");
    }

    private void processHeaderLines() throws IOException {
        String headerLine = bufferedReader.readLine();
        if (headerLine.length() > 0) {
            buildHeader(headerLine);
            processHeaderLines();
        }
    }

    private void buildHeader(String headerLine) {
        String[] parts = getHeaderParts(headerLine);
        request.setHeader(parts[0], parts[1]);
    }

    private String[] getHeaderParts(String headerLine) {
        return headerLine.split(":\\s+");
    }
}
