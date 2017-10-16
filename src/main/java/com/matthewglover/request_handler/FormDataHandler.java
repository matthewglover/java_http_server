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
        if (isGet(request)) return handleGetRequest();
        if (isDelete(request)) return handleDeleteRequest();
        else return handleUpdateRequest(request);
    }

    private boolean isGet(HttpRequest request) {
        return request.getMethod() == HttpRequestMethod.GET;
    }

    private HttpResponse handleGetRequest() {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        if (hasTemporalState()) {
            response.setContent(temporalState);
            response.setContentLengthHeader();
        }
        return response;
    }

    private boolean hasTemporalState() {
        return temporalState != null;
    }

    private boolean isDelete(HttpRequest request) {
        return request.getMethod() == HttpRequestMethod.DELETE;
    }

    private HttpResponse handleDeleteRequest() {
        clearTemporalState();
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private String clearTemporalState() {
        return temporalState = null;
    }

    private HttpResponse handleUpdateRequest(HttpRequest request) {
        if (request.hasContent()) setTemporalState(request.getContent());
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private void setTemporalState(String newState) {
        temporalState = newState;
    }
}
