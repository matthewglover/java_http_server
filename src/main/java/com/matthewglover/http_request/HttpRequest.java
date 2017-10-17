package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class HttpRequest {

    public final Logger logger;
    private PathDetails pathDetails;
    private HttpRequestMethod method;
    private Map<String, String> headers = new HashMap<>();
    private String path;
    private String version;
    private String content;
    private String raw;

    public HttpRequest(HttpRequestMethod httpRequestMethod, LoggerFactory loggerFactory) {
        method = httpRequestMethod;
        logger = loggerFactory.getLogger(HttpRequest.class.getName());
        setVersion("HTTP/1.1");
        setup();
    }

    public HttpRequest(LoggerFactory loggerFactory) {
        this(null, loggerFactory);
    }

    public void setup() {}

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
        buildPathDetails();
    }

    private void buildPathDetails() {
        this.pathDetails = new PathDetails(path);
        pathDetails.parse();
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

    public String requestLineToString() {
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
