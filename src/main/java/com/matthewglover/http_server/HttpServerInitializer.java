package com.matthewglover.http_server;

import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.LoggerFactory;

import java.util.logging.Logger;

public class HttpServerInitializer {
    private final ArgumentParser argumentParser;
    private final LoggerFactory loggerFactory;
    private final Logger logger;

    public HttpServerInitializer(ArgumentParser argumentParser, LoggerFactory loggerFactory) {
        this.argumentParser = argumentParser;
        this.loggerFactory = loggerFactory;
        this.logger = loggerFactory.getLogger(HttpServerInitializer.class.getName());
    }

    public void initialize() {
        if (argumentParser.hasErrors()) {
            argumentParser.getErrors().forEach(logger::severe);
        } else {
            HttpServerRunner runner = new HttpServerRunner(argumentParser, loggerFactory);
            runner.run();
        }
    }
}
