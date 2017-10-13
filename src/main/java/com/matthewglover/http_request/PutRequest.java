package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class PutRequest extends HttpRequest {
    public PutRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.PUT);
    }
}
