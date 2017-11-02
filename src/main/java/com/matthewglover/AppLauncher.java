package com.matthewglover;

import com.matthewglover.socket.ServerSocketFactory;
import com.matthewglover.util.ArgumentParser;
import com.matthewglover.util.FileAccessor;
import com.matthewglover.util.LoggerFactory;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class AppLauncher {
    public static void main(String[] args) throws IOException {
        SimpleHttpServer simpleHttpServer = getSimpleHttpServer(args);
        simpleHttpServer.run();
    }

    private static SimpleHttpServer getSimpleHttpServer(String[] args) throws IOException {
        return new SimpleHttpServer(
                    new ArgumentParser(args),
                    new ServerSocketFactory(),
                    new FileAccessor(),
                    getLoggerFactory());
    }

    public static LoggerFactory getLoggerFactory() throws IOException {
        FileHandler fileHandler = new FileHandler("simple_http_server.log");
        fileHandler.setFormatter(new SimpleFormatter());

        LoggerFactory loggerFactory = new LoggerFactory();
        loggerFactory.setHandler(fileHandler);
        return loggerFactory;
    }
}
