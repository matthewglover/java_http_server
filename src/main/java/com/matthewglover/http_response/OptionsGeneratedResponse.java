package com.matthewglover.http_response;

class OptionsGeneratedResponse extends OkGeneratedResponse {
    public OptionsGeneratedResponse(String allowable) {
        super();
        setHeader("Allow", allowable);
    }
}
