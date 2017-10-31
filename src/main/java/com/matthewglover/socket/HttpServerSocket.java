package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

class HttpServerSocket {

    private final Logger logger;
    private final LoggerFactory loggerFactory;
    private final RequestRouter requestRouter;
    private final ServerSocketAdapter socketAdapter;

    public HttpServerSocket(ServerSocket serverSocket, RequestRouter requestRouter, LoggerFactory loggerFactory) {
        this.requestRouter = requestRouter;
        this.loggerFactory = loggerFactory;
        socketAdapter = new ServerSocketAdapter(serverSocket);
        logger = loggerFactory.getLogger(HttpServerSocket.class.getName());
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
        return new HttpRequestBuilder(socketAdapter.getInputStream(), loggerFactory).getRequest();
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
        logger.warning(exception.getMessage());
    }
}
