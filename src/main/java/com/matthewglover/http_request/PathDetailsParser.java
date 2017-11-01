package com.matthewglover.http_request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class PathDetailsParser {

    public static PathDetails parse(String rawPath) {
        String[] pathElements = parsePathElements(rawPath);
        String basePath = parseBasePath(pathElements);
        String rawQueryString = parseRawQueryString(pathElements);
        String queryString = parseQueryString(rawQueryString);
        HashMap<String, String> queryParams = parseQueryParams(rawQueryString);

        return new PathDetails(basePath, queryString, queryParams);
    }

    private static String[] parsePathElements(String rawPath) {
        return rawPath.split("\\?");
    }

    private static String parseBasePath(String[] pathElements) {
        return pathElements[0].trim();
    }

    private static String parseRawQueryString(String[] pathElements) {
        return pathElements.length > 1 ? pathElements[1] : "";
    }

    private static String parseQueryString(String rawQueryString) {
        return decodeString(rawQueryString);
    }

    private static String decodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static HashMap<String, String> parseQueryParams(String rawQueryString) {
        HashMap<String, String> queryParams = new HashMap<>();

        if (rawQueryString.length() > 0) {
            for (String keyValuePair : rawQueryString.split("&")) {
                addQueryParam(queryParams, keyValuePair.split("="));
            }
        }

        return queryParams;
    }

    private static void addQueryParam(HashMap<String, String> queryParams, String[] keyValue) {
        queryParams.put(keyValue[0], decodeString(keyValue[1]));
    }
}
