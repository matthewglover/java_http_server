package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class BadRequest extends HttpRequest {
    public BadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.INVALID_METHOD);
    }

    @Override
    public HttpResponse buildResponse(File rootDirectory) throws UnsupportedEncodingException {
        return HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST);
    }
}
