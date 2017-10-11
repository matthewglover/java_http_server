package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class HttpResponseFactory {
    public static HttpResponse get(HttpResponseTemplate responseTemplate) throws UnsupportedEncodingException {
        switch (responseTemplate) {
            case OPTIONS_ALLOW_SELECTED: return buildOptionsAllowSelectedResponse();
            case OPTIONS_ALLOW_ALL: return buildOptoinsAllowAllResponse();
            case BAD_REQUEST: return buildBadRequestResponse();
            case NOT_FOUND: return buildNotFoundResponse();
            case SIMPLE_GET:
            default: return buildOkResponse();
        }
    }

    private static HttpResponse buildOptionsAllowSelectedResponse() throws UnsupportedEncodingException {
        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        httpResponse.setHeader("Allow", "GET,OPTIONS");
        return httpResponse;
    }

    private static HttpResponse buildOptoinsAllowAllResponse() throws UnsupportedEncodingException {
        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.SIMPLE_GET);
        httpResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return httpResponse;
    }

    private static HttpResponse buildNotFoundResponse() throws UnsupportedEncodingException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.NOT_FOUND);
        return httpResponse;
    }

    private static HttpResponse buildOkResponse() throws UnsupportedEncodingException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.OK);
        return httpResponse;
    }

    private static HttpResponse buildBadRequestResponse() throws UnsupportedEncodingException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.BAD_REQUEST);
        return httpResponse;
    }
}
