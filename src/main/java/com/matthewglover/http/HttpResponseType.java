package com.matthewglover.http;

public enum HttpResponseType {
    OK {
        @Override
        public String toHeader() {
            return "HTTP/1.1 200 OK";
        }
    };

    public abstract String toHeader();
}
