package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class OptionsRequest extends HttpRequest {

    public OptionsRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public HttpResponse buildResponse(File rootDirectory) throws UnsupportedEncodingException {
        switch (getPath()) {
            case "/method_options": return HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_ALL);
            case "/method_options2": return HttpResponseFactory.get(HttpResponseTemplate.OPTIONS_ALLOW_SELECTED);
            default: return HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        }
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.OPTIONS);
    }
}
