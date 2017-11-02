package com.matthewglover.util;


import java.util.logging.Handler;
import java.util.logging.Logger;

public class AppLogger {

    private static AppLogger singleton;

    public static AppLogger getInstance() {
        if (singleton == null) {
            singleton = new AppLogger();
        }

        return singleton;
    }

    private Handler handler;

    private AppLogger() {
    }

    public Logger getLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        if (handler != null) addHandlerToLogger(logger);
        return logger;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void addHandlerToLogger(Logger logger) {
        if (logger.getHandlers().length == 0) {
            logger.addHandler(handler);
        }
    }
}
