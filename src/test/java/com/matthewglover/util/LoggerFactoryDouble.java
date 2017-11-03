package com.matthewglover.util;

import java.util.logging.Logger;

public class LoggerFactoryDouble extends LoggerFactory {
    private final LoggerDouble logger = new LoggerDouble();

    @Override
    public Logger getLogger(String loggerName) {
        return logger;
    }

    public LoggerDouble getLoggerDouble() {
        return logger;
    }
}
