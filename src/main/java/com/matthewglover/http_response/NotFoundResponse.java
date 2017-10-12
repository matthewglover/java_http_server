package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class NotFoundResponse extends HttpResponse {
    public NotFoundResponse() throws UnsupportedEncodingException {
        super();
    }

    @Override
    public void setup() {
        setResponseType(HttpResponseType.NOT_FOUND);
    }
}
