package com.matthewglover.util;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class AppLoggerTest {

    @Test
    public void sameLoggerNameAlwaysReturnsSameLogger() {
        AppLogger appLogger = AppLogger.getInstance();
        String loggerName = "test.logger";

        Logger loggerA = appLogger.getLogger(loggerName);
        Logger loggerB = appLogger.getLogger(loggerName);
        assertSame(loggerA, loggerB);
    }

    @Test
    public void onlyAddsSingleHandlerToLogger() throws IOException {
        AppLogger appLogger = AppLogger.getInstance();
        String loggerName = "test.logger";

        appLogger.setHandler(new FileHandler("log.txt"));
        Logger logger = appLogger.getLogger(loggerName);
        appLogger.getLogger(loggerName);

        assertEquals(1, logger.getHandlers().length);
    }
}