package com.matthewglover.http_response;

public enum HttpResponseType {
    OK(200, "OK"),
    NO_CONTENT(204, "No Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    REDIRECT(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED_ACCESS(401, "Unauthorized Access"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_FOUND(404, "Not Found"),
    IM_A_TEAPOT(418, "I'm a Teapot");

    private final int code;
    private final String description;

    HttpResponseType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toHeader() {
        return String.format("HTTP/1.1 %d %s", this.code, this.description);
    }
}
