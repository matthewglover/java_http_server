package com.matthewglover.http_server;

import com.matthewglover.util.AppLogger;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.FileAccessor;

import java.net.ServerSocket;
import java.util.logging.Logger;

public class HttpServerInitializer {
    private final ArgumentParser argumentParser;
    private final Logger logger = AppLogger.getInstance().getLogger(HttpServerInitializer.class.getName());

    public HttpServerInitializer(ArgumentParser argumentParser) {
        this.argumentParser = argumentParser;
    }

    public void initialize() {
        if (argumentParser.hasErrors()) {
            argumentParser.getErrors().forEach(logger::severe);
        } else {
            initializeRunner();
        }
    }

    private void initializeRunner() {
        try {
            ServerSocket serverSocket = new ServerSocket(argumentParser.getPort());
            FileAccessor fileAccessor = new FileAccessor();
            new HttpServerRunner(serverSocket, fileAccessor);
        } catch (Exception exception) {
            logger.severe(exception.getMessage());
        }
    }

}
