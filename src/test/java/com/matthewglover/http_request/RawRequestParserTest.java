package com.matthewglover.http_request;

import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RawRequestParserTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpRequest testRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
        testRequest.setPath("/test/path");
        testRequest.setVersion("HTTP/1.0");
        testRequest.setHeader("Header1", "Value1");
        testRequest.setHeader("Header2", "Value2");
    }

    @Test
    public void extractsMethod() {
        RawRequestParser parser = new RawRequestParser(testRequest.toRaw());
        assertEquals("GET", parser.getMethod());
    }

    @Test
    public void extractsPath() {
        RawRequestParser parser = new RawRequestParser(testRequest.toRaw());
        assertEquals("/test/path", parser.getPath());
    }

    @Test
    public void extractsVersion() {
        RawRequestParser parser = new RawRequestParser(testRequest.toRaw());
        assertEquals("HTTP/1.0", parser.getVersion());
    }

    @Test
    public void convertsRawRequestHeadersToKeyValueList() {
        RawRequestParser parser = new RawRequestParser(testRequest.toRaw());
        List<String[]> rawRequestHeaders = parser.getRawRequestHeaders();
        assertArrayEquals(rawRequestHeaders.get(0), new String[]{"Header1", "Value1"});
        assertArrayEquals(rawRequestHeaders.get(1), new String[]{"Header2", "Value2"});
    }

}