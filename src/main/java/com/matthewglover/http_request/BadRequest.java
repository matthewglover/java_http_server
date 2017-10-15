package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class BadRequest extends HttpRequest {
    public BadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.INVALID_METHOD);
    }
}
