package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class FormDataHandler extends RequestHandler {

    @Override
    public void setup() {
       addHandledMethodType(HttpRequestMethod.POST);
       addHandledMethodType(HttpRequestMethod.PUT);
       addHandledMethodType(HttpRequestMethod.GET);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }
}
