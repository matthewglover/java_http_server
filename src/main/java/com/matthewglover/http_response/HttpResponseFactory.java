package com.matthewglover.http_response;

public class HttpResponseFactory {
    public static HttpResponse get(HttpResponseTemplate responseTemplate) {
        switch (responseTemplate) {
            case NO_CONTENT: return new NoContentResponse();
            case PARTIAL_CONTENT: return new PartialResponse();
            case REDIRECT: return new RedirectResponse();
            case METHOD_NOT_ALLOWED: return new MethodNotAllowedResponse();
            case OK_FILE: return new OkFileGeneratedResponse();
            case UNAUTHORIZED_ACCESS: return new UnauthorizedAccessResponse();
            case IM_A_TEAPOT: return new ImATeapotResponse();
            case OPTIONS_ALLOW_SELECTED: return new OptionsGeneratedResponse("GET,OPTIONS");
            case OPTIONS_ALLOW_ALL: return new OptionsGeneratedResponse("GET,HEAD,POST,OPTIONS,PUT");
            case BAD_REQUEST: return new BadRequestResponse();
            case NOT_FOUND: return new NotFoundResponse();
            case OK: return new OkGeneratedResponse();
            default: return null;
        }
    }
}
