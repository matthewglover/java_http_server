package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class PutRequest extends HttpRequest {
    public PutRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.PUT);
    }

    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }
}
