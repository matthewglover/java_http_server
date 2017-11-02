package com.matthewglover.http_server;

import com.matthewglover.util.AppLogger;
import com.matthewglover.util.ArgumentParserDouble;
import com.matthewglover.util.TestStreamHandler;
import org.hamcrest.CoreMatchers;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.util.logging.SimpleFormatter;

import static org.junit.Assert.*;

public class HttpServerInitializerTest {

    @Test
    public void logsErrorWhenInvalidAppArguments() {
        ArgumentParserDouble argumentParserDouble = new ArgumentParserDouble();
        argumentParserDouble.addError("Test Error");

        AppLogger appLogger = AppLogger.getInstance();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        appLogger.setHandler(new TestStreamHandler(outputStream, new SimpleFormatter()));

        HttpServerInitializer httpServerInitializer = new HttpServerInitializer(argumentParserDouble);
        httpServerInitializer.initialize();

        assertThat(outputStream.toString(), CoreMatchers.containsString("SEVERE: Test Error"));
    }
}