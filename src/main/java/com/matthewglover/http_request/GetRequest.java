package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class GetRequest extends HttpRequest {

    public GetRequest(LoggerFactory loggerFactory) {
        super(loggerFactory);
    }

    @Override
    public void setup() {
        setMethod(HttpRequestMethod.GET);
    }

    @Override
    public HttpResponse buildResponse() throws UnsupportedEncodingException {
        switch (getPath()) {
            case "/foobar": return HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
            case "/coffee": return HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
            default: return HttpResponseFactory.get(HttpResponseTemplate.OK);
        }
    }
}
