package com.matthewglover.http_response;

public class OkResponse extends HttpResponse {
    @Override
    public void setup() {
        setResponseType(HttpResponseType.OK);
    }
}
