package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class PostRequest extends HttpRequest {

    public PostRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.POST);
    }
}
