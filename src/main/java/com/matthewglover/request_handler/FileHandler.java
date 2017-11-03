package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.FileResponse;
import com.matthewglover.util.FileAccessor;

import java.io.File;

public class FileHandler extends RequestHandler {

    private final String rootDirectoryPath;
    private final FileAccessor fileAccessor;

    public FileHandler(String rootDirectoryPath, FileAccessor fileAccessor) {
        super();
        this.rootDirectoryPath = rootDirectoryPath;
        this.fileAccessor = fileAccessor;
        addHandledMethodType(HttpRequestMethod.GET);
        addHandledMethodType(HttpRequestMethod.POST);
        addHandledMethodType(HttpRequestMethod.PUT);
        addHandledMethodType(HttpRequestMethod.INVALID_METHOD);
    }

    @Override
    public boolean isHandledPath(HttpRequest httpRequest) {
        File file = fileAccessor.getFileFromPath(getFilePath(httpRequest));
        return file.isFile();
    }

    @Override
    public HttpResponse getResponse(HttpRequest httpRequest) {
        return isDisallowedMethod(httpRequest) ?  handleDisallowedMethodResponse() : handleFileResponse(httpRequest);
    }

    private boolean isDisallowedMethod(HttpRequest httpRequest) {
        return httpRequest.getMethod() != HttpRequestMethod.GET;
    }

    private HttpResponse handleDisallowedMethodResponse() {
        return HttpResponseFactory.get(HttpResponseTemplate.METHOD_NOT_ALLOWED);
    }

    private HttpResponse handleFileResponse(HttpRequest httpRequest) {
        FileResponse fileResponse = getFileResponse();

        fileResponse.setFile(getFilePath(httpRequest), fileAccessor);
        return fileResponse;
    }

    private FileResponse getFileResponse() {
        return (FileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);
    }

    private String getFilePath(HttpRequest httpRequest) {
        return rootDirectoryPath + httpRequest.getPath().replaceAll("^/+", "");
    }
}
