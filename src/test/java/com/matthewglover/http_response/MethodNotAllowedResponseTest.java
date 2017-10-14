package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.*;

public class MethodNotAllowedResponseTest {

    private final HttpResponse methodNotAllowedResponse = HttpResponseFactory.get(HttpResponseTemplate.METHOD_NOT_ALLOWED);

    @Test
    public void createsBadRequestResponse() {
        assertTrue(methodNotAllowedResponse instanceof MethodNotAllowedResponse);
    }

    @Test
    public void hasResponseTypeOfOK() {
        assertEquals(HttpResponseType.METHOD_NOT_ALLOWED, methodNotAllowedResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() {
        assertEquals("", methodNotAllowedResponse.getContent());
    }
}