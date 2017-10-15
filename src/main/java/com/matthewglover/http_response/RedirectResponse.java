package com.matthewglover.http_response;

public class RedirectResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.REDIRECT);
    }
}
