package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpRequestParserTest {
    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final FileDouble rootDirectory = new FileDouble("/path/to/public");

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void parsesRawGetRequest() {
        HttpRequest testRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        testRequest.setPath("/");
        ArrayList<String> rawRequest = testRequest.toRaw();

        HttpRequestParser httpRequestParser = new HttpRequestParser(rawRequest, loggerFactoryDouble);
        httpRequestParser.parse();
        HttpRequest request = httpRequestParser.getRequest();

        assertEquals(HttpRequestMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.1", request.getVersion());
    }


    @Test
    public void invalidMethod() throws UnsupportedEncodingException {
        ArrayList<String> rawRequest = new ArrayList<>(Arrays.asList("GQMZUUMG /file1 HTTP/1.1"));
        HttpRequestParser httpRequestParser = new HttpRequestParser(rawRequest, loggerFactoryDouble);
        httpRequestParser.parse();
        HttpRequest request = httpRequestParser.getRequest();

        assertTrue(new ResponseComparer(HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST),
                request.buildResponse(rootDirectory)).areSame());
    }
}