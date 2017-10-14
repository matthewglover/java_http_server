package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class OptionsAllowAllHandlerTest {

    @Test
    public void allowAllMethodsForMethodOptions() {
        HttpRequest optionsRequest = HttpTestRequestFactory.get(HttpRequestMethod.OPTIONS);
        optionsRequest.setPath("/method_options");
        HttpResponse actualResponse = new OptionsAllowAllHandler().getResponse(optionsRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_ALL);
        expectedResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}