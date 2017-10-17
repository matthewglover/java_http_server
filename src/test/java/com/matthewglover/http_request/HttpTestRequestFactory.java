package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactoryDouble;

public class HttpTestRequestFactory {

    public static HttpRequest get(HttpRequestMethod method) {
        return HttpRequestFactory.get(method, new LoggerFactoryDouble());
    }
}
