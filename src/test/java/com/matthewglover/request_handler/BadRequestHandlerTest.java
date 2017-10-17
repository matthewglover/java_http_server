package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Test;

import static org.junit.Assert.*;

public class BadRequestHandlerTest {

    @Test
    public void handlesBadRequest() {
        RequestHandler requestHandler = new BadRequestHandler();
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.INVALID_METHOD);
        assertTrue(requestHandler.handles(request));
        assertTrue(new ResponseComparer(
                HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST),
                requestHandler.getResponse(request)).areSame());
    }
}