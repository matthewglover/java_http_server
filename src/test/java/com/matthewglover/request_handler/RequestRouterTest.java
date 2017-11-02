package com.matthewglover.request_handler;

import com.matthewglover.DefaultRouterBuilder;
import com.matthewglover.http_request.*;
import com.matthewglover.http_response.*;
import com.matthewglover.socket.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

public class RequestRouterTest {

    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private RequestRouter router;

    @Before
    public void setUp() throws Exception {
        String rootDirectoryPath = "/path/to/public";
        router = new DefaultRouterBuilder().build(rootDirectoryPath, fileAccessorDouble);
    }

    @Test
    public void getToUnhandledPathReturns404() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/unhandled_path")
                .build();
        HttpResponse actualResponse = router.handleRequest(request);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.NOT_FOUND);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void getToCoffeeReturns418() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/coffee")
                .build();
        HttpResponse actualResponse = router.handleRequest(request);
        HttpResponse expectedResponse = HttpResponseFactory.get(HttpResponseTemplate.IM_A_TEAPOT);
        assertTrue(new ResponseComparer(expectedResponse, actualResponse).areSame());
    }

    @Test
    public void getToTeaReturns200() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/tea")
                .build();
        HttpResponse actualResponse = router.handleRequest(request);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void unauthorisedGetToLogsReturns401() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/logs")
                .build();
        HttpResponse actualResponse = router.handleRequest(request);
        assertEquals(HttpResponseType.UNAUTHORIZED_ACCESS, actualResponse.getResponseType());
    }

    @Test
    public void authorisedGetToLogsReturns200WithRequestLineAsBody() {
        String validCredentials = Base64.getEncoder().withoutPadding().encodeToString(("admin:hunter2").getBytes());

        HttpRequest logRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/log")
                .build();

        HttpRequest logAccessRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/logs")
                .setHeader("Authorization", "Basic " + validCredentials)
                .build();

        HttpResponse response = router.handleRequest(logRequest);
        assertEquals(HttpResponseType.OK, response.getResponseType());

        HttpResponse logsResponse = router.handleRequest(logAccessRequest);
        assertThat(logsResponse.getContent(), CoreMatchers.containsString(logRequest.getRequestLine()));
    }

    @Test
    public void postRequestToFormReturns200() {
        HttpRequest postRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/form")
                .build();

        HttpResponse actualResponse = router.handleRequest(postRequest);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void putRequestToFormReturns200() {
        HttpRequest putRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.PUT)
                .setPath("/form")
                .build();

        HttpResponse actualResponse = router.handleRequest(putRequest);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void getRequestWithCookieUrlReturns200WithEat() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/cookie?type=chocolate")
                .build();

        HttpResponse actualResponse = router.handleRequest(request);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
        assertEquals("Eat", actualResponse.getContent());
    }

    @Test
    public void getRequestToFileReturns200WithFile() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/file1")
                .build();

        String testData = "Test file contents";
        fileAccessorDouble.setFileInputStreamData(testData);
        fileAccessorDouble.getFile().setIsFile(true);

        SocketDouble socketDouble = new SocketDouble();
        HttpResponse actualResponse = router.handleRequest(request);

        actualResponse.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void postRequestToFileReturns405() throws Exception {
        HttpRequest postRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/file1")
                .build();

        fileRequestReturns405(postRequest);
    }

    @Test
    public void putRequestToFileReturns405() throws Exception {
        HttpRequest putRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.PUT)
                .setPath("/file1")
                .build();

        fileRequestReturns405(putRequest);
    }

    @Test
    public void invalidRequestToFileReturns405() throws Exception {
        HttpRequest invalidRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.INVALID_METHOD)
                .setPath("/file1")
                .build();

        fileRequestReturns405(invalidRequest);
    }


    @Test
    public void getRequestToRootReturns200WithDirectoryListing() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/")
                .build();

        String[] fileNames = new String[]{ "file1", "file2.txt", "file3.jpg" };
        FileDouble fileDouble = fileAccessorDouble.getFile();
        fileDouble.setIsDirectory(true);
        fileDouble.setFileList(fileNames);

        HttpResponse actualResponse = router.handleRequest(request);

        assertThat(actualResponse.getContent(), CoreMatchers.containsString("<a href=\"/file1\">file1</a>"));
        assertThat(actualResponse.getContent(), CoreMatchers.containsString("<a href=\"/file2.txt\">file2.txt</a>"));
        assertThat(actualResponse.getContent(), CoreMatchers.containsString("<a href=\"/file3.jpg\">file3.jpg</a>"));
    }

    @Test
    public void getRequestToParametersReturns200WithParamsAsBodyContent() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2" +
                "C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff")
                .build();

        HttpResponse response = router.handleRequest(request);
        assertEquals(HttpResponseType.OK, response.getResponseType());
        assertThat(response.getContent(),
                CoreMatchers.containsString("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
    }

    @Test
    public void getRequestToRedirectReturns302WithRootLocation() {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/redirect")
                .build();

        HttpResponse response = router.handleRequest(request);
        assertEquals(HttpResponseType.REDIRECT, response.getResponseType());
    }

    @Test
    public void partialContentRequestWithRange0to4Returns206WithCorrectContent() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/partial_content.txt")
                .setHeader("Range", "bytes=0-4")
                .build();

        String partialContent = "This is a file that contains text to read part of in order to fulfill a 206.\n";
        fileAccessorDouble.setFileInputStreamData(partialContent);
        fileAccessorDouble.getFile().setIsFile(true);

        HttpResponse response = router.handleRequest(request);
        assertEquals(HttpResponseType.PARTIAL_CONTENT, response.getResponseType());

        assertEquals("This ", response.getContent());
    }

    @Test
    public void getRequestToFormReturnsData() {
        HttpRequest postRequest = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.POST)
                .setPath("/form")
                .setContent("data=fatcat")
                .build();

        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/form")
                .build();

        router.handleRequest(postRequest);
        HttpResponse response = router.handleRequest(request);
        assertEquals("data=fatcat", response.getContent());
    }

    @Test
    public void getRequestToPatchedContentReturns200() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/patch-content.txt")
                .build();

        String patchedContent = "Default content\n";
        fileAccessorDouble.setFileInputStreamData(patchedContent);
        fileAccessorDouble.getFile().setIsFile(true);

        HttpResponse response = router.handleRequest(request);
        assertEquals(HttpResponseType.OK, response.getResponseType());

        SocketDouble socketDouble = new SocketDouble();
        response.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(patchedContent));
    }

    @Test
    public void patchRequestToPatchedContentReturns204() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.PATCH)
                .setPath("/patch-content.txt")
                .build();

        String patchedContent = "Default content\n";
        fileAccessorDouble.setFileInputStreamData(patchedContent);
        fileAccessorDouble.getFile().setIsFile(true);

        HttpResponse response = router.handleRequest(request);
        assertEquals(HttpResponseType.NO_CONTENT, response.getResponseType());
    }

    private void fileRequestReturns405(HttpRequest request) {
        fileAccessorDouble.getFile().setIsFile(true);
        HttpResponse actualResponse = router.handleRequest(request);
        assertEquals(HttpResponseType.METHOD_NOT_ALLOWED, actualResponse.getResponseType());
    }
}