package com.matthewglover.http_request;

import java.util.HashMap;
import java.util.Map;

public class HttpTestRequestBuilder {

    HttpRequestMethod method;
    String path;
    Map<String, String> headers = new HashMap<>();
    String content;

    public HttpTestRequestBuilder setMethod(HttpRequestMethod method) {
        this.method = method;
        return this;
    }

    public HttpTestRequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public HttpTestRequestBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public HttpTestRequestBuilder setHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public HttpRequest build() {
        return new HttpRequest(method, path, null, headers, content);
    }
}
