package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImATeapotHandlerTest {

    private final RequestHandler requestHandler = new ImATeapotHandler();

    @Before
    public void setUp() throws Exception {
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
    }

    @Test
    public void handlesGetRequestToCoffee() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/coffee")
                .build();
        assertTrue(requestHandler.handles(request));
    }

    @Test
    public void handlesGetRequestToTea() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/tea")
                .build();
        assertTrue(requestHandler.handles(request));
    }

    @Test
    public void doesntHandleRequestsToOtherPaths() {
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
                .setPath("/coffee")
                .build();
        assertFalse(requestHandler.handles(request));
    }

    @Test
    public void requestToCoffeeReturnsImATeapot() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/coffee")
                .build();
        HttpResponse actualResponse = requestHandler.getResponse(request);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void requestToTeaReturnsImATeapot() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/tea")
                .build();
        HttpResponse actualResponse = requestHandler.getResponse(request);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }
}
