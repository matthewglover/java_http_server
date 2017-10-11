package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class BadRequest extends HttpRequest {
    private String method;

    public BadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setup() {
    }

    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        return HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST);
    }

    @Override
    public String getMethodString() {
        return method;
    }
}
