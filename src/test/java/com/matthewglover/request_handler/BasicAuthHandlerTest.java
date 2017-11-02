package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

public class BasicAuthHandlerTest {

    private final String username = "admin";
    private final String password = "hunter2";
    private final String validCredentials = Base64.getEncoder().withoutPadding().encodeToString((username + ":" + password).getBytes());
    private final String invalidCredentials = Base64.getEncoder().withoutPadding().encodeToString("username:password".getBytes());
    private final BasicAuthHandler requestHandler = new BasicAuthHandler();

    @Test
    public void getToLogsReturns200ResponseIfAuthorized() {
        HttpRequest request =
                new HttpTestRequestBuilder()
                        .setMethod(HttpRequestMethod.GET)
                        .setPath("/logs")
                        .setHeader("Authorization", "Basic " + validCredentials)
                        .build();
        HttpResponse response = requestHandler.getResponse(request);
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.OK, response.getResponseType());
    }

    @Test
    public void getToLogsReturns401ResponseIfNotAuthorised() {
        HttpRequest request =
                new HttpTestRequestBuilder()
                        .setMethod(HttpRequestMethod.GET)
                        .setPath("/logs")
                        .build();
        HttpResponse response = requestHandler.getResponse(request);
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.UNAUTHORIZED_ACCESS, response.getResponseType());
    }

    @Test
    public void getToLogsReturns401ResponseIfInvalidCredentials() {
        HttpRequest request =
                new HttpTestRequestBuilder()
                        .setMethod(HttpRequestMethod.GET)
                        .setPath("/logs")
                        .setHeader("Authorization", "Basic " + invalidCredentials)
                        .build();
        HttpResponse response = requestHandler.getResponse(request);
        assertTrue(requestHandler.handles(request));
        assertEquals(HttpResponseType.UNAUTHORIZED_ACCESS, response.getResponseType());
    }

    @Test
    public void getToLogAddsToLogOutputAndReturns200() {
        HttpRequest logRequest =
                new HttpTestRequestBuilder()
                        .setMethod(HttpRequestMethod.GET)
                        .setPath("/log")
                        .build();
        HttpResponse response = requestHandler.getResponse(logRequest);
        assertTrue(requestHandler.handles(logRequest));
        assertEquals(HttpResponseType.OK, response.getResponseType());

        HttpRequest logAccessRequest =
                new HttpTestRequestBuilder()
                        .setMethod(HttpRequestMethod.GET)
                        .setPath("/logs")
                        .setHeader("Authorization", "Basic " + validCredentials)
                        .build();
        HttpResponse logsResponse = requestHandler.getResponse(logAccessRequest);
        assertThat(logsResponse.getContent(), CoreMatchers.containsString(logRequest.getRequestLine()));
    }
}