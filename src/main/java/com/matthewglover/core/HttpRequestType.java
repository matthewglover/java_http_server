package com.matthewglover.core;

public enum HttpRequestType {
    GET {
        @Override
        public String toHeader() {
            return "HTTP/1.1 200 OK";
        }
    };

    public abstract String toHeader();
}
