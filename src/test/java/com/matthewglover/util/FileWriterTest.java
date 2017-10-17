package com.matthewglover.util;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class FileWriterTest {
    @Test
    public void writesToFile() throws IOException {
        FileAccessorDouble fileAccessor = new FileAccessorDouble();
        FileWriter fileWriter = new FileWriter("path/to/file", fileAccessor);
        fileWriter.write("default content");
        assertEquals("default content", fileAccessor.getFileOutput());
    }
}