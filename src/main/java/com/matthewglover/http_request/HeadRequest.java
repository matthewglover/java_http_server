package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class HeadRequest extends HttpRequest {

    public HeadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.HEAD);
    }
}
