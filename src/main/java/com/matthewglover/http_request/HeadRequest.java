package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class HeadRequest extends HttpRequest {

    private final Logger logger;

    public HeadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
        logger = loggerFactory.getLogger(HeadRequest.class.getName());
        setMethod(HttpRequestMethod.HEAD);
    }

    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        if (getPath().equals("/")) {
            return HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        } else {
            return HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        }
    }
}
