package com.matthewglover.http_response;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResponseHandlerTest {

    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final HttpRequest httpRequest = new HttpRequest();
    private HttpRequestParser httpRequestParser;

    @Before
    public void setUp() throws Exception {
        httpRequest.setMethod(HttpRequestMethod.GET);
        httpRequest.setPath("/");
        httpRequest.setVersion("HTTP/1.1");
        httpRequest.setHeader("Host", "server:port");
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
        assertEquals(expectedResponse.toString(), actualResponse.toString());
    }
}