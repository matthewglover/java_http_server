package com.matthewglover;

import com.matthewglover.request_handler.RequestRouter;

import java.io.File;

public interface RouterBuilder {
    RequestRouter build(File rootDirectory);
}
