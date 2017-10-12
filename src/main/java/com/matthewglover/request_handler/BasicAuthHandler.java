package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class BasicAuthHandler {
    private String username;
    private String password;

    public void addUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public HttpResponse handleRequest(HttpRequest request) throws UnsupportedEncodingException {
        if (isAuthenticated(request)) {
            HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
            response.setContent(request.toString());
            response.setContentLengthHeader();
            return response;
        } else {
            return HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);
        }
    }

    private boolean isAuthenticated(HttpRequest request) {
        String authHeader = request.getHeader("Authorization");
        return isValidHeader(authHeader) && isAuthorizedUsernameAndPassword(authHeader);
    }

    private boolean isValidHeader(String authHeader) {
        return authHeader != null && authHeader.matches("^Basic\\s+\\w+$");
    }

    private boolean isAuthorizedUsernameAndPassword(String authHeader) {
        String[] usernameAndPassword = getUsernameAndPassword(authHeader);
        return isValidToken(usernameAndPassword[0], username) && isValidToken(usernameAndPassword[1], password);
    }

    private String[] getUsernameAndPassword(String authHeader) {
        String encodedString = authHeader.split("\\s+")[1];
        return decodeString(encodedString).split(":");
    }

    private String decodeString(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }

    private boolean isValidToken(String token, String referenceValue) {
        return token.equals(referenceValue);
    }
}
