package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImATeapotHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpRequest simpleGet = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
    private final RequestHandler requestHandler = new ImATeapotHandler();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addHandledPath("/coffee");
    }

    @Test
    public void handlesGetRequestToCoffee() {
        simpleGet.setPath("/coffee");
        assertTrue(requestHandler.handles(simpleGet));
    }

    @Test
    public void doesntHandleReqeustsToOtherPaths() {
        simpleGet.setPath("/another_path");
        assertFalse(requestHandler.handles(simpleGet));
    }

    @Test
    public void onlyAcceptsGetRequests() {
        HttpRequest postRequest = HttpRequestFactory.get(HttpRequestMethod.POST, loggerFactoryDouble);
        postRequest.setPath("/coffee");
        assertFalse(requestHandler.handles(postRequest));
    }

    @Test
    public void returnsImATeapotResponse() {
        simpleGet.setPath("/coffee");
        HttpResponse actualResponse = requestHandler.getResponse(simpleGet);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }
}
