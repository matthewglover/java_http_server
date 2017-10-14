package com.matthewglover.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileAccessor {
    public File getFileFromPath(String path) {
        return new File(path);
    }

    public InputStream getFileInputStreamFromFile(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }
}
