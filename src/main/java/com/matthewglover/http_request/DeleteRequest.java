package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class DeleteRequest extends HttpRequest {
    public DeleteRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.DELETE);
    }
}
