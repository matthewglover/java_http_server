package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

import java.util.Base64;

public class BasicAuthHandler extends RequestHandler {

    private String username;
    private String password;

    @Override
    public void setup() {
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);
    }

    @Override
    public boolean handles(HttpRequest request) {
        return super.handles(request) && isRequestUnauthorized(request);
    }

    public void addAuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private boolean isRequestUnauthorized(HttpRequest request) {
        String authHeader = request.getHeader("Authorization");
        return isInValidHeader(authHeader) || isInvalidCredentials(authHeader);
    }

    private boolean isInValidHeader(String authHeader) {
        return authHeader == null || isMalformedHeader(authHeader);
    }

    private boolean isMalformedHeader(String authHeader) {
        return !authHeader.matches("^Basic\\s+\\w+$");
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
