package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class HttpHeadRequest extends HttpRequest {

    private final Logger logger;

    public HttpHeadRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
        logger = loggerFactory.getLogger(HttpHeadRequest.class.getName());
        setMethod(HttpRequestMethod.HEAD);
    }
    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        if (getPath().equals("/")) {
            return HttpResponseFactory.get(HttpResponseType.OK);
        } else {
            return HttpResponseFactory.get(HttpResponseType.NOT_FOUND);
        }
    }
}
