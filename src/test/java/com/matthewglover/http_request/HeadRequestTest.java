package com.matthewglover.http_request;

import com.matthewglover.http_response.*;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class HeadRequestTest {
    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final String filePath = "path/to/public/dir";

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void respondsWithOKForRootPath() throws UnsupportedEncodingException {
        HttpRequest httpHeadRequest = HttpRequestFactory.get(HttpRequestMethod.HEAD, loggerFactoryDouble);
        httpHeadRequest.setPath("/");
        HttpResponse actualResponse = httpHeadRequest.buildResponse(filePath);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }

    @Test
    public void respondsWithNotFoundForNonRootPath() throws UnsupportedEncodingException {
        HttpRequest httpHeadRequest = HttpRequestFactory.get(HttpRequestMethod.HEAD, loggerFactoryDouble);
        httpHeadRequest.setPath("/foobar");
        HttpResponse actualResponse = httpHeadRequest.buildResponse(filePath);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}