package com.matthewglover;

import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.request_handler.*;
import com.matthewglover.util.FileAccessor;

public class DefaultRouterBuilder implements RouterBuilder {

    private final RequestRouter router = new RequestRouter();
    private String rootDirectoryPath;
    private FileAccessor fileAccessor;

    @Override
    public RequestRouter build(String filePath, FileAccessor fileAccessor) {
        this.rootDirectoryPath = filePath;
        this.fileAccessor = fileAccessor;
        buildHandlers();
        return router;
    }

    private void buildHandlers() {
        router.addHandler(getBasicAuthHandler());
        router.addHandler(getOptionsAllowAllHandler());
        router.addHandler(getOptionsAllowSelectedHandler());
        router.addHandler(getCookieHandler());
        router.addHandler(getImATeapotHandler());
        router.addHandler(getSimpleOkHandler());
        router.addHandler(getFormDataHandler());
        router.addHandler(getFileHandler());
        router.addHandler(getDirectoryListingHandler());
    }

    private RequestHandler getFileHandler() {
        return new FileHandler(rootDirectoryPath, fileAccessor);
    }

    private RequestHandler getCookieHandler() {
        return new CookieHandler();
    }

    private RequestHandler getFormDataHandler() {
        FormDataHandler requestHandler = new FormDataHandler();
        requestHandler.addHandledPath("/form");
        return requestHandler;
    }

    private RequestHandler getDirectoryListingHandler() {
        return new DirectoryListingHandler(rootDirectoryPath, fileAccessor);
    }

    private RequestHandler getBasicAuthHandler() {
        NewBasicAuthHandler requestHandler = new NewBasicAuthHandler();
        requestHandler.addAuthCredentials("admin", "hunter2");
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addHandledPath("/logs");
        return requestHandler;
    }

    public RequestHandler getOptionsAllowAllHandler() {
        return new OptionsAllowAllHandler();
    }

    public RequestHandler getOptionsAllowSelectedHandler() {
        return new OptionsAllowSelectedHandler();
    }

    private RequestHandler getImATeapotHandler() {
        ImATeapotHandler requestHandler = new ImATeapotHandler();
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addHandledPath("/coffee");
        return requestHandler;
    }

    private RequestHandler getSimpleOkHandler() {
        RequestHandler requestHandler = new SimpleOkHandler();
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addHandledMethodType(HttpRequestMethod.PUT);
        requestHandler.addHandledMethodType(HttpRequestMethod.HEAD);
        requestHandler.addHandledPath("/logs");
        requestHandler.addHandledPath("/log");
        requestHandler.addHandledPath("/these");
        requestHandler.addHandledPath("/requests");
        requestHandler.addHandledPath("/tea");
        return requestHandler;
    }
}
