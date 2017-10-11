package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.ResponseHandler;
import com.matthewglover.util.LoggerFactory;

import java.io.*;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final int port;
    private final LoggerFactory loggerFactory;
    private ServerSocketAdapter serverSocketAdapter;

    public HttpServerSocket(int port, ServerSocketFactory serverSocketFactory, LoggerFactory loggerFactory) {
        this.port = port;
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
        serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
        serverSocketAdapter.accept();
    }

    private void listenAndRespond() throws IOException {
        ResponseHandler responseHandler = new ResponseHandler(loggerFactory);
        HttpResponse httpResponse = responseHandler.handleRequest(getHttpRequestParser());
        sendResponse(httpResponse);
    }

    private HttpRequestParser getHttpRequestParser() throws IOException {
        HttpRequestStreamAdapter httpRequestStreamAdapter = new HttpRequestStreamAdapter(
                serverSocketAdapter.getInputStream());
        return new HttpRequestParser(httpRequestStreamAdapter.getRequest());
    }

    private void sendResponse(HttpResponse httpResponse) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(serverSocketAdapter.getOutputStream());
        outputStream.writeBytes(httpResponse.toString());
        serverSocketAdapter.close();
    }

}
