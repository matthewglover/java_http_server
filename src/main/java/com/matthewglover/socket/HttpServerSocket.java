package com.matthewglover.socket;

import com.matthewglover.http.HttpRequest;
import com.matthewglover.http.HttpRequestParser;
import com.matthewglover.http.HttpResponse;
import com.matthewglover.http.HttpResponseType;
import com.matthewglover.util.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HttpServerSocket {

    private final Logger logger;
    private final ServerSocketFactory serverSocketFactory;
    private final int port;
    private ServerSocketAdapter serverSocketAdapter;
    private HttpRequest httpRequest;

    public HttpServerSocket(int port, ServerSocketFactory serverSocketFactory, LoggerFactory loggerFactory) {
        this.port = port;
        this.serverSocketFactory = serverSocketFactory;
        logger = loggerFactory.getLogger(HttpServerSocket.class.getName());
    }

    public void run() {
        try {
            connectSocket();
            listenAndRespond();
        } catch (IOException ioException) {
            logger.warning(ioException.getMessage());
        }
    }

    private void connectSocket() throws IOException {
        serverSocketAdapter = new ServerSocketAdapter(serverSocketFactory, port);
        serverSocketAdapter.accept();
    }

    private void listenAndRespond() throws IOException {
        HttpRequestParser httpRequestParser = parseRequest();
        if (httpRequestParser.hasErrors()) {
            logger.info("Handling bad request");
            respondWithBadRequest();
        } else {
            processRequest(httpRequestParser);
        }
    }

    private HttpRequestParser parseRequest() throws IOException {
        HttpRequestStreamAdapter httpRequestStreamAdapter =
                new HttpRequestStreamAdapter(serverSocketAdapter.getInputStream());
        ArrayList<String> rawRequest = httpRequestStreamAdapter.getRequest();
        logger.info(rawRequest.get(0));
        HttpRequestParser httpRequestParser =
                new HttpRequestParser(rawRequest);
        httpRequestParser.parse();
        logger.info("Is bad request: " + httpRequestParser.hasErrors());
        return httpRequestParser;
    }

    private void respondWithBadRequest() throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.BAD_REQUEST);
        httpResponse.setHeader("Content-Length", "0");
        DataOutputStream outputStream = new DataOutputStream(serverSocketAdapter.getOutputStream());
        outputStream.writeBytes(httpResponse.toString());
        serverSocketAdapter.close();
    }

    private void processRequest(HttpRequestParser httpRequestParser) throws IOException {
        httpRequest = httpRequestParser.getRequest();
        logRequest();
        processResponse();
    }

    private void logRequest() {
        logger.info(httpRequest.toString());
    }


    private void processResponse() throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseType(HttpResponseType.OK);
        httpResponse.setContent("<html><head></head><body></body></html>");
        httpResponse.setContentLengthHeader();
        DataOutputStream outputStream = new DataOutputStream(serverSocketAdapter.getOutputStream());
        outputStream.writeBytes(httpResponse.toString());
        serverSocketAdapter.close();
    }
}
