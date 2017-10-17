package com.matthewglover.http_response;

import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileResponseTest {

    private final FileResponse response = (FileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);
    private final FileAccessorDouble fileAccessor = new FileAccessorDouble();

    @Test
    public void createsOkResponse() {
        response.setFile("path/to/file", fileAccessor);
        assertTrue(response instanceof FileResponse);
    }

    @Test
    public void hasResponseType() {
        response.setFile("path/to/file", fileAccessor);
        assertEquals(HttpResponseType.OK, response.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() throws Exception {
        response.setFile("path/to/file", fileAccessor);
        String testData = "this is some test data";
        fileAccessor.setFileInputStreamData(testData);
        SocketDouble socketDouble = new SocketDouble();
        response.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void textFileHasPlainTextMimeTypeHeader() {
        response.setFile("path/to/file.txt", fileAccessor);
        assertEquals("text/plain", response.getHeader("Content-Type"));
    }

    @Test
    public void pngFileHasPlainImageMimeTypeHeader() {
        response.setFile("path/to/file.png", fileAccessor);
        assertEquals("image/png", response.getHeader("Content-Type"));
    }

    @Test
    public void jpegFileHasPlainImageMimeTypeHeader() {
        response.setFile("path/to/file.jpeg", fileAccessor);
        assertEquals("image/jpeg", response.getHeader("Content-Type"));
    }

    @Test
    public void gifFileHasPlainImageMimeTypeHeader() {
        response.setFile("path/to/file.gif", fileAccessor);
        assertEquals("image/gif", response.getHeader("Content-Type"));
    }
}