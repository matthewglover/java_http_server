package com.matthewglover;

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
        router.addHandler(getFormDataHandler());
        router.addHandler(getParametersHandler());
        router.addHandler(getRedirectHandler());
        router.addHandler(getPartialContentHandler());
        router.addHandler(getPatchContentHandler());
        router.addHandler(getFileHandler());
        router.addHandler(getDirectoryListingHandler());
    }

    private RequestHandler getPatchContentHandler() {
        return new PatchContentHandler(rootDirectoryPath, fileAccessor);
    }

    private RequestHandler getPartialContentHandler() {
        return new PartialContentHandler(rootDirectoryPath, fileAccessor);
    }

    private RequestHandler getRedirectHandler() {
        return new RedirectHandler();
    }

    private RequestHandler getFileHandler() {
        return new FileHandler(rootDirectoryPath, fileAccessor);
    }

    private RequestHandler getCookieHandler() {
        return new CookieHandler();
    }

    private RequestHandler getFormDataHandler() {
        return new FormDataHandler();
    }

    private RequestHandler getDirectoryListingHandler() {
        return new DirectoryListingHandler(rootDirectoryPath, fileAccessor);
    }

    private RequestHandler getBasicAuthHandler() {
        return new BasicAuthHandler();
    }

    public RequestHandler getOptionsAllowAllHandler() {
        return new OptionsAllowAllHandler();
    }

    public RequestHandler getOptionsAllowSelectedHandler() {
        return new OptionsAllowSelectedHandler();
    }

    private RequestHandler getImATeapotHandler() {
        return new ImATeapotHandler();
    }

    public RequestHandler getParametersHandler() {
        return new ParametersHandler();
    }
}
