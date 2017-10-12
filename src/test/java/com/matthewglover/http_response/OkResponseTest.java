package com.matthewglover.http_response;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class OkResponseTest {

    private final HttpResponse okResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);

    public OkResponseTest() throws UnsupportedEncodingException {
    }

    @Test
    public void createsOkResponse() {
        assertTrue(okResponse instanceof OkResponse);
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
