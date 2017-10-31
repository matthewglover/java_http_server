package com.matthewglover;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.FileAccessor;

interface RouterBuilder {
    RequestRouter build(String rootDirectoryPath, FileAccessor fileAccessor);
}
