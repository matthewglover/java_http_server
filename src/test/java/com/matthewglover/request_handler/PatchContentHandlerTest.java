package com.matthewglover.request_handler;

import com.matthewglover.http_request.HttpRequest;
import com.matthewglover.http_request.HttpRequestMethod;
import com.matthewglover.http_request.HttpTestRequestFactory;
import com.matthewglover.http_response.HttpResponse;
import com.matthewglover.http_response.HttpResponseType;
import com.matthewglover.socket.SocketDouble;
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

        HttpRequest request = HttpTestRequestFactory.get(HttpRequestMethod.GET);
        request.setPath("/patch-content.txt");

        RequestHandler handler = new PatchContentHandler("root/to/public", fileAccessorDouble);
        HttpResponse response = handler.getResponse(request);

        assertEquals(HttpResponseType.OK, response.getResponseType());
        SocketDouble socketDouble = new SocketDouble();
        response.sendResponseOverSocket(socketDouble.getOutputStream());
        assertThat(socketDouble.getOutput(), CoreMatchers.containsString(patchedContent));
    }

    @Test
    public void patchRequestToPatchedContentReturns204() throws Exception {
        HttpRequest patchRequest = HttpTestRequestFactory.get(HttpRequestMethod.PATCH);
        patchRequest.setPath("/patch-content.txt");
        patchRequest.setContent("patched content");

        RequestHandler handler = new PatchContentHandler("root/to/public", fileAccessorDouble);
        HttpResponse response = handler.getResponse(patchRequest);

        assertEquals(HttpResponseType.NO_CONTENT, response.getResponseType());
    }

    
}