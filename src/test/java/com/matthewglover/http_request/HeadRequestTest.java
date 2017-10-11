package com.matthewglover.http_request;

import com.matthewglover.http_response.*;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class HeadRequestTest {
    @Test
    public void respondsWithOKForRootPath() throws UnsupportedEncodingException {
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);
        HttpRequest httpHeadRequest = HttpRequestFactory.get(HttpRequestMethod.HEAD, loggerFactoryDouble);
        httpHeadRequest.setPath("/");
        HttpResponse actualResponse = httpHeadRequest.buildResponse();
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }

    @Test
    public void respondsWithNotFoundForNonRootPath() throws UnsupportedEncodingException {
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);
        HttpRequest httpHeadRequest = HttpRequestFactory.get(HttpRequestMethod.HEAD, loggerFactoryDouble);
        httpHeadRequest.setPath("/foobar");
        HttpResponse actualResponse = httpHeadRequest.buildResponse();
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}