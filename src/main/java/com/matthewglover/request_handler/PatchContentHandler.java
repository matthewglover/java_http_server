package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.FileResponse;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.FileWriter;

public class PatchContentHandler extends RequestHandler {


    private final String rootDirectoryPath;
    private final FileAccessor fileAccessor;

    public PatchContentHandler(String rootDirectoryPath, FileAccessor fileAccessor) {
        super();
        this.rootDirectoryPath = rootDirectoryPath;
        this.fileAccessor = fileAccessor;
    }

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.PATCH);
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledPath("/patch-content.txt");
    }

    @Override
    public HttpResponse getResponse(HttpRequest request) {
        return isPatchRequest(request) ? handlePatchRequest(request) : handleGetRequest(request);
    }

    private boolean isPatchRequest(HttpRequest request) {
        return request.getMethod() == HttpRequestMethod.PATCH;
    }

    private HttpResponse handlePatchRequest(HttpRequest request) {
        if (request.hasContent()) patchFile(request);
        return HttpResponseFactory.get(HttpResponseTemplate.NO_CONTENT);
    }

    private void patchFile(HttpRequest request) {
        FileWriter fileWriter = getFileWriter(request);
        fileWriter.write(request.getContent());
    }

    private FileWriter getFileWriter(HttpRequest request) {
        return new FileWriter(getFilePath(request), fileAccessor);
    }

    private HttpResponse handleGetRequest(HttpRequest request) {
        FileResponse fileResponse = getFileResponse();
        fileResponse.setFile(getFilePath(request), fileAccessor);
        return fileResponse;
    }

    private FileResponse getFileResponse() {
        return (FileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);
    }

    private String getFilePath(HttpRequest request) {
        return rootDirectoryPath + request.getPath().replaceAll("^/+", "");
    }
}
