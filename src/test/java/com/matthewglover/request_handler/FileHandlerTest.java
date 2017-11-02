package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private final FileAccessorDouble fileAccessor = new FileAccessorDouble();
    private final RequestHandler requestHandler = new FileHandler("path/to/public", fileAccessor);
    private final String testData = "this is some test data";
    private final SocketDouble socket = new SocketDouble();

    @Before
    public void setUp() throws Exception {
        fileAccessor.setFileInputStreamData(testData);
    }

    @Test
    public void returnsFileForValidFilePath() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/file1")
                .build();
        fileAccessor.getFile().setIsFile(true);
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        response.sendResponseOverSocket(socket.getOutputStream());
        assertThat(socket.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void doesntHandleInvalidFilePath() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/invalid_path")
                .build();
        fileAccessor.getFile().setIsFile(false);
        assertFalse(requestHandler.handles(request));
    }

    @Test
    public void returns405MethodNotAllowedForPost() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/file1")
                .build();
        respondsWithMethodNotAllowed(request);
    }

    @Test
    public void returns405MethodNotAllowedForPut() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.PUT)
                .setPath("/file1")
                .build();
        respondsWithMethodNotAllowed(request);
    }

    @Test
    public void returns405MethodNotAllowedForInvalidMethod() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.INVALID_METHOD)
                .setPath("/file1")
                .build();
        respondsWithMethodNotAllowed(request);
    }

    private void respondsWithMethodNotAllowed(HttpRequest request) {
        fileAccessor.getFile().setIsFile(true);
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(HttpResponseType.METHOD_NOT_ALLOWED, response.getResponseType());
    }


}