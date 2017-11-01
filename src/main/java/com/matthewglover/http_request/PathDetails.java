package com.matthewglover.http_request;

import java.util.HashMap;

public class PathDetails {
    private final String basePath;
    private final String queryString;
    private final HashMap<String, String> queryParams;

    public PathDetails(String basePath, String queryString, HashMap<String, String> queryParams) {
        this.basePath = basePath;
        this.queryString = queryString;
        this.queryParams = queryParams;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getQueryParam(String param) {
        return queryParams.get(param);
    }
}
