package com.matthewglover.util;

import com.matthewglover.http_request.FileDouble;

import java.io.*;

public class FileAccessorDouble extends FileAccessor {
    private final FileDouble fileDouble = new FileDouble("");
    private String fileInputStreamData;

    @Override
    public File getFileFromPath(String path) {
        return fileDouble;
    }

    @Override
    public InputStream getFileInputStreamFromFile(String filePath) throws FileNotFoundException {
        return new ByteArrayInputStream(fileInputStreamData.getBytes());
    }

    @Override
    public String probeContentType(String filePath) throws IOException {
        return new FileAccessor().determineMimeTypeFromExtension(filePath);
    }

    public void setFileInputStreamData(String fileInputStreamData) {
        this.fileInputStreamData = fileInputStreamData;
    }

    public FileDouble getFile() {
        return fileDouble;
    }
}
