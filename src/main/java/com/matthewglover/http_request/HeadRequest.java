package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class HeadRequest extends HttpRequest {

    public HeadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        if (getPath().equals("/")) {
            return HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        } else {
            return HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        }
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.HEAD);
    }
}