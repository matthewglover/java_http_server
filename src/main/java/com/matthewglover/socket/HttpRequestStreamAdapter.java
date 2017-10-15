package com.matthewglover.socket;

import com.matthewglover.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HttpRequestStreamAdapter {

    private final ArrayList<String> rawRequest = new ArrayList<>();
    private final BufferedReader bufferedReader;

    public HttpRequestStreamAdapter(InputStream inputStream, LoggerFactory loggerFactory) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public ArrayList<String> getRequest() throws IOException {
        processRequest();
//        bufferedReader.close();
        return rawRequest;
    }

    private void processRequest() throws IOException {
        String line = bufferedReader.readLine();
        if (line.length() > 0) {
            rawRequest.add(line);
            processRequest();
        }
    }
}
