package com.matthewglover.http_response;

import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OkFileResponseTest {

    private final OkFileResponse fileResponse = (OkFileResponse) HttpResponseFactory.get(HttpResponseTemplate.OK_FILE);
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();

    @Before
    public void setUp() throws Exception {
        fileResponse.setFile("path/to/file", fileAccessorDouble);
    }

    @Test
    public void createsOkResponse() {
        assertTrue(fileResponse instanceof OkFileResponse);
    }

    @Test
    public void hasResponseType() {
        assertEquals(HttpResponseType.OK, fileResponse.getResponseType());
    }

    @Test
    public void createsResponseWithNoContent() throws Exception {
        String testData = "this is some test data";
        fileAccessorDouble.setFileInputStreamData(testData);
        SocketDouble socketDouble = new SocketDouble();
        fileResponse.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(testData));
    }
}