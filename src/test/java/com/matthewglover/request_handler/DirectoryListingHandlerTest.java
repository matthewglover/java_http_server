package com.matthewglover.request_handler;

import com.matthewglover.http_request.FileDouble;
import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectoryListingHandlerTest {
    private final HttpRequest simpleGet = HttpTestRequestFactory.get(HttpRequestMethod.GET);
    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();
    private final RequestHandler requestHandler = new DirectoryListingHandler("path/to/public", fileAccessorDouble);
    private final String[] fileNames = new String[]{ "file1", "file2.txt", "file3.jpg" };

    @Before
    public void setUp() throws Exception {
        FileDouble fileDouble = fileAccessorDouble.getFile();
        fileDouble.setIsDirectory(true);
        fileDouble.setFileList(fileNames);
    }

    @Test
    public void returnsFileForValidFilePath() throws Exception {
        simpleGet.setPath("/");
        assertTrue(requestHandler.handles(simpleGet));
        HttpResponse response = requestHandler.getResponse(simpleGet);
        assertThat(response.getContent(), CoreMatchers.containsString("<a href=\"/file1\">file1</a>"));
        assertThat(response.getContent(), CoreMatchers.containsString("<a href=\"/file2.txt\">file2.txt</a>"));
        assertThat(response.getContent(), CoreMatchers.containsString("<a href=\"/file3.jpg\">file3.jpg</a>"));
    }
}