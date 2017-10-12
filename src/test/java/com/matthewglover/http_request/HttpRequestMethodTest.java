package com.matthewglover.http_request;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HttpRequestMethodTest {

    @Test
    public void returnsListOfRequestMethodsAsStrings() {
        List<String> methodList = new ArrayList<>(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"));
        assertEquals(methodList, HttpRequestMethod.getMethodList());
    }

    @Test
    public void validatesMethodString() {
        assertTrue(HttpRequestMethod.isValid("GET"));
        assertFalse(HttpRequestMethod.isValid("INCORRECT_METHOD_NAME"));
    }

    @Test
    public void returnsMatchingMethodEnumForValidMethodName() {
        assertEquals(HttpRequestMethod.GET, HttpRequestMethod.parse("GET"));
    }

    @Test
    public void returnsInvalidMethodEnumForInvalidMethodName() {
        assertEquals(HttpRequestMethod.INVALID_METHOD, HttpRequestMethod.parse("INCORRECT_METHOD_NAME"));
    }
}