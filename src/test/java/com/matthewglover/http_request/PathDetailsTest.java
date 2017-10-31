package com.matthewglover.http_request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathDetailsTest {

    private final String basicPath = "/parameters";
    private final String complexQueryString = "variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2" +
            "C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
    private final String complexPath = "/parameters?" + complexQueryString;

    private final PathDetails basicPathDetails = new PathDetails(basicPath);
    private final PathDetails complexPathDetails = new PathDetails(complexPath);

    @Before
    public void setUp() throws Exception {
        basicPathDetails.parse();
        complexPathDetails.parse();
    }

    @Test
    public void extractsBasePathFromRaw() {
        assertEquals("/parameters", basicPathDetails.getBasePath());
        assertEquals("/parameters", complexPathDetails.getBasePath());
    }

    @Test
    public void queryStringIsEmptyForBasicPath() {
        assertEquals("", basicPathDetails.getQueryString());
    }

    @Test
    public void queryStringIsCorrectForComplexPath() {
        assertEquals(
                "variable_1=Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?&variable_2=stuff",
                complexPathDetails.getQueryString());
    }

    @Test
    public void extractsAndDecodesParams() {
        assertEquals(
                "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?",
                complexPathDetails.getQueryParam("variable_1"));
        assertEquals("stuff", complexPathDetails.getQueryParam("variable_2"));
    }

    @Test
    public void nonExistentQueryParamReturnsNull() {
        assertEquals(null, basicPathDetails.getQueryParam("variable_1"));
    }
}