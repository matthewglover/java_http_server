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
        HttpRequest getRequest = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        getRequest.setPath("/logs");
        assertValidResponse(getRequest);
    }

    @Test
    public void handlesGetRequestToTea() {
        HttpRequest getRequest = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        getRequest.setPath("/tea");
        assertValidResponse(getRequest);
    }

    @Test
    public void handlesPutRequestToLog() {
        HttpRequest putRequest = HttpTestRequestFactory.get(HttpRequestMethod.PUT);
        putRequest.setPath("/log");
        assertValidResponse(putRequest);
    }

    @Test
    public void handlesHeadRequestToThese() {
        HttpRequest headRequest = HttpTestRequestFactory.get(HttpRequestMethod.HEAD);
        headRequest.setPath("/these");
        assertValidResponse(headRequest);
    }

    @Test
    public void doesntHandlePostRequestToRequests() {
        HttpRequest postRequest = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        postRequest.setPath("/requests");
        assertFalse(requestHandler.handles(postRequest));
    }

    private void assertValidResponse(HttpRequest request) {
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(request.requestLineToString(), response.getContent());
    }
}