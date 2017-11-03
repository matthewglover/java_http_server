package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestBuilder;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.http_server.SocketDouble;
import com.matthewglover.util.FileAccessorDouble;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatchContentHandlerTest {

    private final FileAccessorDouble fileAccessorDouble = new FileAccessorDouble();

    @Test
    public void getRequestReturns200() throws Exception {
        String patchedContent = "default content";
        fileAccessorDouble.setFileInputStreamData(patchedContent);
        fileAccessorDouble.getFile().setIsFile(true);

        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.GET)
                .setPath("/patch_content.txt")
                .build();

        RequestHandler handler = new PatchContentHandler("root/to/public", fileAccessorDouble);
        HttpResponse response = handler.getResponse(request);

        assertEquals(HttpResponseType.OK, response.getResponseType());
        SocketDouble socketDouble = new SocketDouble();
        response.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(patchedContent));
    }

    @Test
    public void patchRequestToPatchedContentReturns204AndPatchesContent() throws Exception {
        HttpRequest request = new HttpTestRequestBuilder()
                .setMethod(HttpRequestMethod.PATCH)
                .setPath("/patch_content.txt")
                .setContent("patched content")
                .build();

        RequestHandler handler = new PatchContentHandler("root/to/public", fileAccessorDouble);
        HttpResponse response = handler.getResponse(request);

        assertEquals(HttpResponseType.NO_CONTENT, response.getResponseType());
        assertEquals("patched content", fileAccessorDouble.getFileOutput());
    }
}