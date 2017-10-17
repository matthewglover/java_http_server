package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;

import java.util.ArrayList;

public abstract class RequestHandler {

    private final ArrayList<HttpRequestMethod> handledMethodTypes = new ArrayList<>();
    private final ArrayList<String> handledPaths = new ArrayList<>();

    public RequestHandler() {
        setup();
    }

    public abstract void setup();
    public abstract HttpResponse getResponse(HttpRequest request);

    public void addHandledMethodType(HttpRequestMethod method) {
        handledMethodTypes.add(method);
    }

    public boolean isHandledRequestType(HttpRequest request) {
        return handledMethodTypes.contains(request.getMethod());
    }

    public void addHandledPath(String path) {
        handledPaths.add(path);
    }

    public boolean isHandledPath(HttpRequest request) {
        return handledPaths.contains(request.getBasePath());
    }

    public boolean handles(HttpRequest request) {
        return isHandledRequestType(request) && isHandledPath(request);
    }
}
