package com.matthewglover.http_response;

import com.matthewglover.http_request.*;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResponseHandlerTest {

    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final HttpRequest httpRequest = HttpRequestFactory.get(HttpRequestType.SIMPLE_GET);
    private HttpRequestParser httpRequestParser;

    @Before
    public void setUp() throws Exception {
        httpRequestParser = new HttpRequestParser(httpRequest.toRaw());
        loggerFactory.setLogger(logger);
    }

    @Test
    public void givenSimpleGetRequestReturns200() throws IOException {
        ResponseHandler responseHandler = new ResponseHandler(loggerFactory);
        HttpResponse expectedResponse = new HttpResponse();
        expectedResponse.setResponseType(HttpResponseType.OK);
        expectedResponse.setContent("<html><head></head><body></body></html>");
        expectedResponse.setContentLengthHeader();
        HttpResponse actualResponse = responseHandler.handleRequest(httpRequestParser);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }
}