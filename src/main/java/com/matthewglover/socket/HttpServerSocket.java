package com.matthewglover.socket;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestParser;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.request_handler.RequestRouter;
import com.matthewglover.util.LoggerFactory;

import java.io.*;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final int port;
    private final LoggerFactory loggerFactory;
    private final RequestRouter requestRouter;
    private ServerSocketAdapter serverSocketAdapter;

    public HttpServerSocket(
            int port, ServerSocketFactory serverSocketFactory,
            RequestRouter requestRouter, LoggerFactory loggerFactory) {
        this.port = port;
        this.serverSocketFactory = serverSocketFactory;
        this.requestRouter = requestRouter;
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

    private void listenAndRespond() throws Exception {
        HttpRequestParser httpRequestParser = getHttpRequestParser();
        httpRequestParser.parse();
        HttpRequest httpRequest = httpRequestParser.getRequest();
        logger.info(httpRequest.toString());
        HttpResponse httpResponse = requestRouter.handleRequest(httpRequest);
        httpResponse.sendResponseOverSocket(serverSocketAdapter.getOutputStream());
        serverSocketAdapter.close();
    }

    private HttpRequestParser getHttpRequestParser() throws IOException {
        HttpRequestStreamAdapter httpRequestStreamAdapter = new HttpRequestStreamAdapter(
                serverSocketAdapter.getInputStream(), loggerFactory);
        return new HttpRequestParser(httpRequestStreamAdapter.getRequest(), loggerFactory);
    }
}
