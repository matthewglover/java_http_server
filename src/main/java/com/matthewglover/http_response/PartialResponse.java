package com.matthewglover.http_response;

import com.matthewglover.util.FileAccessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PartialResponse extends HttpResponse {

    private String filePath;
    private FileAccessor fileAccessor;
    private int rangeStart;
    private int rangeEnd;

    @Override
    public void setup() {
        setResponseType(HttpResponseType.PARTIAL_CONTENT);
    }

    public void setFile(String filePath, FileAccessor fileAccessor) {
        this.filePath = filePath;
        this.fileAccessor = fileAccessor;
        setMimeTypeHeader(filePath);
    }

    public void setRange(int rangeStart, int rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    private void setMimeTypeHeader(String filePath) {
        try {
            setHeader("Content-Type", fileAccessor.probeContentType(filePath));
        } catch(Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void build() {
        try {
            InputStream inputStream = fileAccessor.getFileInputStreamFromFile(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            processContent(bufferedReader, stringBuilder);
            setContent(stringBuilder.toString());
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void processContent(BufferedReader bufferedReader, StringBuilder stringBuilder) throws IOException {
        for (int count = 0; count < rangeEnd; count++) {
            int data = bufferedReader.read();
            if (count >= rangeStart) {
                stringBuilder.append((char) data);
            }
        }
    }
}
