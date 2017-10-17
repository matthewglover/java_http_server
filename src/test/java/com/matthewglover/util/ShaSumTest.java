package com.matthewglover.util;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class ShaSumTest {
    @Test
    public void convertsDefaultFileContentToCorrectShaSum() throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("default content".getBytes("UTF-8"));
        ShaSum shaSum = new ShaSum();
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", shaSum.generate(inputStream));
    }

    @Test
    public void convertsPatchedFileContentToCorrectShaSum() throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("patched content".getBytes("UTF-8"));
        ShaSum shaSum = new ShaSum();
        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", shaSum.generate(inputStream));
    }
}