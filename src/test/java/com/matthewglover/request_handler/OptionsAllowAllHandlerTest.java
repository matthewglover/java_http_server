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

import static junit.framework.TestCase.assertTrue;


public class OptionsAllowAllHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void allowAllMethodsForMethodOptions() {
        HttpRequest optionsRequest = HttpRequestFactory.get(HttpRequestMethod.OPTIONS, loggerFactoryDouble);
        optionsRequest.setPath("/method_options");
        HttpResponse actualResponse = new OptionsAllowAllHandler().getResponse(optionsRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_ALL);
        expectedResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}