package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private final HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
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
        fileAccessor.getFile().setIsFile(true);
        request.setPath("/file1");
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        response.sendResponseOverSocket(socket.getOutputStream());
        assertThat(socket.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void doesntHandleInvalidFilePath() throws Exception {
        fileAccessor.getFile().setIsFile(false);
        request.setPath("/invalid_path");
        assertFalse(requestHandler.handles(request));
    }

    @Test
    public void returns405MethodNotAllowedForPost() throws Exception {
        HttpRequest postRequest = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        postRequest.setPath("file1");
        respondsWithMethodNotAllowed(postRequest);
    }

    @Test
    public void returns405MethodNotAllowedForPut() throws Exception {
        HttpRequest putRequest = HttpTestRequestFactory.get(HttpRequestMethod.PUT);
        putRequest.setPath("file1");
        respondsWithMethodNotAllowed(putRequest);
    }

    @Test
    public void returns405MethodNotAllowedForInvalidMethod() throws Exception {
        HttpRequest invalidMethodRequest = HttpTestRequestFactory.get(HttpRequestMethod.INVALID_METHOD);
        invalidMethodRequest.setPath("file1");
        respondsWithMethodNotAllowed(invalidMethodRequest);
    }

    private void respondsWithMethodNotAllowed(HttpRequest request) {
        fileAccessor.getFile().setIsFile(true);
        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertEquals(HttpResponseType.METHOD_NOT_ALLOWED, response.getResponseType());
    }


}