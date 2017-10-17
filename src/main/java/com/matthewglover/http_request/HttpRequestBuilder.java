package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestBuilder {

    private static final String CRLF = "\r\n";
    private final ArrayList<String> lines = new ArrayList<>();
    private final LoggerFactory loggerFactory;
    private final BufferedReader bufferedReader;
    private String firstLine;
    private HttpRequest request;
    private String content;

    public HttpRequestBuilder(InputStream inputStream, LoggerFactory loggerFactory) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.loggerFactory = loggerFactory;
    }

    public HttpRequest build() throws IOException {
        buildRequestDetails();
        if (hasContent()) setContent();
        buildRaw();
        return request;
    }

    private void buildRequestDetails() throws IOException {
        getRawRequestDetails();
        processFirstLine();
        processHeaderLines();
    }

    private void getRawRequestDetails() throws IOException {
        String line;
        while((line = bufferedReader.readLine()).length() > 0) {
            lines.add(line);
        }
    }

    private void processFirstLine() throws IOException {
        firstLine = lines.get(0);
        String[] requestParts = getRequestParts(firstLine);
        request = HttpRequestFactory.get(getMethod(requestParts[0]), loggerFactory);
        request.setPath(requestParts[1]);
        request.setVersion(requestParts[2]);
    }

    private String[] getRequestParts(String firstLine) {
        return firstLine.split("\\s+");
    }

    private HttpRequestMethod getMethod(String methodName) {
        return HttpRequestMethod.parse(methodName);
    }

    private void processHeaderLines() throws IOException {
        for (String headerLine : getHeaderLines()) {
            buildHeader(headerLine);
        }
    }

    private List<String> getHeaderLines() {
        return lines.subList(1, lines.size());
    }

    private void buildHeader(String headerLine) {
        String[] parts = getHeaderParts(headerLine);
        request.setHeader(parts[0], parts[1]);
    }

    private String[] getHeaderParts(String headerLine) {
        return headerLine.split(":\\s+");
    }

    private boolean hasContent() {
        return request.getHeader("Content-Length") != null &&
                getContentSize() > 0;
    }

    private int getContentSize() {
        return Integer.parseInt(request.getHeader("Content-Length"));
    }

    private void setContent() throws IOException {
        processContent();
        request.setContent(content);
    }

    private void processContent() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int byteCount = getContentSize();

        for (int count = 0; count < byteCount; count++) {
            char ch = (char) bufferedReader.read();
            stringBuilder.append(ch);
        }

        content = stringBuilder.toString();
    }

    private void buildRaw() {
        String rawRequest = firstLine + CRLF;

        for (String headerLine : getHeaderLines()) {
            rawRequest += headerLine + CRLF;
        }

        rawRequest += CRLF;

        if (hasContent()) rawRequest += content;

        request.setRaw(rawRequest);
    }
}
