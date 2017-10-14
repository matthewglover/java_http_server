package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.*;

public class BadRequestResponseTest {

    private final HttpResponse badRequestResponse = HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST);

    @Test
    public void createsBadRequestResponse() {
        assertTrue(badRequestResponse instanceof BadRequestResponse);
    }

    @Test
    public void hasResponseTypeOfOK() {
        assertEquals(HttpResponseType.BAD_REQUEST, badRequestResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() {
        assertEquals("", badRequestResponse.getContent());
    }
}