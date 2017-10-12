package com.matthewglover.http_response;

public class UnauthorizedAccessResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.UNAUTHORIZED_ACCESS);
    }
}
