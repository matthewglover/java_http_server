package com.matthewglover.util;

import java.io.*;
import java.nio.file.Files;

public class FileAccessor {
    public File getFileFromPath(String path) {
        return new File(path);
    }

    public InputStream getFileInputStreamFromFile(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }

    public String probeContentType(String filePath) throws IOException {
        File file = new File(filePath);
        String mimeType = Files.probeContentType(file.toPath());
        if (mimeType != null) {
            return mimeType;
        } else {
            return determineMimeTypeFromExtension(filePath);
        }
    }

    public String determineMimeTypeFromExtension(String filePath) {
        if (filePath.matches("^.*\\.txt$")) {
            return "text/plain";
        } else if (filePath.matches("^.*\\.png$")) {
            return "image/png";
        } else if (filePath.matches("^.*\\.jpeg$")) {
            return "image/jpeg";
        } else if (filePath.matches("^.*\\.gif$")) {
            return "image/gif";
        } else {
            return null;
        }
    }
}
