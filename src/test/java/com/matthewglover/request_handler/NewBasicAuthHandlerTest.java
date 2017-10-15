package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

public class NewBasicAuthHandlerTest {

    private final HttpRequest simpleGet = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final String username = "admin";
    private final String password = "hunter2";
    private final String validCredentials = Base64.getEncoder().withoutPadding().encodeToString((username + ":" + password).getBytes());
    private final String invalidCredentials = Base64.getEncoder().withoutPadding().encodeToString("username:password".getBytes());
    private final NewBasicAuthHandler requestHandler = new NewBasicAuthHandler();
    private final HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);

    @Before
    public void setUp() throws Exception {
        requestHandler.addHandledMethodType(HttpRequestMethod.GET);
        requestHandler.addAuthCredentials(username, password);
        requestHandler.addHandledPath("/logs");
    }

    @Test
    public void doesntHandleNonProtectedResources() {
        simpleGet.setPath("/not_protected");
        assertFalse(requestHandler.handles(simpleGet));
    }

    @Test
    public void doesntHandleProtectedResourceWithValidCredentials() {
        simpleGet.setPath("/logs");
        simpleGet.setHeader("Authorization", "Basic " + validCredentials);
        assertFalse(requestHandler.handles(simpleGet));
    }

    @Test
    public void handlesProtectedResourceWithNoCredentials() {
        simpleGet.setPath("/logs");
        assertTrue(requestHandler.handles(simpleGet));
        assertTrue(new ResponseComparer(expectedResponse, requestHandler.getResponse(simpleGet)).areSame());
    }

    @Test
    public void handlesProtectedResourceWithInvalidCredentials() {
        simpleGet.setPath("/logs");
        simpleGet.setHeader("Authorization", "Basic " + invalidCredentials);
        assertTrue(requestHandler.handles(simpleGet));
        assertTrue(new ResponseComparer(expectedResponse, requestHandler.getResponse(simpleGet)).areSame());
    }
}