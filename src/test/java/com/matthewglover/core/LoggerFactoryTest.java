package com.matthewglover.core;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LoggerFactoryTest {

    private final LoggerFactory loggerFactory = new LoggerFactory();
    private final String loggerName = "logger.one";

    @Test
    public void sameLoggerNameAlwaysReturnsSameLogger() {
        Logger loggerA = loggerFactory.getLogger(loggerName);
        Logger loggerB = loggerFactory.getLogger(loggerName);
        assertSame(loggerA, loggerB);
    }

    @Test
    public void onlyAddsSingleHandlerToLogger() throws IOException {
        loggerFactory.setHandler(new FileHandler("log.txt"));
        Logger logger = loggerFactory.getLogger(loggerName);
        loggerFactory.getLogger(loggerName);
        assertEquals(1, logger.getHandlers().length);
    }
}