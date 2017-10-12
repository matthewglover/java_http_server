package com.matthewglover.http_response;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class NotFoundResponseTest {

    private final HttpResponse notFoundResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);

    public NotFoundResponseTest() throws UnsupportedEncodingException {
    }

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