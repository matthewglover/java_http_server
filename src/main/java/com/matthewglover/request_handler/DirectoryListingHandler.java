package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.HtmlBuilder;

import java.io.File;

public class DirectoryListingHandler extends RequestHandler {

    private final File rootDirectory;

    public DirectoryListingHandler(File rootDirectory) {
        super();
        this.rootDirectory = rootDirectory;
    }

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledMethodType(HttpRequestMethod.HEAD);
    }

    @Override
    public boolean handles(HttpRequest request) {
        return isHandledRequestType(request) && isValidDirectory(request.getPath());
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return handleDirectoryListing();
    }

    private boolean isValidDirectory(String path) {
        File file = new File(rootDirectory.getPath() + path);
        return file.isDirectory();
    }

    private HttpResponse handleDirectoryListing() {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(buildDirectoryListing());
        response.setContentLengthHeader();
        return response;
    }

    private String buildDirectoryListing() {
        HtmlBuilder htmlBuilder = new HtmlBuilder();

        for (File file : rootDirectory.listFiles()) {
            htmlBuilder.addLink("/" + file.getName(), file.getName());
            htmlBuilder.addBr();
        }

        return htmlBuilder.build();
    }
}
