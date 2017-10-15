package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParametersHandlerTest {
    @Test
    public void respondsWithQueryParamsAsContent() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        request.setPath("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2" +
                "C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");
        RequestHandler handler = new ParametersHandler();
        assertTrue(handler.handles(request));
        HttpResponse response = handler.getResponse(request);
        assertEquals(HttpResponseType.OK, response.getResponseType());
        assertThat(response.getContent(),
                CoreMatchers.containsString("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
    }
}