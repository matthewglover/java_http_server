package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class HttpHeadRequestTest {
    @Test
    public void respondsWithOKForRootPath() throws UnsupportedEncodingException {
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);
        HttpRequest httpHeadRequest = HttpRequestFactory.get(HttpRequestMethod.HEAD, loggerFactoryDouble);
        httpHeadRequest.setPath("/");
        HttpResponse actualResponse = httpHeadRequest.buildResponse();
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseType.OK);
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
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseType.NOT_FOUND);
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}