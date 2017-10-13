package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleOkHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final RequestHandler requestHandler = new SimpleOkHandler();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addHandledMethodType(HttpRequestMethod.PUT);
        requestHandler.addHandledMethodType(HttpRequestMethod.HEAD);
        requestHandler.addHandledPath("/logs");
        requestHandler.addHandledPath("/log");
        requestHandler.addHandledPath("/these");
        requestHandler.addHandledPath("/requests");
        requestHandler.addHandledPath("/tea");
    }

    @Test
    public void handlesGetRequestToLogs() {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/logs");
        assertValidResponse(getRequest);
    }

    @Test
    public void handlesGetRequestToTea() {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/tea");
        assertValidResponse(getRequest);
    }

    @Test
    public void handlesPutRequestToLog() {
        HttpRequest putRequest = HttpRequestFactory.get(HttpRequestMethod.PUT, loggerFactoryDouble);
        putRequest.setPath("/log");
        assertValidResponse(putRequest);
    }

    @Test
    public void handlesHeadRequestToThese() {
        HttpRequest headRequest = HttpRequestFactory.get(HttpRequestMethod.HEAD, loggerFactoryDouble);
        headRequest.setPath("/these");
        assertValidResponse(headRequest);
    }

    @Test
    public void doesntHandlePostRequestToRequests() {
        HttpRequest postRequest = HttpRequestFactory.get(HttpRequestMethod.POST, loggerFactoryDouble);
        postRequest.setPath("/requests");
        assertFalse(requestHandler.handles(postRequest));
    }

    private void assertValidResponse(HttpRequest request) {
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(request.requestLineToString(), response.getContent());
    }
}