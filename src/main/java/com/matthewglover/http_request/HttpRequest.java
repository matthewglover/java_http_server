package com.matthewglover.http_request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private PathDetails pathDetails;
    private HttpRequestMethod method;
    private Map<String, String> headers = new HashMap<>();
    private String path;
    private String version;
    private String content;

    public HttpRequest(HttpRequestMethod method) {
        this.method = method;
    }

    public HttpRequest(HttpRequestMethod method, String path, String version, Map<String, String> headers, String content) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = headers;
        this.content = content;
        this.pathDetails = PathDetailsParser.parse(path);
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getRequestLine() {
        return getMethod().toString() + " " + getPath() + " " + getVersion();
    }

    public String getQueryParam(String queryParam) {
        return pathDetails.getQueryParam(queryParam);
    }

    public String getBasePath() {
        return pathDetails.getBasePath();
    }

    public boolean hasContent() {
        return getContent() != null;
    }

    public String getContent() {
        return this.content;
    }
}
