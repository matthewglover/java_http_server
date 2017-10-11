package com.matthewglover.http_response;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class HttpResponseHandler {

    private final Logger logger;

    public HttpResponseHandler(LoggerFactory loggerFactory) {
        logger = loggerFactory.getLogger(HttpResponseHandler.class.getName());
    }

    public HttpResponse handleRequest(HttpRequestParser httpRequestParser) throws IOException {
        httpRequestParser.parse();
        return processRequest(httpRequestParser.getRequest());
    }

    public HttpResponse processRequest(HttpRequest httpRequest) throws IOException {
        logRequest(httpRequest);
        return httpRequest.buildResponse();
    }

    public void logRequest(HttpRequest httpRequest) {
        logger.info(httpRequest.toString());
    }
}