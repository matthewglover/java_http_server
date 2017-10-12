package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class BadRequestResponse extends HttpResponse {

    public BadRequestResponse() throws UnsupportedEncodingException {
        super();
    }

    @Override
    public void setup() {
        setResponseType(HttpResponseType.BAD_REQUEST);
    }
}
