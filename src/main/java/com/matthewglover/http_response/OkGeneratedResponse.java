package com.matthewglover.http_response;

public class OkGeneratedResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.OK);
    }
}
