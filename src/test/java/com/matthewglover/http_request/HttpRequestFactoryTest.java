package com.matthewglover.http_request;

import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpRequestFactoryTest {
    @Test
    public void createsRequestForEachRequestMethod() {
        for (HttpRequestMethod method : HttpRequestMethod.values()) {
            HttpRequest request = HttpRequestFactory.get(method, new LoggerFactoryDouble());
            assertEquals(method, request.getMethod());
        }
    }
}