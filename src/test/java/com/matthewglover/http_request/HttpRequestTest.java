package com.matthewglover.http_request;

import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class HttpRequestTest {

    @Test
    public void buildsRequestForGivenRequestType() {
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);

        HttpRequest httpRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        httpRequest.setPath("/path/to/get");

        String requestString = httpRequest.toString();
        Pattern requestLinePattern = Pattern.compile("^GET\\s+/path/to/get\\s+HTTP/1.1\\r\\n", Pattern.MULTILINE);
        Pattern requestEndPattern = Pattern.compile("\\r\\n\\r\\n$", Pattern.MULTILINE);
        assertTrue(requestLinePattern.matcher(requestString).find());
        assertTrue(requestEndPattern .matcher(requestString).find());
    }
}