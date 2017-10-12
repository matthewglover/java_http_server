package com.matthewglover.http_response;

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

    NOT_FOUND {
        @Override
        public String toHeader() {
            return "HTTP/1.1 404 Not Found";
        }
    },

    IM_A_TEAPOT {
        @Override
        public String toHeader() {
            return "HTTP/1.1 418 I'm a Teapot";
        }
    };

    public abstract String toHeader();
}
