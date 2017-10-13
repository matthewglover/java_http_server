package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class SimpleOkHandler extends RequestHandler {

    @Override
    public void setup() {
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        HttpResponse response  = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(request.requestLineToString());
        response.setContentLengthHeader();
        return response;
    }
}
