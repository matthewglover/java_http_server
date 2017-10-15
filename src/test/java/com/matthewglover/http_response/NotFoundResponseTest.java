package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotFoundResponseTest {

    private final HttpResponse notFoundResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);

    @Test
    public void createsBadRequestResponse() {
        assertTrue(notFoundResponse instanceof NotFoundResponse);
    }

    @Test
    public void hasResponseTypeOfOK() {
        assertEquals(HttpResponseType.NOT_FOUND, notFoundResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() {
        assertEquals("", notFoundResponse.getContent());
    }
}