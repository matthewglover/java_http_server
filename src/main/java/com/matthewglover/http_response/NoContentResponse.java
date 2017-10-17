package com.matthewglover.http_response;

public class NoContentResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.NO_CONTENT);
    }
}
