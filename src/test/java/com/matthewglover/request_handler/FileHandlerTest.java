package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private final HttpRequest simpleGet = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final RequestHandler requestHandler = new FileHandler("path/to/public", fileAccessorDouble);
    private final String testData = "this is some test data";
    private final SocketDouble socketDouble = new SocketDouble();

    @Before
    public void setUp() throws Exception {
        fileAccessorDouble.setFileInputStreamData(testData);
    }

    @Test
    public void returnsFileForValidFilePath() throws Exception {
        fileAccessorDouble.getFile().setIsFile(true);
        simpleGet.setPath("/file1");
        assertTrue(requestHandler.handles(simpleGet));
        HttpResponse response = requestHandler.getResponse(simpleGet);
        response.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void doesntHandleInvalidFilePath() throws Exception {
        fileAccessorDouble.getFile().setIsFile(false);
        simpleGet.setPath("/file1");
        assertFalse(requestHandler.handles(simpleGet));
    }
}