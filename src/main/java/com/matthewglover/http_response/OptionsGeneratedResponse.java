package com.matthewglover.http_response;

public class OptionsGeneratedResponse extends OkGeneratedResponse {
    public OptionsGeneratedResponse(String allowable) {
        super();
        setHeader("Allow", allowable);
    }
}
