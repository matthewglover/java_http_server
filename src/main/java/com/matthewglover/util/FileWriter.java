package com.matthewglover.util;

import java.io.OutputStream;

public class FileWriter {
    private final String filePath;
    private final FileAccessor fileAccessor;

    public FileWriter(String filePath, FileAccessor fileAccessor) {
        this.filePath = filePath;
        this.fileAccessor = fileAccessor;
    }

    public void write(String content) {
        try {
            OutputStream stream = fileAccessor.getFileOutputStreamFromFile(filePath);
            stream.write(content.getBytes());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
