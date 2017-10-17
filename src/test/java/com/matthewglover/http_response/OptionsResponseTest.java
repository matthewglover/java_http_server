package com.matthewglover.http_response;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptionsResponseTest {

    @Test
    public void createsAllowAllOptionsResponse() throws UnsupportedEncodingException {
        HttpResponse optionsResponse = HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_ALL);
        assertTrue(optionsResponse instanceof OptionsGeneratedResponse);
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", optionsResponse.getHeader("Allow"));
    }

    @Test
    public void createsAllowSelectedOptionsResponse() throws UnsupportedEncodingException {
        HttpResponse optionsResponse = HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_SELECTED);
        assertTrue(optionsResponse instanceof OptionsGeneratedResponse);
        assertEquals("GET,OPTIONS", optionsResponse.getHeader("Allow"));
    }
}