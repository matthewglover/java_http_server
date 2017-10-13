package com.matthewglover.request_handler;

import com.matthewglover.DefaultRouter;
import com.matthewglover.http_request.FileDouble;
import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestFactory;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_response.*;
import com.matthewglover.util.LoggerFactoryDouble;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

public class RequestRouterTest {

    private final LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
    private final HttpRequest simpleGet = HttpRequestFactory.get(HttpRequestMethod.GET, loggerFactoryDouble);
    private final String username = "admin";
    private final String password = "hunter2";
    private final String validCredentials = Base64.getEncoder().withoutPadding().encodeToString((username + ":" + password).getBytes());
    private RequestRouter router;

    @Before
    public void setUp() throws Exception {
        router = new DefaultRouter().build(new FileDouble(""));
    }

    @Test
    public void getToUnhandledPathReturns404() {
        simpleGet.setPath("/unhandled_path");
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void getToCoffeeReturns418() {
        simpleGet.setPath("/coffee");
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void getToTeaReturns200() {
        simpleGet.setPath("/tea");
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void unauthorisedGetToLogsReturns401() {
        simpleGet.setPath("/logs");
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.UNAUTHORIZED_ACCESS);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void authorisedGetToLogsReturns200WithRequestLineAsBody() {
        simpleGet.setPath("/logs");
        simpleGet.setHeader("Authorization", "Basic " + validCredentials);
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        assertEquals(simpleGet.requestLineToString(), actualResponse.getContent());
    }

    @Test
    public void postRequestToFormReturns200() {
        HttpRequest postRequest = HttpRequestFactory.get(HttpRequestMethod.POST, loggerFactoryDouble);
        postRequest.setPath("/form");
        HttpResponse actualResponse = router.handleRequest(postRequest);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void putRequestToFormReturns200() {
        HttpRequest putRequest = HttpRequestFactory.get(HttpRequestMethod.PUT, loggerFactoryDouble);
        putRequest.setPath("/form");
        HttpResponse actualResponse = router.handleRequest(putRequest);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void getRequestWithCookieUrlReturns200WithEat() {
        simpleGet.setPath("/cookie?type=chocolate");
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
        assertEquals("Eat", actualResponse.getContent());
    }
}