package com.matthewglover.http_response;

public class OptionsResponse extends OkResponse {
    public OptionsResponse(String allowable) {
        super();
        setHeader("Allow", allowable);
    }
}
