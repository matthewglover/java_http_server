package com.matthewglover.http_response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImATeapotResponseTest {

    private final HttpResponse imATeapotResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);

    @Test
    public void createsImATeapotResponse() {
        assertTrue(imATeapotResponse instanceof ImATeapotResponse);
    }

    @Test
    public void hasResponseTypeOfImATeapot() {
        assertEquals(HttpResponseType.IM_A_TEAPOT, imATeapotResponse.getResponseType());
    }

    @Test
    public void createsResponseWithImATeapot() {
        assertEquals("I'm a teapot", imATeapotResponse.getContent());
    }
}
