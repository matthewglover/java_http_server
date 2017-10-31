package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

import static com.matthewglover.http_request.HttpRequestMethod.*;

public class FormDataHandler extends RequestHandler {

    private String temporalState;

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.POST);
        addHandledMethodType(HttpRequestMethod.PUT);
        addHandledMethodType(GET);
        addHandledMethodType(HttpRequestMethod.DELETE);
        addHandledPath("/form");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        switch (request.getMethod()) {
            case POST:
            case PUT:
                return handleUpdateRequest(request);
            case GET:
                return handleGetRequest();
            case DELETE:
                return handleDeleteRequest();
            default:
                return null;
        }
    }

    private HttpResponse handleUpdateRequest(HttpRequest request) {
        if (request.hasContent()) setTemporalState(request.getContent());
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private void setTemporalState(String newState) {
        temporalState = newState;
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

    private HttpResponse handleDeleteRequest() {
        clearTemporalState();
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private void clearTemporalState() {
        temporalState = null;
    }
}
