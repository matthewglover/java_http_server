package com.matthewglover.http_response;

import com.matthewglover.util.FileAccessor;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class OkFileResponse extends OkResponse {

    private String filePath;
    private FileAccessor fileAccessor;

    public void setFile(String filePath, FileAccessor fileAccessor) {
        this.filePath = filePath;
        this.fileAccessor = fileAccessor;
        setContentLengthHeader();
        setMimeTypeHeader(filePath);
    }

    private void setMimeTypeHeader(String filePath) {
        try {
            setHeader("Content-Type", fileAccessor.probeContentType(filePath));
        } catch(Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public long getContentLength() {
        return filePath == null ? 0 : fileAccessor.getFileFromPath(filePath).length();
    }

    @Override
    public void sendResponseOverSocket(OutputStream outputStream) throws Exception {
        DataOutputStream dataStream = new DataOutputStream(outputStream);
        dataStream.writeBytes(getStatusLine());
        dataStream.writeBytes(headersToString());
        dataStream.writeBytes(CRLF);
        sendFileOverSocket(dataStream);
    }

    private void sendFileOverSocket(DataOutputStream outputStream) throws Exception {
        InputStream inputStream = fileAccessor.getFileInputStreamFromFile(filePath);
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while((bytes = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytes);
        }

//        inputStream.close();
    }
}
