package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionsAllowSelectedHandlerTest {

    @Test
    public void selectivelyAllowMethodsForMethodOptions2() {
        HttpRequest optionsRequest = HttpTestRequestFactory.get(HttpRequestMethod.OPTIONS);
        optionsRequest.setPath("/method_options2");
        HttpResponse actualResponse = new OptionsAllowSelectedHandler().getResponse(optionsRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_SELECTED);
        expectedResponse.setHeader("Allow", "GET,OPTIONS");
        assertTrue(new ResponseComparer(actualResponse, expectedResponse).areSame());
    }
}