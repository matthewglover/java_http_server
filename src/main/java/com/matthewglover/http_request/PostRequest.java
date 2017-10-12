package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class PostRequest extends HttpRequest {

    public PostRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.POST);
    }

    @Override
    public HttpResponse buildResponse(File rootDirectory) throws UnsupportedEncodingException {
        return HttpResponseFactory.get(HttpResponseTemplate.OK);
    }
}
