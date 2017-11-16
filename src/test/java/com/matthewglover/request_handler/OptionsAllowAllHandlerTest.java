package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class OptionsAllowAllHandlerTest {

    @Test
    public void allowAllMethodsForMethodOptions() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.OPTIONS)
                .setPath("/method_options")
                .build();
        HttpResponse actualResponse = new OptionsAllowAllHandler().getResponse(request);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_ALL);
        expectedResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}