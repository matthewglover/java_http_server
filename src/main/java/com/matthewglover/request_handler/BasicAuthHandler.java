package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

public class BasicAuthHandler extends RequestHandler {

    private final String username = "admin";
    private final String password = "hunter2";
    private final String logsAccessPath = "/logs";
    private String temporalContent = "";

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledMethodType(HttpRequestMethod.PUT);
        addHandledMethodType(HttpRequestMethod.HEAD);
        addHandledPath(logsAccessPath);
        addHandledPath("/log");
        addHandledPath("/these");
        addHandledPath("/requests");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return isLoggableRequest(request) ?  handleLoggableRequest(request) : handleLogsAccessRequest(request);
    }

    private boolean isLoggableRequest(HttpRequest request) {
        return !request.getPath().equals(logsAccessPath);
    }

    private HttpResponse handleLoggableRequest(HttpRequest request) {
        updateTemporalContent(request.requestLineToString());
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private void updateTemporalContent(String requestLine) {
        temporalContent += requestLine + "\r\n";
    }

    private HttpResponse handleLogsAccessRequest(HttpRequest request) {
        return isAuthorizedRequest(request) ? buildLogAccessResponse() : handleUnauthorizedRequest();
    }

    private boolean isAuthorizedRequest(HttpRequest request) {
        BasicAuthValidator validator = new BasicAuthValidator(username, password);
        return validator.validate(request);
    }

    private HttpResponse buildLogAccessResponse() {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(temporalContent);
        response.setContentLengthHeader();
        return response;
    }

    private HttpResponse handleUnauthorizedRequest() {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);
        response.setHeader("WWW-Authenticate", "Basic realm=\"Logs\"");
        return response;
    }
}
