package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.OkFileGeneratedResponse;
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
        if (request.getMethod() == HttpRequestMethod.PATCH) return handlePatchRequest(request);
        else return handleGetRequest(request);
    }

    private HttpResponse handlePatchRequest(HttpRequest request) {
        FileWriter fileWriter = new FileWriter(getFilePath(request), fileAccessor);
        if (request.hasContent()) fileWriter.write(request.getContent());
        return HttpResponseFactory.get(HttpResponseTemplate.NO_CONTENT);
    }

    private HttpResponse handleGetRequest(HttpRequest request) {
        OkFileGeneratedResponse fileResponse =
                (OkFileGeneratedResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);

        fileResponse.setFile(getFilePath(request), fileAccessor);
        return fileResponse;
    }

    private String getFilePath(HttpRequest request) {
        return rootDirectoryPath + request.getPath().replaceAll("^/+", "");
    }
}
