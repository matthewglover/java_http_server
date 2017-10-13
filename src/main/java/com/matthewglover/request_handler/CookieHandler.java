package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class CookieHandler extends RequestHandler {

    private final String chocolateCookiePath = "/cookie?type=chocolate";
    private final String eatCookiePath = "/eat_cookie";

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath(chocolateCookiePath);
        addHandledPath(eatCookiePath);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(getContent(request.getPath()));
        response.setContentLengthHeader();
        return response;
    }

    private String getContent(String path) {
        return path.equals(chocolateCookiePath) ? "Eat" : "mmmm chocolate";
    }
}
