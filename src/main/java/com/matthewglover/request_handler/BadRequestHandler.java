package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class BadRequestHandler extends RequestHandler {

    @Override
    public void setup() {
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST);
    }

    @Override
    public boolean handles(HttpRequest request) {
        return request.getMethod().equals(HttpRequestMethod.INVALID_METHOD);
    }
}
