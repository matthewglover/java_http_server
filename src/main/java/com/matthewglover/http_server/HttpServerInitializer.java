package com.matthewglover.http_server;

import com.matthewglover.util.AppLogger;
import com.matthewglover.util.ArgumentParser;

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
            HttpServerRunner runner = new HttpServerRunner(argumentParser);
            runner.run();
        }
    }
}
