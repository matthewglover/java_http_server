package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Test;

import static org.junit.Assert.*;

public class CookieHandlerTest {

    private final RequestHandler requestHandler = new CookieHandler();

    @Test
    public void handlesGetRequestToCookieTypeChocolate() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/cookie")
                .build();
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(HttpResponseType.OK, response.getResponseType());
        assertEquals("Eat", response.getContent());
    }

    @Test
    public void handlesGetRequestToEatCookie() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/eat_cookie")
                .build();
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(HttpResponseType.OK, response.getResponseType());
        assertEquals("mmmm chocolate", response.getContent());
    }
}