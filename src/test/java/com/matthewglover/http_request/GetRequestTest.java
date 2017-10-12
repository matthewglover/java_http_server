package com.matthewglover.http_request;

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

import static org.junit.Assert.*;

public class GetRequestTest {
    private final LoggerDouble loggerDouble = new LoggerDouble(null, null);
    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final FileDouble rootDirectory = new FileDouble("/path/to/public");

    @Before
    public void setUp() throws Exception {
        loggerFactoryDouble.setLogger(loggerDouble);
    }

    @Test
    public void requestToRootReturns200() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/");
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        assertTrue(new ResponseComparer(expectedResponse, getRequest.buildResponse(rootDirectory)).areSame());
    }

    @Test
    public void requestToFoobarReturns404() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/foobar");
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        assertTrue(new ResponseComparer(expectedResponse, getRequest.buildResponse(rootDirectory)).areSame());
    }

    @Test
    public void requestToCoffeeReturns418() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/coffee");
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(expectedResponse, getRequest.buildResponse(rootDirectory)).areSame());
    }

    @Test
    public void requestToLogsWithoutCredentialsReturns401() throws UnsupportedEncodingException {
        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/logs");
        HttpResponse expecteResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);
        assertTrue(new ResponseComparer(expecteResponse, getRequest.buildResponse(rootDirectory)).areSame());
    }

    @Test
    public void requestToLogsWithCredentialsReturns200() throws UnsupportedEncodingException {
        String username = "admin";
        String password = "hunter2";
        String validCredentials = Base64.getEncoder().withoutPadding().encodeToString((username + ":" + password).getBytes());

        HttpRequest getRequest = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
        getRequest.setPath("/logs");
        getRequest.setHeader("Authorization", "Basic " + validCredentials);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.OK);
        expectedResponse.setContent(getRequest.toString());
        expectedResponse.setContentLengthHeader();
        assertTrue(new ResponseComparer(expectedResponse, getRequest.buildResponse(rootDirectory)).areSame());
    }
}
