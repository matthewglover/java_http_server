package com.matthewglover.http_response;

import com.matthewglover.http_request.FileResponse;

import java.io.UnsupportedEncodingException;

public class HttpResponseFactory {
    public static HttpResponse get(HttpResponseTemplate responseTemplate) throws UnsupportedEncodingException {
        switch (responseTemplate) {
            case FILE: return new FileResponse();
            case UNAUTHORIZED_ACCESS: return new UnauthorizedAccessResponse();
            case IM_A_TEAPOT: return new ImATeapotResponse();
            case OPTIONS_ALLOW_SELECTED: return new OptionsResponse("GET,OPTIONS");
            case OPTIONS_ALLOW_ALL: return new OptionsResponse("GET,HEAD,POST,OPTIONS,PUT");
            case BAD_REQUEST: return new BadRequestResponse();
            case NOT_FOUND: return new NotFoundResponse();
            case OK: return new OkResponse();
            default: return null;
        }
    }
}
