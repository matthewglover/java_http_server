package com.matthewglover.http_response;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class HttpResponseHandler {

    private final Logger logger;

    public HttpResponseHandler(LoggerFactory loggerFactory) {
        logger = loggerFactory.getLogger(HttpResponseHandler.class.getName());
    }

    public HttpResponse handleRequest(HttpRequest httpRequest) throws IOException {
        return processRequest(httpRequest);
    }

    public HttpResponse processRequest(HttpRequest httpRequest) throws IOException {
        logRequest(httpRequest);
        return httpRequest.buildResponse();
    }

    public void logRequest(HttpRequest httpRequest) {
        logger.info(httpRequest.toString());
    }
}