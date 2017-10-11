package com.matthewglover.http_request;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class HttpRequestTest {

    @Test
    public void buildsRequestForGivenRequestType() {
        HttpRequest httpRequest = HttpRequestFactory.get(HttpRequestType.SIMPLE_GET);
        httpRequest.setPath("/path/to/get");

        String requestString = httpRequest.toString();
        Pattern requestLinePattern = Pattern.compile("^GET\\s+/path/to/get\\s+HTTP/1.1\\r\\n", Pattern.MULTILINE);
        Pattern requestEndPattern = Pattern.compile("\\r\\n\\r\\n$", Pattern.MULTILINE);
        assertTrue(requestLinePattern.matcher(requestString).find());
        assertTrue(requestEndPattern .matcher(requestString).find());
    }
}