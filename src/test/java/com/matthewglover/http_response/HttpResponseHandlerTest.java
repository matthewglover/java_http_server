package com.matthewglover.http_response;

import com.matthewglover.http_request.*;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpResponseHandlerTest {

    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final HttpRequest httpRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactory);
    private HttpRequestParser httpRequestParser;

    @Before
    public void setUp() throws Exception {
        httpRequestParser = new HttpRequestParser(httpRequest.toRaw(), loggerFactory);
        loggerFactory.setLogger(logger);
    }

    @Test
    public void givenSimpleGetRequestReturns200() throws IOException {
        HttpResponseHandler httpResponseHandler = new HttpResponseHandler(loggerFactory);
        HttpResponse expectedResponse = new HttpResponse();
        expectedResponse.setResponseType(HttpResponseType.OK);
        expectedResponse.setContent("<html><head></head><body></body></html>");
        expectedResponse.setContentLengthHeader();
        HttpResponse actualResponse = httpResponseHandler.handleRequest(httpRequestParser);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }
}