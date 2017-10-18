package com.matthewglover;

import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.FileAccessor;

public interface RouterBuilder {
    RequestRouter build(String rootDirectoryPath, FileAccessor fileAccessor);
}
