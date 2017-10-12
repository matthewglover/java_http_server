package com.matthewglover.http_response;

import java.io.UnsupportedEncodingException;

public class OptionsResponse extends OkResponse {
    public OptionsResponse(String allowable) throws UnsupportedEncodingException {
        super();
        setHeader("Allow", allowable);
    }
}
