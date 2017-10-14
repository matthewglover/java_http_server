package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormDataHandlerTest {

    private final RequestHandler requestHandler = new FormDataHandler();

    @Before
    public void setUp() throws Exception {
        requestHandler.addHandledPath("/form");
    }

    @Test
    public void handlesPostRequestToForm() {
        HttpRequest simplePost = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        simplePost.setPath("/form");
        assertTrue(requestHandler.handles(simplePost));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(simplePost).getResponseType());
    }

    @Test
    public void handlesPutRequestToForm() {
        HttpRequest simplePut = HttpTestRequestFactory.get(HttpRequestMethod.PUT);
        simplePut.setPath("/form");
        assertTrue(requestHandler.handles(simplePut));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(simplePut).getResponseType());
    }
}