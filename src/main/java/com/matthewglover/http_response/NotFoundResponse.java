package com.matthewglover.http_response;

public class NotFoundResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.NOT_FOUND);
    }
}
