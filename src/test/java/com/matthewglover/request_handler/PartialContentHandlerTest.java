package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartialContentHandlerTest {

    private final String partialContent = "This is a file that contains text to read part of in order to fulfill a 206.\n";
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final SocketDouble socketDouble = new SocketDouble();
    private final HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final RequestHandler handler = new PartialContentHandler("path/to/public/", fileAccessorDouble);

    @Before
    public void setUp() throws Exception {
        fileAccessorDouble.setFileInputStreamData(partialContent);
        fileAccessorDouble.getFile().setIsFile(true);
        fileAccessorDouble.getFile().setLength(partialContent.getBytes().length);
        request.setPath("/partial_content.txt");
    }

    @Test
    public void handlesRoute() {
        assertTrue(handler.handles(request));
    }

    @Test
    public void returns206Response() {
        request.setHeader("Range", "bytes=0-4");
        HttpResponse response = handler.getResponse(request);
        assertEquals(HttpResponseType.PARTIAL_CONTENT, response.getResponseType());
    }

//    @Test
//    public void returnsPartOfFile() throws Exception {
//        request.setHeader("Range", "bytes=0-4");
//        HttpResponse response = handler.getResponse(request);
//        response.sendResponseOverSocket(socketDouble.getOutputStream());
//        assertEquals("This", response.getContent());
//    }
//
//    @Test
//    public void startRangeDefaultsToZero() throws Exception {
//        request.setHeader("Range", "bytes=-6");
//        HttpResponse response = handler.getResponse(request);
//        response.sendResponseOverSocket(socketDouble.getOutputStream());
//        assertEquals("This i", response.getContent());
//    }
//
//    @Test
//    public void endRangeDefaultsToFileLength() throws Exception {
//        request.setHeader("Range", "bytes=4-");
//        HttpResponse response = handler.getResponse(request);
//        response.sendResponseOverSocket(socketDouble.getOutputStream());
//        assertEquals(" is a file that contains text to read part of in order to fulfill a 206.\n", response.getContent());
//    }
}