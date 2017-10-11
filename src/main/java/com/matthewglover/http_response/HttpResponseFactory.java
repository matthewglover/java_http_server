package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class HttpResponseFactory {
    public static HttpResponse get(HttpResponseType httpResponseType) throws UnsupportedEncodingException {
        switch (httpResponseType) {
            case BAD_REQUEST: return buildBadRequestResponse();
            case INTERNAL_SERVER_ERROR: return null;
            case OK: return buildOkResponse();
            default: return null;
        }
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
