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

public class PostRequestTest {
    LoggerDouble loggerDouble = new LoggerDouble(null, null);
    LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void requestToFormReturns200() throws UnsupportedEncodingException {
        HttpRequest postRequest = HttpRequestFactory.get(HttpRequestMethod.POST, loggerFactoryDouble);
        HttpResponse actualResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        assertTrue(new ResponseComparer(actualResponse, postRequest.buildResponse()).areSame());
    }
}