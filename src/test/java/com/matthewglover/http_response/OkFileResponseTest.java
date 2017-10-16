package com.matthewglover.http_response;

import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class OkFileResponseTest {

    private final OkFileResponse fileResponse = (OkFileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();

    @Test
    public void createsOkResponse() {
        fileResponse.setFile("path/to/file", fileAccessorDouble);
        assertTrue(fileResponse instanceof OkFileResponse);
    }

    @Test
    public void hasResponseType() {
        fileResponse.setFile("path/to/file", fileAccessorDouble);
        assertEquals(HttpResponseType.OK, fileResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() throws Exception {
        fileResponse.setFile("path/to/file", fileAccessorDouble);
        String testData = "this is some test data";
        fileAccessorDouble.setFileInputStreamData(testData);
        SocketDouble socketDouble = new SocketDouble();
        fileResponse.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void textFileHasPlainTextMimeTypeHeader() {
        fileResponse.setFile("path/to/file.txt", fileAccessorDouble);
        assertEquals("text/plain", fileResponse.getHeader("Content-Type"));
    }

    @Test
    public void pngFileHasPlainImageMimeTypeHeader() {
        fileResponse.setFile("path/to/file.png", fileAccessorDouble);
        assertEquals("image/png", fileResponse.getHeader("Content-Type"));
    }

    @Test
    public void jpegFileHasPlainImageMimeTypeHeader() {
        fileResponse.setFile("path/to/file.jpeg", fileAccessorDouble);
        assertEquals("image/jpeg", fileResponse.getHeader("Content-Type"));
    }

    @Test
    public void gifFileHasPlainImageMimeTypeHeader() {
        fileResponse.setFile("path/to/file.gif", fileAccessorDouble);
        assertEquals("image/gif", fileResponse.getHeader("Content-Type"));
    }
}