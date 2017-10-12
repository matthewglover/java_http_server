package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class GetRequestTest {
    LoggerDouble loggerDouble = new LoggerDouble(null, null);
    LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void requestToRootReturns200() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/");
        HttpResponse actualResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        assertTrue(new ResponseComparer(actualResponse, getRequest.buildResponse()).areSame());
    }

    @Test
    public void requestToFoobarReturns404() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/foobar");
        HttpResponse actualResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        assertTrue(new ResponseComparer(actualResponse, getRequest.buildResponse()).areSame());
    }

    @Test
    public void requestToCoffeeReturns418() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/coffee");
        HttpResponse actualResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(actualResponse, getRequest.buildResponse()).areSame());
    }
}
