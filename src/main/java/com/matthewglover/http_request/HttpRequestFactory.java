package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactory;

public class HttpRequestFactory {
    public static HttpRequest get(HttpRequestMethod httpRequestMethod, LoggerFactory loggerFactory) {
        switch (httpRequestMethod) {
            case DELETE: return new DeleteRequest(loggerFactory);
            case PATCH: return new PatchRequest(loggerFactory);
            case PUT: return new PutRequest(loggerFactory);
            case POST: return new PostRequest(loggerFactory);
            case INVALID_METHOD: return new BadRequest(loggerFactory);
            case OPTIONS: return new OptionsRequest(loggerFactory);
            case HEAD: return new HeadRequest(loggerFactory);
            case GET: return new GetRequest(loggerFactory);
            default: return null;
        }
    }
}
