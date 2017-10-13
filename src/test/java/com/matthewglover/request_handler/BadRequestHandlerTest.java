package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BadRequestHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpRequest simpleGet = HttpRequestFactory.get(HttpRequestMethod.INVALID_METHOD, loggerFactoryDouble);
    private final RequestHandler requestHandler = new BadRequestHandler();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void handlesGetRequestToCoffee() {
        simpleGet.setPath("/any_path");
        assertTrue(requestHandler.handles(simpleGet));
        assertTrue(new ResponseComparer(
                HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST),
                requestHandler.getResponse(simpleGet)).areSame());
    }
}