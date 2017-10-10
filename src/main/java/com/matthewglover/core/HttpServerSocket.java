package com.matthewglover.core;

import java.io.*;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final int port;
    private ServerSocketAdapter serverSocketAdapter;

    public HttpServerSocket(int port, ServerSocketFactory serverSocketFactory, LoggerFactory loggerFactory) {
        this.port = port;
        this.serverSocketFactory = serverSocketFactory;
        logger = loggerFactory.getLogger(HttpServerSocket.class.getName());
    }

    public void run() {
        try {
            serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
            serverSocketAdapter.accept();
            getRequest();
            sendResponse();
        } catch (IOException ioException) {
            logger.warning(ioException.getMessage());
        }
    }

    private void getRequest() throws IOException {
        InputStream inputStream = serverSocketAdapter.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String request = "";
        while ((line = br.readLine()).length() > 0) {
            request += line + "\r\n";
        }
        logger.info(request + "\r\n");
    }


    private void sendResponse() throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.OK);
        httpResponse.setContent("<html><head></head><body></body></html>");
        httpResponse.setContentLengthHeader();
        DataOutputStream outputStream = new DataOutputStream(serverSocketAdapter.getOutputStream());
        outputStream.writeBytes(httpResponse.toString());
        serverSocketAdapter.close();
    }
}
