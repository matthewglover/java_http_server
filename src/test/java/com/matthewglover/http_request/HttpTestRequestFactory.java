package com.matthewglover.http_request;

import com.matthewglover.util.LoggerDouble;
import com.matthewglover.util.LoggerFactoryDouble;

public class HttpTestRequestFactory {

    public static HttpRequest get(HttpRequestMethod method) {
        return HttpRequestFactory.get(method, getLoggerFactoryDouble());
    }

    public static LoggerFactoryDouble getLoggerFactoryDouble() {
        LoggerDouble loggerDouble = new LoggerDouble(null, null);
        LoggerFactoryDouble loggerFactoryDouble = new LoggerFactoryDouble();
        loggerFactoryDouble.setLogger(loggerDouble);
        return loggerFactoryDouble;
    }
}
