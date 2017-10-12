package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class GetRequest extends HttpRequest {

    public GetRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        return httpResponse;
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.GET);
    }
}
