package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class OptionsAllowSelectedHandler extends RequestHandler {

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.OPTIONS);
        addHandledPath("/method_options2");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_SELECTED);
    }
}
