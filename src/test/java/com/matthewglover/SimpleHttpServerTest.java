package com.matthewglover;

import com.matthewglover.socket.*;
import com.matthewglover.util.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SimpleHttpServerTest {

    private final ArgumentParserDouble argumentParser = new ArgumentParserDouble(null);
    private final LoggerDouble logger = new LoggerDouble(null);
    private final LoggerFactoryDouble loggerFactory = new LoggerFactoryDouble();
    private final ServerSocketFactoryDouble serverSocketFactory = new ServerSocketFactoryDouble();
    private final FileAccessor fileAccessor = new FileAccessorDouble();

    public SimpleHttpServerTest() throws IOException {
    }

    @Test
    public void logsInvalidArgumentErrors() {
        loggerFactory.setLogger(logger);
        String invalidArgumentsMessage = "Test Error Message";
        argumentParser.addError(invalidArgumentsMessage);
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(
                argumentParser,
                serverSocketFactory,
                new DefaultRouterBuilder(),
                fileAccessor,
                loggerFactory);
        simpleHttpServer.run();
        assertEquals(invalidArgumentsMessage, logger.popFromMessageType(LoggerDouble.WARNING));
    }
}