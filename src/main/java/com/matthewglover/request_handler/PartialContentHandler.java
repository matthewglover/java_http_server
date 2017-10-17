package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.PartialResponse;
import com.matthewglover.util.FileAccessor;

public class PartialContentHandler extends RequestHandler {

    private final String rootDirectoryPath;
    private final FileAccessor fileAccessor;

    public PartialContentHandler(String rootDirectoryPath, FileAccessor fileAccessor) {
        super();
        this.rootDirectoryPath = rootDirectoryPath;
        this.fileAccessor = fileAccessor;
    }

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath("/partial_content.txt");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        PartialResponse response = getPartialResponse();
        addRange(response, request);
        addFile(response, request);
        response.build();
        return response;
    }

    private PartialResponse getPartialResponse() {
        return (PartialResponse) HttpResponseFactory.get(HttpResponseTemplate.PARTIAL_CONTENT);
    }

    private void addFile(PartialResponse response, HttpRequest request) {
        response.setFile(getFilePath(request), fileAccessor);
    }

    private String getFilePath(HttpRequest request) {
        return rootDirectoryPath + request.getPath().replaceAll("^/+", "");
    }

    private void addRange(PartialResponse response, HttpRequest request) {
        RangeBuilder range = getRange(request);
        response.setRange(range.getStart(), range.getEnd());
    }

    private RangeBuilder getRange(HttpRequest request) {
        return new RangeBuilder(request.getHeader("Range"), getFileLength(request));
    }


    public int getFileLength(HttpRequest request) {
        return (int) fileAccessor.getFileFromPath(getFilePath(request)).length() - 1;
    }
}
