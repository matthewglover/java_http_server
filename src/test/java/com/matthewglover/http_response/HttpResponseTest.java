package com.matthewglover.http_request;

import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class HttpResponseTest {
    private final HttpResponse httpResponse = new HttpResponse();

    @Test
    public void buildsRequestForGivenResponseType() throws UnsupportedEncodingException {
        String content = "<html><head></head><body></body></html>";
        int contentLength = content.getBytes("UTF-8").length;
        httpResponse.setResponseType(HttpResponseType.OK);
        httpResponse.setHeader("Response-Type", "text/html");
        httpResponse.setContent(content);
        httpResponse.setContentLengthHeader();
        String output = HttpResponseType.OK.toHeader() + "\r\n" +
                "Response-Type: text/html" + "\r\n" +
                "Content-Length: " + contentLength + "\r\n\r\n" +
                content + "\r\n";

        assertEquals(output, httpResponse.toString());
    }

    @Test
    public void responseOnlyRequiresResponseType() {
        httpResponse.setResponseType(HttpResponseType.BAD_REQUEST);
        String output = HttpResponseType.BAD_REQUEST.toHeader() + "\r\n\r\n";
        assertEquals(output, httpResponse.toString());
    }
}