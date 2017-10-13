package com.matthewglover;

import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.request_handler.*;

import java.io.File;

public class DefaultRouter implements RouterBuilder {

    private final RequestRouter router = new RequestRouter();
    private File rootDirectory;

    @Override
    public RequestRouter build(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        buildHandlers();
        return router;
    }

    private void buildHandlers() {
        router.addHandler(getBadRequestHandler());
        router.addHandler(getBasicAuthHandler());
        router.addHandler(getOptionsAllowAllHandler());
        router.addHandler(getOptionsAllowSelectedHandler());
        router.addHandler(getCookieHandler());
        router.addHandler(getImATeapotHandler());
        router.addHandler(getSimpleOkHandler());
        router.addHandler(getFormDataHandler());
        router.addHandler(getDirectoryListingHandler());
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
        return new DirectoryListingHandler(rootDirectory);
    }

    public RequestHandler getBadRequestHandler() {
        return new BadRequestHandler();
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
