package com.matthewglover.http_server;

import com.matthewglover.util.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class HttpServerInitializerTest {

    @Test
    public void logsErrorWhenInvalidAppArguments() {
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        ArgumentParserDouble argumentParserDouble = new ArgumentParserDouble();
        argumentParserDouble.addError("Test Error");


        HttpServerInitializer httpServerInitializer = new HttpServerInitializer(argumentParserDouble, loggerFactoryDouble);
        httpServerInitializer.initialize();

        assertThat(loggerFactoryDouble.getLoggerDouble().popFromMessageType("SEVERE"), containsString("Test Error"));
    }
}