package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

import java.util.ArrayList;

public class RequestRouter {

    private final ArrayList<RequestHandler> handlers = new ArrayList<>();

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
