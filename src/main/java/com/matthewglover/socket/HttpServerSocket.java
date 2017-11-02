package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.AppLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final RequestRouter requestRouter;
    private final ServerSocketAdapter socketAdapter;

    public HttpServerSocket(ServerSocket serverSocket, RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        socketAdapter = new ServerSocketAdapter(serverSocket);
    }

    public HttpServerSocket(ServerSocketAdapter socketAdapter, RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        this.socketAdapter = socketAdapter;
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
        AppLogger appLogger = AppLogger.getInstance();
        Logger logger = appLogger.getLogger(HttpServerSocket.class.getName());
        logger.severe(exception.getMessage());
    }
}
