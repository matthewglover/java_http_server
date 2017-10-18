package com.matthewglover;

import com.matthewglover.request_handler.*;
import com.matthewglover.util.FileAccessor;

import java.util.Arrays;

public class DefaultRouterBuilder implements RouterBuilder {

    private final RequestRouter router = new RequestRouter();

    @Override
    public RequestRouter build(String rootDirectoryPath, FileAccessor fileAccessor) {
        addHandlers(rootDirectoryPath, fileAccessor);
        return router;
    }

    private void addHandlers(String rootDirectoryPath, FileAccessor fileAccessor) {
        router.addHandlers(Arrays.asList(
            new BasicAuthHandler(),
                new OptionsAllowAllHandler(),
                new OptionsAllowSelectedHandler(),
                new CookieHandler(),
                new ImATeapotHandler(),
                new FormDataHandler(),
                new ParametersHandler(),
                new RedirectHandler(),
                new PartialContentHandler(rootDirectoryPath, fileAccessor),
                new PatchContentHandler(rootDirectoryPath, fileAccessor),
                new FileHandler(rootDirectoryPath, fileAccessor),
                new DirectoryListingHandler(rootDirectoryPath, fileAccessor)
        ));
    }
}
