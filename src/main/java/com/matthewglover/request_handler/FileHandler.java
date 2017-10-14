package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.OkFileResponse;
import com.matthewglover.util.FileAccessor;

import java.io.File;

public class FileHandler extends RequestHandler {

    private final String rootDirectoryPath;
    private final FileAccessor fileAccessor;

    public FileHandler(String rootDirectoryPath, FileAccessor fileAccessor) {
        super();
        this.rootDirectoryPath = rootDirectoryPath;
        this.fileAccessor = fileAccessor;
    }

    @Override
    public void setup() {
        addHandledMethodType(HttpRequestMethod.GET);
    }

    @Override
    public boolean isHandledPath(HttpRequest httpRequest) {
        File file = fileAccessor.getFileFromPath(getFilePath(httpRequest));
        return file.isFile();
    }

    @Override
    public HttpResponse getResponse(HttpRequest httpRequest) {
        OkFileResponse fileResponse =
                (OkFileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);

        fileResponse.setFile(getFilePath(httpRequest), fileAccessor);
        return fileResponse;
    }

    private String getFilePath(HttpRequest httpRequest) {
        return rootDirectoryPath + httpRequest.getPath().replaceAll("^/+", "");
    }
}
