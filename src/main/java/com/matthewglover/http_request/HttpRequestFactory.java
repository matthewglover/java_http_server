package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class HttpRequestFactory {
    public static HttpRequest get(HttpRequestMethod httpRequestMethod, LoggerFactory loggerFactory) {
        return new HttpRequest(httpRequestMethod, loggerFactory);
    }
}
