package com.matthewglover.http_request;

import com.matthewglover.http_response.*;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class OptionsRequestTest {
    LoggerDouble loggerDouble = new LoggerDouble(null, null);
    LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void allowAllMethodsForMethodOptions() throws UnsupportedEncodingException {
        HttpRequest optionsRequest = HttpRequestFactory.get(HttpRequestMethod.OPTIONS, loggerFactoryDouble);
        optionsRequest.setPath("/method_options");
        HttpResponse actualResponse = optionsRequest.buildResponse();
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        expectedResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }

    @Test
    public void selectivelyAllowMethodsForMethodOptions2() throws UnsupportedEncodingException {
        HttpRequest optionsRequest = HttpRequestFactory.get(HttpRequestMethod.OPTIONS, loggerFactoryDouble);
        optionsRequest.setPath("/method_options2");
        HttpResponse actualResponse = optionsRequest.buildResponse();
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        expectedResponse.setHeader("Allow", "GET,OPTIONS");
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}