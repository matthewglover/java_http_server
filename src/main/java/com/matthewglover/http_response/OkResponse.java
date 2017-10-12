package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class OkResponse extends HttpResponse {
    public OkResponse() throws UnsupportedEncodingException {
        super();
    }

    @Override
    public void setup() {
        setResponseType(HttpResponseType.OK);
    }
}
