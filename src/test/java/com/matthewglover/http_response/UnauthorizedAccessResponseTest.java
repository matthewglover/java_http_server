package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnauthorizedAccessResponseTest {

    private final HttpResponse unauthorizedAccessResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);

    @Test
    public void createsUnauthorizedAccessResponse() {
        assertTrue(unauthorizedAccessResponse instanceof UnauthorizedAccessResponse);
    }

    @Test
    public void hasResponseTypeOfOK() {
        assertEquals(HttpResponseType.UNAUTHORIZED_ACCESS, unauthorizedAccessResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() {
        assertEquals("", unauthorizedAccessResponse.getContent());
    }
}