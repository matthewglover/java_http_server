package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class CookieHandler extends RequestHandler {

    private final String setCookiePath = "/cookie";
    private final String eatCookiePath = "/eat_cookie";
    private final String setCookieContent = "Eat";
    private final String eatCookieContent = "mmmm chocolate";

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath(setCookiePath);
        addHandledPath(eatCookiePath);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return isSetCookieRequest(request) ? handleSetCookieResponse(request) : handleEatCookeResponse();
    }

    private boolean isSetCookieRequest(HttpRequest request) {
        return request.getBasePath().equals(setCookiePath);
    }

    private HttpResponse handleSetCookieResponse(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setHeader("Set-Cookie", request.getQueryParam("type"));
        response.setContent(setCookieContent);
        return response;
    }

    private HttpResponse handleEatCookeResponse() {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(eatCookieContent);
        return response;
    }
}
