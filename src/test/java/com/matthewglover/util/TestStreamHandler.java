package com.matthewglover.util;

import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class TestStreamHandler extends StreamHandler {

    public TestStreamHandler (OutputStream out, Formatter f) {
        super(out, f);
    }

    @Override
    public synchronized void publish(LogRecord record) {
        super.publish(record);
        flush();
    }

    @Override
    public void close() throws SecurityException {
        flush();
    }
}
