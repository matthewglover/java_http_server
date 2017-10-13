package com.matthewglover.http_request;

import com.matthewglover.http_response.OkResponse;

import java.io.*;

public class FileResponse extends OkResponse {
    private File file;

    public FileResponse() throws UnsupportedEncodingException {
        super();
    }

    public void setFile(File rootDirectory, String filePath) {
        file = new File(rootDirectory.getPath() + filePath);
        setContentLengthHeader();
        setHeader("Content-Type", "text/plain");
    }

    @Override
    public void sendResponseOverSocket(OutputStream outputStream) throws Exception {
        System.out.println("Sending data over socket");
        DataOutputStream dataStream = new DataOutputStream(outputStream);
        dataStream.writeBytes(getStatusLine());
        dataStream.writeBytes(headersToString());
        dataStream.writeBytes(CRLF);
        sendFileOverSocket(dataStream);
        System.out.println("Data sent over socket");
    }

    @Override
    public long getContentLength() {
        return file == null ? 0 : file.length();
    }

    private void sendFileOverSocket(DataOutputStream outputStream) throws Exception {
        System.out.println("Sending file over socket");
        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while((bytes = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytes);
        }

        inputStream.close();
    }
}
