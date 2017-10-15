package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HttpRequestBuilder {

    private final ArrayList<String> lines = new ArrayList<>();
    private final LoggerFactory loggerFactory;
    private final BufferedReader bufferedReader;
    private final Logger logger;
    private String firstLine;
    private HttpRequest request;

    public HttpRequestBuilder(InputStream inputStream, LoggerFactory loggerFactory) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.loggerFactory = loggerFactory;
        this.logger = loggerFactory.getLogger(HttpRequestBuilder.class.getName());
    }

    public HttpRequest build() throws IOException {
        buildRequest();
        if (hasContent()) {
            buildContent();
        }
        return request;
    }

    private void buildRequest() throws IOException {
        getRawRequestDetails();
        buildBasicRequest();
        processHeaderLines();
    }

    private void getRawRequestDetails() throws IOException {
        String line;
        while((line = bufferedReader.readLine()).length() > 0) {
            lines.add(line);
        }
    }

    private void buildBasicRequest() throws IOException {
        processFirstLine();
        request = HttpRequestFactory.get(getMethod(), loggerFactory);
        request.setPath(getPath());
        request.setVersion(getVersion());
    }

    private void processFirstLine() throws IOException {
        firstLine = lines.get(0);
        logger.info(firstLine);
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
        for (String headerLine : lines.subList(1, lines.size())) {
            buildHeader(headerLine);
        }
    }

    private void buildHeader(String headerLine) {
        String[] parts = getHeaderParts(headerLine);
        logger.info(headerLine);
        request.setHeader(parts[0], parts[1]);
    }

    private String[] getHeaderParts(String headerLine) {
        return headerLine.split(":\\s+");
    }

    private boolean hasContent() {
        return request.getHeader("Content-Length") != null &&
                Integer.parseInt(request.getHeader("Content-Length")) > 0 &&
                request.getMethod() != HttpRequestMethod.PATCH;
    }

    private void buildContent() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int byteCount = Integer.parseInt(request.getHeader("Content-Length"));

        for (int count = 0; count < byteCount; count++) {
            char ch = (char) bufferedReader.read();
            stringBuilder.append(ch);
        }
        request.setContent(stringBuilder.toString());
    }
}
