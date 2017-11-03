package com.matthewglover.http_response;

import com.matthewglover.http_server.SocketDouble;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class HttpResponseTest {

    @Test
    public void buildsResponseForGivenResponseType() throws UnsupportedEncodingException {
        String content = "<html><head></head><body></body></html>";

        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        httpResponse.setHeader("Response-Type", "text/html");
        httpResponse.setContent(content);
        httpResponse.setContentLengthHeader();

        String output = HttpResponseType.OK.toHeader() + "\r\n" +
                "Response-Type: text/html" + "\r\n" +
                "Content-Length: " + content.getBytes("UTF-8").length + "\r\n\r\n" +
                content;

        assertEquals(output, httpResponse.toString());
    }

    @Test
    public void responseOnlyRequiresResponseType() {
        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.BAD_REQUEST);
        String output = HttpResponseType.BAD_REQUEST.toHeader() + "\r\nContent-Length: 0\r\n";
        assertEquals(output, httpResponse.toString());
    }

    @Test
    public void sendResponseToSocket() throws Exception {
        SocketDouble socketDouble = new SocketDouble();
        HttpResponse httpResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        httpResponse.setContent("blah blah blah");
        httpResponse.sendResponseOverSocket(socketDouble.getOutputStream());
        assertEquals(httpResponse.toString(), socketDouble.getOutput().toString());
    }
}