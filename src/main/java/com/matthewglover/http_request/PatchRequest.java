package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class PatchRequest extends HttpRequest {
    public PatchRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.PATCH);
    }
}
