package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedirectHandlerTest {

    private final HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final RequestHandler requestHandler = new RedirectHandler();

    @Test
    public void handlesGetRequestToCoffee() {
        request.setPath("/redirect");
        assertTrue(requestHandler.handles(request));
    }

    @Test
    public void doesntHandleReqeustsToOtherPaths() {
        request.setPath("/another_path");
        assertFalse(requestHandler.handles(request));
    }

    @Test
    public void onlyAcceptsGetRequests() {
        HttpRequest postRequest = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        postRequest.setPath("/redirect");
        assertFalse(requestHandler.handles(postRequest));
    }

    @Test
    public void returnsRedirectResponse() {
        request.setPath("/redirect");
        HttpResponse actualResponse = requestHandler.getResponse(request);
        assertEquals(HttpResponseType.REDIRECT, actualResponse.getResponseType());
        assertEquals("/", actualResponse.getHeader("Location"));
    }
}