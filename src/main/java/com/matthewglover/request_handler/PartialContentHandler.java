package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.OkPartialFileResponse;
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
        return getFileResponse();
    }

    private HttpResponse getFileResponse() {
        OkPartialFileResponse fileResponse =
                (OkPartialFileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_PARTIAL_FILE);

        fileResponse.setFile("/partial_content.txt", fileAccessor);
        fileResponse.setRange(0, 4);
        return fileResponse;
    }
}
