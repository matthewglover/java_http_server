package com.matthewglover.util;

import java.util.logging.Logger;

public class LoggerFactoryDouble extends LoggerFactory {
    private Logger logger;

    public LoggerFactoryDouble() {
        setLogger(new LoggerDouble(null));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Logger getLogger(String loggerName) {
        return logger;
    }
}
