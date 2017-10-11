package com.matthewglover.http_request;

public enum HttpResponseType {
    OK {
        @Override
        public String toHeader() {
            return "HTTP/1.1 200 OK";
        }
    },

    BAD_REQUEST {
        @Override
        public String toHeader() {
            return "HTTP/1.1 400 Bad Request";
        }
    },

    INTERNAL_SERVER_ERROR {
        @Override
        public String toHeader() {
            return "HTTP/1.1 500 Internal Server Error";
        }
    };

    public abstract String toHeader();
}
