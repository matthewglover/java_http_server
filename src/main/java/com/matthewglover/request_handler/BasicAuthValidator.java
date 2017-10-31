package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;

import java.util.Base64;

class BasicAuthValidator {

    private final String username;
    private final String password;

    public BasicAuthValidator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validate(HttpRequest request) {
        String authHeader = request.getHeader("Authorization");
        return isValidHeader(authHeader) && isValidCredentials(authHeader);
    }

    private boolean isValidHeader(String authHeader) {
        return authHeader != null && isWellFormedHeader(authHeader);
    }

    private boolean isWellFormedHeader(String authHeader) {
        return authHeader.matches("^\\s*Basic\\s+.+$");
    }

    private boolean isValidCredentials(String authHeader) {
        String[] credentials = getCredentials(authHeader);
        return isValidToken(credentials[0], username) && isValidToken(credentials[1], password);
    }

    private String[] getCredentials(String authHeader) {
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
