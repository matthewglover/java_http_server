package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class GetRequest extends HttpRequest {

    public GetRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.GET);
    }
}
