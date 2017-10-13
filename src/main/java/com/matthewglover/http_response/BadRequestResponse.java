package com.matthewglover.http_response;

public class BadRequestResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.BAD_REQUEST);
    }
}
