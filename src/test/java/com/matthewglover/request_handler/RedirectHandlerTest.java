package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedirectHandlerTest {

    private final RequestHandler requestHandler = new RedirectHandler();

    @Test
    public void handlesGetRequestToCoffee() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/redirect")
                .build();
        assertTrue(requestHandler.handles(request));
    }

    @Test
    public void doesntHandleReqeustsToOtherPaths() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/another_path")
                .build();
        assertFalse(requestHandler.handles(request));
    }

    @Test
    public void onlyAcceptsGetRequests() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/redirect")
                .build();
        assertFalse(requestHandler.handles(request));
    }

    @Test
    public void returnsRedirectResponse() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/redirect")
                .build();
        HttpResponse actualResponse = requestHandler.getResponse(request);
        assertEquals(HttpResponseType.REDIRECT, actualResponse.getResponseType());
        assertEquals("/", actualResponse.getHeader("Location"));
    }
}