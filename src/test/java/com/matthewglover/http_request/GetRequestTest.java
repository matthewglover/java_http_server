package com.matthewglover.http_request;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetRequestTest {
    @Test
    public void extractsParametersFromPath() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        request.setPath("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C" +
                "%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?",
                request.getQueryParam("variable_1"));
    }
}
