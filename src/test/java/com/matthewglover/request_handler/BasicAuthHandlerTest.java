package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

public class BasicAuthHandlerTest {

    private final HttpRequest simpleGet = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final String username = "admin";
    private final String password = "hunter2";
    private final String validCredentials = Base64.getEncoder().withoutPadding().encodeToString((username + ":" + password).getBytes());
    private final String invalidCredentials = Base64.getEncoder().withoutPadding().encodeToString("username:password".getBytes());
    private final BasicAuthHandler requestHandler = new BasicAuthHandler();

    @Test
    public void getToLogsReturns200ResponseIfAuthorized() {
        simpleGet.setPath("/logs");
        simpleGet.setHeader("Authorization", "Basic " + validCredentials);
        HttpResponse response = requestHandler.getResponse(simpleGet);
        assertTrue(requestHandler.handles(simpleGet));
        assertEquals(HttpResponseType.OK, response.getResponseType());
    }

    @Test
    public void getToLogsReturns401ResponseIfNotAuthorised() {
        simpleGet.setPath("/logs");
        HttpResponse response = requestHandler.getResponse(simpleGet);
        assertTrue(requestHandler.handles(simpleGet));
        assertEquals(HttpResponseType.UNAUTHORIZED_ACCESS, response.getResponseType());
    }

    @Test
    public void getToLogsReturns401ResponseIfInvalidCredentials() {
        simpleGet.setPath("/logs");
        simpleGet.setHeader("Authorization", "Basic " + invalidCredentials);
        HttpResponse response = requestHandler.getResponse(simpleGet);
        assertTrue(requestHandler.handles(simpleGet));
        assertEquals(HttpResponseType.UNAUTHORIZED_ACCESS, response.getResponseType());
    }

    @Test
    public void getToLogAddsToLogOutputAndReturns200() {
        HttpRequest logRequest = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        logRequest.setPath("/log");
        HttpResponse response = requestHandler.getResponse(logRequest);
        assertTrue(requestHandler.handles(logRequest));
        assertEquals(HttpResponseType.OK, response.getResponseType());

        HttpRequest logAccessRequest = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        logAccessRequest.setPath("/logs");
        logAccessRequest.setHeader("Authorization", "Basic " + validCredentials);
        HttpResponse logsResponse = requestHandler.getResponse(logAccessRequest);
        assertThat(logsResponse.getContent(), CoreMatchers.containsString(logRequest.requestLineToString()));
    }
}