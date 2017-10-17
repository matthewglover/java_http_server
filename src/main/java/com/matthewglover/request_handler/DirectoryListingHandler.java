package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.HtmlBuilder;

import java.io.File;

public class DirectoryListingHandler extends RequestHandler {

    private final FileAccessor fileAccessor;
    private final String rootDirectoryPath;

    public DirectoryListingHandler(String rootDirectoryPath, FileAccessor fileAccessor) {
        super();
        this.rootDirectoryPath = rootDirectoryPath;
        this.fileAccessor = fileAccessor;
    }

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledMethodType(HttpRequestMethod.HEAD);
    }

    @Override
    public boolean handles(HttpRequest request) {
        return isHandledRequestType(request) && isValidDirectory(request);
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        HttpResponse response = HttpResponseFactory.get(HttpResponseTemplate.OK);
        response.setContent(buildDirectoryListing(request));
        return response;
    }

    private boolean isValidDirectory(HttpRequest request) {
        return getDirectory(request).isDirectory();
    }

    private File getDirectory(HttpRequest request) {
        return fileAccessor.getFileFromPath(getFilePath(request));
    }

    private String getFilePath(HttpRequest request) {
        return rootDirectoryPath + request.getPath().replaceAll("^/+", "");
    }

    private String buildDirectoryListing(HttpRequest request) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();

        for (File file : getDirectory(request).listFiles()) {
            addDirectoryLink(htmlBuilder, file);
        }

        return htmlBuilder.build();
    }

    private void addDirectoryLink(HtmlBuilder htmlBuilder, File file) {
        htmlBuilder.addLink("/" + file.getName(), file.getName());
        htmlBuilder.addBr();
    }
}
