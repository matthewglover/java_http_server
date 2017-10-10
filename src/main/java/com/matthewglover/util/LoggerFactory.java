package com.matthewglover.util;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class LoggerFactory {
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Logger getLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        addHandlerToLogger(logger);
        return logger;
    }

    private void addHandlerToLogger(Logger logger) {
        if (logger.getHandlers().length == 0) {
            logger.addHandler(handler);
        }
    }
}
