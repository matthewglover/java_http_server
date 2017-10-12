package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.util.LoggerFactory;

import java.io.*;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final int port;
    private final LoggerFactory loggerFactory;
    private final String filePath;
    private ServerSocketAdapter serverSocketAdapter;

    public HttpServerSocket(int port, String filePath, ServerSocketFactory serverSocketFactory, LoggerFactory loggerFactory) {
        this.port = port;
        this.filePath = filePath;
        this.serverSocketFactory = serverSocketFactory;
        this.loggerFactory = loggerFactory;
        logger = loggerFactory.getLogger(HttpServerSocket.class.getName());
    }

    public void run() {
        try {
            connectSocket();
            listenAndRespond();
        } catch (Exception exception) {
            logger.warning(exception.getMessage());
        }
    }

    private void connectSocket() throws IOException {
        serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port, filePath);
        serverSocketAdapter.accept();
    }

    private void listenAndRespond() throws IOException {
        HttpRequestParser httpRequestParser = getHttpRequestParser();
        httpRequestParser.parse();
        HttpRequest httpRequest = httpRequestParser.getRequest();
        HttpResponse httpResponse = httpRequest.buildResponse(filePath);
        sendResponse(httpResponse);
    }

    private HttpRequestParser getHttpRequestParser() throws IOException {
        HttpRequestStreamAdapter httpRequestStreamAdapter = new HttpRequestStreamAdapter(
                serverSocketAdapter.getInputStream(), loggerFactory);
        return new HttpRequestParser(httpRequestStreamAdapter.getRequest(), loggerFactory);
    }

    private void sendResponse(HttpResponse httpResponse) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(serverSocketAdapter.getOutputStream());
        outputStream.writeBytes(httpResponse.toString());
        serverSocketAdapter.close();
    }

}
