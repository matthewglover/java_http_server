package com.matthewglover.core;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

public class TextWriterAdapter extends WriteAdapter {
    private final String STRING_ENCODING = "UTF-8";

    public void setInput(String input) throws UnsupportedEncodingException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(STRING_ENCODING));
        setInputStream(inputStream);
    }
}
