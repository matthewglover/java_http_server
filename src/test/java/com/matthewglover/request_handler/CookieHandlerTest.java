package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CookieHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpRequest simpleGet = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
    private final RequestHandler requestHandler = new CookieHandler();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void handlesGetRequestToCookieTypeChocolate() {
        simpleGet.setPath("/cookie?type=chocolate");
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