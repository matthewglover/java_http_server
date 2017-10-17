package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.OkFileResponse;
import com.matthewglover.util.FileAccessor;

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
        if (request.getMethod() == HttpRequestMethod.GET) return handleGetRequest(request);
        else return HttpResponseFactory.get(HttpResponseTemplate.NO_CONTENT);
    }

    private HttpResponse handleGetRequest(HttpRequest request) {
        OkFileResponse fileResponse =
                (OkFileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);

        fileResponse.setFile(getFilePath(request), fileAccessor);
        return fileResponse;
    }

    private String getFilePath(HttpRequest request) {
        return rootDirectoryPath + request.getPath().replaceAll("^/+", "");
    }
}
