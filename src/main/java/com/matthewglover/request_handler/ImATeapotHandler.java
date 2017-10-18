package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class ImATeapotHandler extends RequestHandler {

    private final String teaRequestPath = "/tea";
    private final String coffeeRequestPath = "/coffee";

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath(coffeeRequestPath);
        addHandledPath(teaRequestPath);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return isCoffeeRequest(request) ? handleCoffeeRequest() : handleTeaRequest();
    }

    private boolean isCoffeeRequest(HttpRequest request) {
        return request.getPath().equals(coffeeRequestPath);
    }

    private HttpResponse handleCoffeeRequest() {
        return HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
    }

    private HttpResponse handleTeaRequest() {
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }
}
