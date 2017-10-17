package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.*;

public class OkGeneratedResponseTest {

    private final HttpResponse okResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);

    @Test
    public void createsOkResponse() {
        assertTrue(okResponse instanceof OkGeneratedResponse);
    }

    @Test
    public void hasResponseType() {
        assertEquals(HttpResponseType.OK, okResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() {
        assertEquals("", okResponse.getContent());
    }
}
