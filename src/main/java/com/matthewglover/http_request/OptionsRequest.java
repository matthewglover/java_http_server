package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class OptionsRequest extends HttpRequest {

    public OptionsRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.OPTIONS);
    }
}
