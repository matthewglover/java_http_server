package com.matthewglover.http_server;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final RequestRouter requestRouter;
    private final ServerSocketAdapter socketAdapter;
    private final LoggerFactory loggerFactory;

    public HttpServerSocket(ServerSocket serverSocket, RequestRouter requestRouter, LoggerFactory loggerFactory) {
        this.requestRouter = requestRouter;
        this.loggerFactory = loggerFactory;
        socketAdapter = new ServerSocketAdapter(serverSocket);
    }

    public void connect() {
        try {
            socketAdapter.accept();
        } catch (Exception exception) {
            handleFatalError(exception);
        }
    }

    public void run() {
        try {
            listenAndRespond();
        } catch (Exception exception) {
            handleFatalError(exception);
        }
    }

    private void listenAndRespond() throws Exception {
        HttpRequest httpRequest = getRequest();
        sendResponse(getResponse(httpRequest));
        closeConnection();
    }

    private HttpRequest getRequest() throws IOException {
        return new HttpRequestParser(socketAdapter.getInputStream()).build();
    }

    private HttpResponse getResponse(HttpRequest httpRequest) {
        return requestRouter.handleRequest(httpRequest);
    }

    private void sendResponse(HttpResponse httpResponse) throws Exception {
        httpResponse.sendResponseOverSocket(socketAdapter.getOutputStream());
    }

    private void closeConnection() throws IOException {
        socketAdapter.close();
    }

    private void handleFatalError(Exception exception) {
        Logger logger = loggerFactory.getLogger(HttpServerSocket.class.getName());
        logger.severe(exception.getMessage());
    }
}
