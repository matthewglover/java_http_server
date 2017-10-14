package com.matthewglover.http_response;

public class MethodNotAllowedResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.METHOD_NOT_ALLOWED);
    }
}
