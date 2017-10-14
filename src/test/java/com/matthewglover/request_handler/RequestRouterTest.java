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
    private final HttpRequest simpleGet = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private RequestRouter router;

    @Before
    public void setUp() throws Exception {
        String rootDirectoryPath = "/path/to/public";
        router = new DefaultRouterBuilder().build(rootDirectoryPath, fileAccessorDouble);
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
        String validCredentials = Base64.getEncoder().withoutPadding().encodeToString(("admin:hunter2").getBytes());
        simpleGet.setHeader("Authorization", "Basic " + validCredentials);
        HttpResponse actualResponse = router.handleRequest(simpleGet);
        assertEquals(simpleGet.requestLineToString(), actualResponse.getContent());
    }

    @Test
    public void postRequestToFormReturns200() {
        HttpRequest postRequest = HttpTestRequestFactory.get(HttpRequestMethod.POST);
        postRequest.setPath("/form");
        HttpResponse actualResponse = router.handleRequest(postRequest);
        assertEquals(HttpResponseType.OK, actualResponse.getResponseType());
    }

    @Test
    public void putRequestToFormReturns200() {
        HttpRequest putRequest = HttpTestRequestFactory.get(HttpRequestMethod.PUT);
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

    @Test
    public void getRequestToFileReturns200WithFile() throws Exception {
        String testData = "Test file contents";
        fileAccessorDouble.setFileInputStreamData(testData);
        fileAccessorDouble.getFile().setIsFile(true);

        simpleGet.setPath("/file1");

        SocketDouble socketDouble = new SocketDouble();
        HttpResponse actualResponse = router.handleRequest(simpleGet);

        actualResponse.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(testData));
    }

    @Test
    public void getRequestToRootReturns200WithDirectoryListing() throws Exception {
        String[] fileNames = new String[]{ "file1", "file2.txt", "file3.jpg" };
        FileDouble fileDouble = fileAccessorDouble.getFile();
        fileDouble.setIsDirectory(true);
        fileDouble.setFileList(fileNames);

        simpleGet.setPath("/");

        HttpResponse actualResponse = router.handleRequest(simpleGet);

        assertThat(actualResponse.getContent(), CoreMatchers.containsString("<a href=\"/file1\">file1</a>"));
        assertThat(actualResponse.getContent(), CoreMatchers.containsString("<a href=\"/file2.txt\">file2.txt</a>"));
        assertThat(actualResponse.getContent(), CoreMatchers.containsString("<a href=\"/file3.jpg\">file3.jpg</a>"));
    }
}