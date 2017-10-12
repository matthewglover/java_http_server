package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpRequestParserTest {

    @Test
    public void parsesRawGetRequest() {
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);
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
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);
        ArrayList<String> rawRequest = new ArrayList<>(Arrays.asList(
                "GQMZUUMG /file1 HTTP/1.1"
        ));
        HttpRequestParser httpRequestParser = new HttpRequestParser(rawRequest, loggerFactoryDouble);
        httpRequestParser.parse();
        HttpRequest request = httpRequestParser.getRequest();

        assertTrue(new ResponseComparer(HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST),
                request.buildResponse()).areSame());
    }
}