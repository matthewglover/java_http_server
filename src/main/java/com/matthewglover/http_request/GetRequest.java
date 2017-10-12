package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.request_handler.BasicAuthHandler;
import com.matthewglover.util.HtmlBuilder;
import com.matthewglover.util.LoggerFactory;

import java.io.File;
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
    public HttpResponse buildResponse(File rootDirectory) throws UnsupportedEncodingException {
        switch (getPath()) {
            case "/foobar": return HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
            case "/coffee": return HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
            case "/logs": return handleAuthorizedPath();
            case "/": return handleDirectoryListing(rootDirectory);
            default: return HttpResponseFactory.get(HttpResponseTemplate.OK);
        }
    }

    private HttpResponse handleDirectoryListing(File rootDirectory) throws UnsupportedEncodingException {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(buildDirectoryListing(rootDirectory));
        response.setContentLengthHeader();
        return response;
    }

    private String buildDirectoryListing(File rootDirectory) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();

        for (File file : rootDirectory.listFiles()) {
            htmlBuilder.addLink("/" + file.getName(), file.getName());
            htmlBuilder.addBr();
        }

        return htmlBuilder.build();
    }

    private HttpResponse handleAuthorizedPath() throws UnsupportedEncodingException {
        BasicAuthHandler authHandler = new BasicAuthHandler();
        authHandler.addUsernameAndPassword("admin", "hunter2");
        return authHandler.handleRequest(this);
    }
}
