package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class CookieHandler extends RequestHandler {

    private final String chocolateCookiePath = "/cookie";
    private final String eatCookiePath = "/eat_cookie";

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath(chocolateCookiePath);
        addHandledPath(eatCookiePath);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        if (isCookieRequest(request)) {
            return buildSetCookieResponse(request, getOkResponse());
        } else {
            return setResponseContent(getOkResponse(), "mmmm chocolate");
        }
    }

    private HttpResponse getOkResponse() {
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private boolean isCookieRequest(HttpRequest request) {
        return request.getBasePath().equals("/cookie");
    }

    private HttpResponse buildSetCookieResponse(HttpRequest request, HttpResponse response) {
        response.setHeader("Set-Cookie", request.getQueryParam("type"));
        setResponseContent(response, "Eat");
        return response;
    }

    private HttpResponse setResponseContent(HttpResponse response, String content) {
        response.setContent(content);
        response.setContentLengthHeader();
        return response;
    }
}
