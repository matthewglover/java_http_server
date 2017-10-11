package com.matthewglover.http_response;

import java.util.Map;

public class ResponseComparer {

    private final HttpResponse responseA;
    private final HttpResponse responseB;

    public ResponseComparer(HttpResponse responseA, HttpResponse responseB) {
        this.responseA = responseA;
        this.responseB = responseB;
    }

    public boolean areSame() {
        return isSameResponseType() &&
                isSameContent() &&
                isEqualHeaderCount() &&
                areSameHeaders();
    }

    private boolean isSameResponseType() {
        return responseA.getResponseType() == responseB.getResponseType();
    }

    private boolean isSameContent() {
        return responseA.getContent().equals(responseB.getContent());
    }

    private boolean isEqualHeaderCount() {
        return responseA.getHeaders().size() == responseB.getHeaders().size();
    }

    private boolean areSameHeaders() {
        Map<String, String> responseAHeaders = responseA.getHeaders();

        for (Map.Entry<String, String> header : responseAHeaders.entrySet()) {
            if (!isSameHeader(header)) return false;
        }

        return true;
    }

    private boolean isSameHeader(Map.Entry<String, String> responseAHeader) {
        Map<String, String> responseBHeaders = responseB.getHeaders();
        return responseBHeaders.get(responseAHeader.getKey()).equals(responseAHeader.getValue());
    }
}
