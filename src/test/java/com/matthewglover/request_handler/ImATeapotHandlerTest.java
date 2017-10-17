package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImATeapotHandlerTest {

    private final HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final RequestHandler requestHandler = new ImATeapotHandler();

    @Before
    public void setUp() throws Exception {
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addHandledPath("/coffee");
    }

    @Test
    public void handlesGetRequestToCoffee() {
        request.setPath("/coffee");
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
        postRequest.setPath("/coffee");
        assertFalse(requestHandler.handles(postRequest));
    }

    @Test
    public void returnsImATeapotResponse() {
        request.setPath("/coffee");
        HttpResponse actualResponse = requestHandler.getResponse(request);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }
}
