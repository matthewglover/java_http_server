package com.matthewglover.http_response;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class ResponseHandler {

    private final Logger logger;

    public ResponseHandler(LoggerFactory loggerFactory) {
        logger = loggerFactory.getLogger(ResponseHandler.class.getName());
    }

    public HttpResponse handleRequest(HttpRequestParser httpRequestParser) throws IOException {
        httpRequestParser.parse();
        if (httpRequestParser.hasErrors()) {
            return respondWithBadRequest();
        } else {
            return processRequest(httpRequestParser.getRequest());
        }
    }

    public HttpResponse respondWithBadRequest() throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.BAD_REQUEST);
        httpResponse.setHeader("Content-Length", "0");
        return httpResponse;
    }

    public HttpResponse processRequest(HttpRequest httpRequest) throws IOException {
        logRequest(httpRequest);
        return processResponse(httpRequest);
    }

    public void logRequest(HttpRequest httpRequest) {
        logger.info(httpRequest.toString());
    }

    public HttpResponse processResponse(HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.OK);
        httpResponse.setContent("<html><head></head><body></body></html>");
        httpResponse.setContentLengthHeader();
        return httpResponse;
    }
}