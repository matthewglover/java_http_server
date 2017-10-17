package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleOkHandlerTest {

    private final RequestHandler requestHandler = new SimpleOkHandler();

    @Before
    public void setUp() throws Exception {
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
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        request.setPath("/logs");
        assertValidResponse(request);
    }

    @Test
    public void handlesGetRequestToTea() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        request.setPath("/tea");
        assertValidResponse(request);
    }

    @Test
    public void handlesPutRequestToLog() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.PUT);
        request.setPath("/log");
        assertValidResponse(request);
    }

    @Test
    public void handlesHeadRequestToThese() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.HEAD);
        request.setPath("/these");
        assertValidResponse(request);
    }

    @Test
    public void doesntHandlePostRequestToRequests() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        request.setPath("/requests");
        assertFalse(requestHandler.handles(request));
    }

    private void assertValidResponse(HttpRequest request) {
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(request.requestLineToString(), response.getContent());
    }
}