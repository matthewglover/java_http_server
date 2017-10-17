package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

import java.util.Base64;

public class BasicAuthHandler extends RequestHandler {

    private final String username = "admin";
    private final String password = "hunter2";
    private String content = "";

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledMethodType(HttpRequestMethod.PUT);
        addHandledMethodType(HttpRequestMethod.HEAD);
        addHandledPath("/logs");
        addHandledPath("/log");
        addHandledPath("/these");
        addHandledPath("/requests");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        if (isLogsAccessRequest(request)) return handleLogsAccessRequest(request);
        return handleLoggableRequest(request);
    }

    private HttpResponse handleLoggableRequest(HttpRequest request) {
        content += request.requestLineToString() + "\r\n";
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }

    private HttpResponse handleLogsAccessRequest(HttpRequest request) {
        if (isRequestUnauthorized(request)) return handleUnauthorizedRequest(request);
        else return getLogsResponse(request);
    }

    private HttpResponse handleUnauthorizedRequest(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);
        response.setHeader("WWW-Authenticate", "Basic realm=\"Logs\"");
        return response;
    }

    private HttpResponse getLogsResponse(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(content);
        response.setContentLengthHeader();
        return response;
    }

    private boolean isLogsAccessRequest(HttpRequest request) {
        return request.getPath().equals("/logs");
    }

    private boolean isRequestUnauthorized(HttpRequest request) {
        String authHeader = request.getHeader("Authorization");
        return isInValidHeader(authHeader) || isInvalidCredentials(authHeader);
    }

    private boolean isInValidHeader(String authHeader) {
        return authHeader == null || isMalformedHeader(authHeader);
    }

    private boolean isMalformedHeader(String authHeader) {
        return !authHeader.matches("^\\s*Basic\\s+.+$");
    }

    private boolean isInvalidCredentials(String authHeader) {
        String[] credentials = getCredentials(authHeader);
        return isInValidToken(credentials[0], username) || isInValidToken(credentials[1], password);
    }

    private String[] getCredentials(String authHeader) {
        String encodedString = authHeader.split("\\s+")[1];
        return decodeString(encodedString).split(":");
    }

    private String decodeString(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }

    private boolean isInValidToken(String token, String referenceValue) {
        return !token.equals(referenceValue);
    }
}
