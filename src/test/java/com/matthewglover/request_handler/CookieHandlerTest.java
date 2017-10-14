package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CookieHandlerTest {

    private final HttpRequest simpleGet = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final RequestHandler requestHandler = new CookieHandler();

    @Test
    @Ignore
    public void handlesGetRequestToCookieTypeChocolate() {
        simpleGet.setPath("/cookie");
        assertTrue(requestHandler.handles(simpleGet));
        HttpResponse response = requestHandler.getResponse(simpleGet);
        assertEquals(HttpResponseType.OK, response.getResponseType());
        assertEquals("Eat", response.getContent());
    }

    @Test
    public void handlesGetRequestToEatCookie() {
        simpleGet.setPath("/eat_cookie");
        assertTrue(requestHandler.handles(simpleGet));
        HttpResponse response = requestHandler.getResponse(simpleGet);
        assertEquals(HttpResponseType.OK, response.getResponseType());
        assertEquals("mmmm chocolate", response.getContent());
    }
}