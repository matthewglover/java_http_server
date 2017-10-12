package com.matthewglover.http_request;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum HttpRequestMethod {
    GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS, INVALID_METHOD;

    public static List<String> getMethodList() {
        return Stream.of(HttpRequestMethod.values())
                .filter(method -> method != INVALID_METHOD)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public static boolean isValid(String method) {
        return getMethodList().contains(method);
    }

    public static HttpRequestMethod parse(String method) {
        return isValid(method) ? valueOf(method) : INVALID_METHOD;
    }
}
