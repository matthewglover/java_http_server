package com.matthewglover.http_response;

import java.io.DataOutputStream;
import java.io.RandomAccessFile;

public class OkPartialFileResponse extends OkFileResponse {

    private int rangeStart;
    private int rangeEnd;

    @Override
    public void setup() {
        setResponseType(HttpResponseType.PARTIAL_CONTENT);
    }

    public void setRange(int rangeStart, int rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    @Override
    public void sendFileOverSocket(DataOutputStream outputStream ) throws Exception {
        RandomAccessFile randomAccessFile = fileAccessor.getRandomAccessFileFromPath(filePath);
        byte[] buffer = new byte[1024];
        randomAccessFile.read(buffer, rangeStart, rangeEnd);
        outputStream.write(buffer);
        randomAccessFile.close();
    }
}
