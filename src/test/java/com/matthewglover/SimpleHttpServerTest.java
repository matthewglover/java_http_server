package com.matthewglover;

import com.matthewglover.socket.*;
import com.matthewglover.util.ArgumentParserDouble;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SimpleHttpServerTest {

    private final ArgumentParserDouble argumentParser = new ArgumentParserDouble();
    private final LoggerDouble logger = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();

    public SimpleHttpServerTest() throws IOException {
    }

    @Test
    public void logsInvalidArgumentErrors() {
        loggerFactory.setLogger(logger);
        SimpleHttpServer simpleHttpServer =
                new SimpleHttpServer(argumentParser, serverSocketFactory, new DefaultRouter(), loggerFactory);
        String invalidArgumentsMessage = "Test Error Message";
        argumentParser.addError(invalidArgumentsMessage);
        simpleHttpServer.run();
        assertEquals(invalidArgumentsMessage, logger.popFromMessageType(LoggerDouble.WARNING));
    }
}