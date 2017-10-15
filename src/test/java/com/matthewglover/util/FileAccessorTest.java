package com.matthewglover.util;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FileAccessorTest {

    private final FileAccessor fileAccessor = new FileAccessor();

    @Test
    public void textFileHasCorrectMimeType() throws IOException {
        assertEquals("text/plain", fileAccessor.determineMimeTypeFromExtension("path/to/file.txt"));
    }

    @Test
    public void pngFileHasCorrectMimeType() throws IOException {
        assertEquals("image/png", fileAccessor.determineMimeTypeFromExtension("path/to/file.png"));
    }

    @Test
    public void jpegFileHasCorrectMimeType() throws IOException {
        assertEquals("image/jpeg", fileAccessor.determineMimeTypeFromExtension("path/to/file.jpeg"));
    }

    @Test
    public void gifFileHasCorrectMimeType() throws IOException {
        assertEquals("image/gif", fileAccessor.determineMimeTypeFromExtension("path/to/file.gif"));
    }
}