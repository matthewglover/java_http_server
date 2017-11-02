package com.matthewglover;

import com.matthewglover.http_server.HttpServerInitializer;
import com.matthewglover.util.AppLogger;
import com.matthewglover.util.ArgumentParser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class AppLauncher {
    public static void main(String[] args) throws IOException {
        initializeLogger();
        HttpServerInitializer initializer = new HttpServerInitializer(new ArgumentParser(args));
        initializer.initialize();
    }

    public static void initializeLogger() throws IOException {
        FileHandler fileHandler = new FileHandler("simple_http_server.log");
        fileHandler.setFormatter(new SimpleFormatter());

        AppLogger appLogger = AppLogger.getInstance();
        appLogger.setHandler(fileHandler);
    }
}
