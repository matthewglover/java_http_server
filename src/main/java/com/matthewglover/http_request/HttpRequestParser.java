package com.matthewglover.http_request;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestParser {

    private final BufferedReader bufferedReader;
    private final ArrayList<String> lines = new ArrayList<>();

    private HttpRequestMethod requestMethod;
    private String requestPath;
    private String requestVersion;
    private Map<String, String> requestHeaders = new HashMap<>();
    private String requestContent;

    public HttpRequestParser(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public HttpRequest build() throws IOException {
        processRequestDetails();
        if (hasContent()) processContent();

        return new HttpRequest(requestMethod, requestPath, requestVersion, requestHeaders, requestContent);
    }

    private void processRequestDetails() throws IOException {
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
        String firstLine = lines.get(0);
        String[] requestParts = firstLine.split("\\s+");
        requestMethod = HttpRequestMethod.parse(requestParts[0]);
        requestPath = requestParts[1];
        requestVersion = requestParts[2];
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
        requestHeaders.put(parts[0], parts[1]);
    }

    private String[] getHeaderParts(String headerLine) {
        return headerLine.split(":\\s+");
    }

    private boolean hasContent() {
        return requestHeaders.get("Content-Length") != null &&
                getContentSize() > 0;
    }

    private int getContentSize() {
        return Integer.parseInt(requestHeaders.get("Content-Length"));
    }

    private void processContent() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int byteCount = getContentSize();

        for (int count = 0; count < byteCount; count++) {
            char ch = (char) bufferedReader.read();
            stringBuilder.append(ch);
        }

        requestContent = stringBuilder.toString();
    }
}
