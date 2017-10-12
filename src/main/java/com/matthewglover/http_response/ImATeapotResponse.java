package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class ImATeapotResponse extends HttpResponse {
    public ImATeapotResponse() throws UnsupportedEncodingException {
        super();
    }

    @Override
    public void setup() {
        setResponseType(HttpResponseType.IM_A_TEAPOT);
        setContent("I'm a teapot");
        setContentLengthHeader();
    }
}
