package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartialContentHandlerTest {

    private final String partialContent = "This is a file that contains text to read part of in order to fulfill a 206.\n";
    private final FileAccessorDouble fileAccessor = new FileAccessorDouble();
    private final SocketDouble socket = new SocketDouble();
    private final RequestHandler handler = new PartialContentHandler("path/to/public/", fileAccessor);

    @Before
    public void setUp() throws Exception {
        fileAccessor.setFileInputStreamData(partialContent);
        fileAccessor.getFile().setIsFile(true);
        fileAccessor.getFile().setLength(partialContent.getBytes().length);
    }

    @Test
    public void handlesRoute() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/partial_content.txt")
                .build();
        assertTrue(handler.handles(request));
    }

    @Test
    public void returns206Response() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/partial_content.txt")
                .setHeader("Range", "bytes=0-4")
                .build();
        HttpResponse response = handler.getResponse(request);
        assertEquals(HttpResponseType.PARTIAL_CONTENT, response.getResponseType());
    }

    @Test
    public void returnsPartOfFile() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/partial_content.txt")
                .setHeader("Range", "bytes=0-4")
                .build();
        HttpResponse response = handler.getResponse(request);
        response.sendResponseOverSocket(socket.getOutputStream());
        assertEquals("This ", response.getContent());
    }

    @Test
    public void startRangeDefaultsToZero() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/partial_content.txt")
                .setHeader("Range", "bytes=-6")
                .build();
        HttpResponse response = handler.getResponse(request);
        response.sendResponseOverSocket(socket.getOutputStream());
        assertEquals(" 206.\n", response.getContent());
    }

    @Test
    public void endRangeDefaultsToFileLength() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/partial_content.txt")
                .setHeader("Range", "bytes=4-")
                .build();
        HttpResponse response = handler.getResponse(request);
        response.sendResponseOverSocket(socket.getOutputStream());
        assertEquals(" is a file that contains text to read part of in order to fulfill a 206.\n", response.getContent());
    }
}