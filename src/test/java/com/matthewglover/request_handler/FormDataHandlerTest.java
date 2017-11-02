package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
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
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/form")
                .build();
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(request).getResponseType());
    }

    @Test
    public void handlesPutRequestToForm() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.PUT)
                .setPath("/form")
                .build();
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.OK, requestHandler.getResponse(request).getResponseType());
    }

    @Test
    public void sendsFormDataBackInResponseBody() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/form")
                .setContent("data=fatcat")
                .build();
        assertTrue(requestHandler.handles(request));
    }

    @Test
    public void handlerHasTemporalState() {
        HttpRequest postRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/form")
                .setContent("data=fatcat")
                .build();


        HttpRequest getRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/form")
                .build();

        HttpRequest deleteRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.DELETE)
                .setPath("/form")
                .build();

        requestHandler.getResponse(postRequest);

        HttpResponse firstResponse = requestHandler.getResponse(getRequest);
        assertEquals("data=fatcat", firstResponse.getContent());


        requestHandler.getResponse(deleteRequest);

        HttpResponse secondResponse = requestHandler.getResponse(getRequest);
        assertEquals("", secondResponse.getContent());
    }
}