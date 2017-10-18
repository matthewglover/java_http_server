package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

import java.util.ArrayList;
import java.util.List;

public class RequestRouter {

    private final ArrayList<RequestHandler> handlers = new ArrayList<>();

    public void addHandlers(List<RequestHandler> handlers) {
        for (RequestHandler handler : handlers) {
            addHandler(handler);
        }
    }

    public void addHandler(RequestHandler handler) {
        handlers.add(handler);
    }

    public HttpResponse handleRequest(HttpRequest request) {
        for (RequestHandler handler : handlers) {
            if (handler.handles(request)) return handler.getResponse(request);
        }

        return HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
    }
}
