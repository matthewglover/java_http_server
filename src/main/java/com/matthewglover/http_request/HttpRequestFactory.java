package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class HttpRequestFactory {
    public static HttpRequest get(HttpRequestMethod httpRequestMethod, LoggerFactory loggerFactory) {
        switch (httpRequestMethod) {
            case OPTIONS: return new OptionsRequest(loggerFactory);
            case HEAD: return new HeadRequest(loggerFactory);
            case GET: return new GetRequest(loggerFactory);
            default: return null;
        }
    }
}
