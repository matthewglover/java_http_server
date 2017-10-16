package com.matthewglover.http_response;

import com.matthewglover.util.FileAccessorDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PartialResponseTest {

    private final PartialResponse response = (PartialResponse) HttpResponseFactory.get(HttpResponseTemplate.PARTIAL_CONTENT);
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final String partialContent = "This is a file that contains text to read part of in order to fulfill a 206.\n";

    @Before
    public void setUp() throws Exception {
        fileAccessorDouble.setFileInputStreamData(partialContent);
        fileAccessorDouble.getFile().setLength(partialContent.getBytes().length);
    }

    @Test
    public void createsOkResponse() {
        assertTrue(response instanceof PartialResponse);
    }

    @Test
    public void setsContentOfFirstFourBytesWithRange0to4() throws IOException {
        response.setFile("/partial_content.txt", fileAccessorDouble);
        response.setRange(0, 4);
        response.build();
        assertEquals("This ", response.getContent());
    }

    @Test
    public void setsContentOfFirstFourBytesWithRange4to10() throws IOException {
        response.setFile("/partial_content.txt", fileAccessorDouble);
        response.setRange(4, 10);
        response.build();
        assertEquals(" is a f", response.getContent());
    }
}