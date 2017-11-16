package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class RedirectHandler extends RequestHandler {

    public RedirectHandler() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath("/redirect");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.REDIRECT);
        response.setHeader("Location", "/");
        return response;
    }
}
