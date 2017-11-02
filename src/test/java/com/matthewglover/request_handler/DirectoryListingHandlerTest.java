package com.matthewglover.request_handler;

import com.matthewglover.http_request.FileDouble;
import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectoryListingHandlerTest {
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
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/")
                .build();

        assertTrue(requestHandler.handles(request));
        HttpResponse response = requestHandler.getResponse(request);
        assertThat(response.getContent(), CoreMatchers.containsString("<a href=\"/file1\">file1</a>"));
        assertThat(response.getContent(), CoreMatchers.containsString("<a href=\"/file2.txt\">file2.txt</a>"));
        assertThat(response.getContent(), CoreMatchers.containsString("<a href=\"/file3.jpg\">file3.jpg</a>"));
    }
}