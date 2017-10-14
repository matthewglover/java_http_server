package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class ParametersHandler extends RequestHandler {

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath("/parameters");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(buildContent(request));
        response.setContentLengthHeader();
        return response;
    }

    private String buildContent(HttpRequest request) {
        return "variable_1 = " + request.getQueryParam("variable_1") + "\n" +
                "variable_2 = " + request.getQueryParam("variable_2");
    }
}
