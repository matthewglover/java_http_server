package com.matthewglover.core;

import java.util.logging.Logger;

public class LoggerFactoryDouble extends LoggerFactory {
    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Logger getLogger(String loggerName) {
        return logger;
    }
}
