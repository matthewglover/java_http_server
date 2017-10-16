package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class FormDataHandler extends RequestHandler {

    private String temporalState;

    @Override
    public void setup() {
       addHandledMethodType(HttpRequestMethod.POST);
       addHandledMethodType(HttpRequestMethod.PUT);
       addHandledMethodType(HttpRequestMethod.GET);
       addHandledMethodType(HttpRequestMethod.DELETE);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        if (request.getMethod() == HttpRequestMethod.GET) {
            return handleGetRequest(request);
        } else if (request.getMethod() == HttpRequestMethod.DELETE) {
            return handleDeleteRequest();
        } else {
            return handleUpdateRequest(request);
        }
    }

    private HttpResponse handleDeleteRequest() {
        temporalState = null;
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private HttpResponse handleUpdateRequest(HttpRequest request) {
        if (request.hasContent()) {
            temporalState = request.getContent();
        }
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private HttpResponse handleGetRequest(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        if (temporalState != null) {
            response.setContent(temporalState);
            response.setContentLengthHeader();
        }
        return response;
    }
}
