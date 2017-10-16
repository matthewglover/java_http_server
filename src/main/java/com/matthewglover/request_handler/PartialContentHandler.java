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
        PartialResponse response = (PartialResponse) HttpResponseFactory.get(HttpResponseTemplate.PARTIAL_CONTENT);
        response.setFile(getFilePath(request), fileAccessor);
//        response.setRange(getRangeStart(request), getRangeEnd(request));
        response.setRange(0, 3);
        response.build();
        return response;
    }

    private int getRangeStart(HttpRequest request) {
        String[] rangePair = getRangePair(request);
        return rangePair[0].equals("")  ? 0 : Integer.parseInt(rangePair[0]);
    }

    private int getRangeEnd(HttpRequest request) {
        String[] rangePair = getRangePair(request);
        return rangePair.length == 1 ? getFileLength(request) : Integer.parseInt(rangePair[1]);
    }

    private String[] getRangePair(HttpRequest request) {
        String range = request.getHeader("Range");
        String rangeData = range.split("=")[1];
        return rangeData.split("-");
    }

    private String getFilePath(HttpRequest request) {
        return rootDirectoryPath + request.getPath().replaceAll("^/+", "");
    }

    public int getFileLength(HttpRequest request) {
        return (int) fileAccessor.getFileFromPath(getFilePath(request)).length();
    }
}
