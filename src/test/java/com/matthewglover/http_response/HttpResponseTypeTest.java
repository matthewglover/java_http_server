package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpResponseTypeTest {
    @Test
    public void get200HeaderIsCorrect() {
        assertEquals("HTTP/1.1 200 OK", HttpResponseType.OK.toHeader());
    }

    @Test
    public void noContentHeaderIsCorrect() {
        assertEquals("HTTP/1.1 204 No Content", HttpResponseType.NO_CONTENT.toHeader());
    }

    @Test
    public void partialContentHeaderIsCorrect() {
        assertEquals("HTTP/1.1 206 Partial Content", HttpResponseType.PARTIAL_CONTENT.toHeader());
    }

    @Test
    public void redirectHeaderIsCorrect() {
        assertEquals("HTTP/1.1 302 Found", HttpResponseType.REDIRECT.toHeader());
    }

    @Test
    public void unauthorizedHeaderIsCorrect() {
        assertEquals("HTTP/1.1 401 Unauthorized Access", HttpResponseType.UNAUTHORIZED_ACCESS.toHeader());
    }

    @Test
    public void badRequestHeaderIsCorrect() {
        assertEquals("HTTP/1.1 400 Bad Request", HttpResponseType.BAD_REQUEST.toHeader());
    }

    @Test
    public void methodNotAllowedHeaderIsCorrect() {
        assertEquals("HTTP/1.1 405 Method Not Allowed", HttpResponseType.METHOD_NOT_ALLOWED.toHeader());
    }

    @Test
    public void notFoundHeaderIsCorrect() {
        assertEquals("HTTP/1.1 404 Not Found", HttpResponseType.NOT_FOUND.toHeader());
    }

    @Test
    public void imATeapotHeaderIsCorrect() {
        assertEquals("HTTP/1.1 418 I'm a Teapot", HttpResponseType.IM_A_TEAPOT.toHeader());
    }
}