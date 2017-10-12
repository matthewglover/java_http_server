package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseFactory;
import com.matthewglover.http_response.HttpResponseTemplate;
import com.matthewglover.http_response.ResponseComparer;
import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class BasicAuthHandlerTest {

    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpRequest testRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
    private final BasicAuthHandler basicAuthHandler = new BasicAuthHandler();

    private final String username = "admin";
    private final String password = "hunter2";
    private final String validCredentials = Base64.getEncoder().withoutPadding().encodeToString((username + ":" + password).getBytes());
    private final String invalidCredentials = Base64.getEncoder().withoutPadding().encodeToString("username:password".getBytes());

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
        basicAuthHandler.addUsernameAndPassword(username, password);
    }

    @Test
    public void rejectsWith404RequestWithoutAuthentication() throws UnsupportedEncodingException {
        HttpResponse actualResponse = basicAuthHandler.handleRequest(testRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);

        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void resolvesWith200RequestsWithValidAuthentication() throws UnsupportedEncodingException {
        testRequest.setHeader("Authorization", "Basic " + validCredentials);
        HttpResponse actualResponse = basicAuthHandler.handleRequest(testRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        expectedResponse.setContent(testRequest.toString());
        expectedResponse.setContentLengthHeader();

        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void rejectsWith404RequestWithMalformedAuthentication() throws UnsupportedEncodingException {
        testRequest.setHeader("Authorization", "Blah Blah Blah");
        HttpResponse actualResponse = basicAuthHandler.handleRequest(testRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);

        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void rejectsWith404RequestWithInvalidCredentials() throws UnsupportedEncodingException {
        testRequest.setHeader("Authorization", "Basic " + invalidCredentials);
        HttpResponse actualResponse = basicAuthHandler.handleRequest(testRequest);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);

        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }
}