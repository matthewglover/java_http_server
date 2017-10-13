package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormDataHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final RequestHandler requestHandler = new FormDataHandler();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
        requestHandler.addHandledPath("/form");
    }

    @Test
    public void handlesPostRequestToForm() {
        HttpRequest simplePost = HttpRequestFactory.get(HttpRequestMethod.POST, loggerFactoryDouble);
        simplePost.setPath("/form");
        assertTrue(requestHandler.handles(simplePost));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(simplePost).getResponseType());
    }

    @Test
    public void handlesPutRequestToForm() {
        HttpRequest simplePut = HttpRequestFactory.get(HttpRequestMethod.PUT, loggerFactoryDouble);
        simplePut.setPath("/form");
        assertTrue(requestHandler.handles(simplePut));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(simplePut).getResponseType());
    }
}