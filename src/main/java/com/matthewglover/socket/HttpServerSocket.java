package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestBuilder;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final Logger logger;
    private final ServerSocket serverSocket;
    private final LoggerFactory loggerFactory;
    private final RequestRouter requestRouter;
    private ServerSocketAdapter serverSocketAdapter;

    public HttpServerSocket(
            ServerSocket serverSocket,
            RequestRouter requestRouter,
            LoggerFactory loggerFactory) {
        this.serverSocket = serverSocket;
        this.requestRouter = requestRouter;
        this.loggerFactory = loggerFactory;
        logger = loggerFactory.getLogger(HttpServerSocket.class.getName());
    }

    public void connect() {
        try {
            serverSocketAdapter = new ServerSocketAdapter(serverSocket);
            serverSocketAdapter.accept();
        } catch (Exception exception) {
            logger.warning(exception.getMessage());
        }
    }

    public void run() {
        try {
            listenAndRespond();
        } catch (Exception exception) {
            logger.warning(exception.getMessage());
        }
    }

    private void listenAndRespond() throws Exception {
        HttpRequestBuilder httpRequestBuilder =
                new HttpRequestBuilder(serverSocketAdapter.getInputStream(), loggerFactory);
        HttpRequest httpRequest = httpRequestBuilder.build();
        logger.info(httpRequest.toString());
        HttpResponse httpResponse = requestRouter.handleRequest(httpRequest);
        httpResponse.sendResponseOverSocket(serverSocketAdapter.getOutputStream());
        serverSocketAdapter.close();
    }
}
