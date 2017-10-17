package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormDataHandlerTest {

    private final RequestHandler requestHandler = new FormDataHandler();

    @Before
    public void setUp() throws Exception {
        requestHandler.addHandledPath("/form");
    }

    @Test
    public void handlesPostRequestToForm() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        request.setPath("/form");
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(request).getResponseType());
    }

    @Test
    public void handlesPutRequestToForm() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.PUT);
        request.setPath("/form");
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(request).getResponseType());
    }

    @Test
    public void sendsFormDataBackInResponseBody() {
        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        request.setPath("/form");
        request.setContent("data=fatcat");
        assertTrue(requestHandler.handles(request));
    }

    @Test
    public void handlerHasTemporalState() {
        HttpRequest postRequest = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        postRequest.setPath("/form");
        postRequest.setContent("data=fatcat");


        HttpRequest getRequest = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        getRequest.setPath("/form");

        HttpRequest deleteRequest = HttpTestRequestFactory.get(HttpRequestMethod.DELETE);
        deleteRequest.setPath("/form");

        requestHandler.getResponse(postRequest);

        HttpResponse firstResponse = requestHandler.getResponse(getRequest);
        assertEquals("data=fatcat", firstResponse.getContent());


        requestHandler.getResponse(deleteRequest);

        HttpResponse secondResponse = requestHandler.getResponse(getRequest);
        assertEquals("", secondResponse.getContent());
    }
}