package com.matthewglover;

import com.matthewglover.http_server.HttpServerInitializer;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class AppLauncher {
    public static void main(String[] args) throws IOException {
        LoggerFactory loggerFactory = getLoggerFactory();
        HttpServerInitializer initializer = new HttpServerInitializer(new ArgumentParser(args), loggerFactory);
        initializer.initialize();
    }

    public static LoggerFactory getLoggerFactory() throws IOException {
        FileHandler fileHandler = new FileHandler("simple_http_server.log");
        fileHandler.setFormatter(new SimpleFormatter());
        LoggerFactory loggerFactory = new LoggerFactory();
        loggerFactory.setHandler(fileHandler);
        return loggerFactory;
    }
}
