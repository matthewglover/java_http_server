package com.matthewglover.http_request;

import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpRequestTest {

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
    public void buildsRequestForGivenRequestType() {
        String requestString = testRequest.toString();
        Pattern requestLinePattern = Pattern.compile("^GET\\s+/test/path\\s+HTTP/1.0\\r\\n", Pattern.MULTILINE);
        Pattern requestEndPattern = Pattern.compile("\\r\\n\\r\\n$", Pattern.MULTILINE);
        assertTrue(requestLinePattern.matcher(requestString).find());
        assertTrue(requestEndPattern .matcher(requestString).find());
    }

    @Test
    public void parsesRawGetRequest() {
        ArrayList<String> rawRequest = testRequest.toRaw();
        HttpRequest request = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        RawRequestParser parser = new RawRequestParser(rawRequest);
        request.parse(parser);

        assertEquals(HttpRequestMethod.GET, request.getMethod());
        assertEquals("/test/path", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("Value1", request.getHeader("Header1"));
        assertEquals("Value2", request.getHeader("Header2"));
    }
}