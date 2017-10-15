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
    },

    UNAUTHORIZED_ACCESS {
        @Override
        public String toHeader() {
            return "HTTP/1.1 401 Unauthorized Access";
        }
    },

    METHOD_NOT_ALLOWED {
        @Override
        public String toHeader() {
            return "HTTP/1.1 405 Method Not Allowed";
        }
    },

    PARTIAL_CONTENT {
        @Override
        public String toHeader() {
            return "HTTP/1.1 206 Partial Content";
        }
    };

    public abstract String toHeader();
}
