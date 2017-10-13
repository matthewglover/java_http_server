package com.matthewglover.http_response;

public class ImATeapotResponse extends HttpResponse {
    public ImATeapotResponse() {
        super();
    }

    @Override
    public void setup() {
        setResponseType(HttpResponseType.IM_A_TEAPOT);
        setContent("I'm a teapot");
        setContentLengthHeader();
    }
}
