package com.matthewglover.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ArgumentParserTest {
    @Test
    public void portIsAnIntegerAndPathIsAString() {
        String[] args = {"-p", "5000", "-d", "path/to/files/"};
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);
        assertEquals(5000, argumentParser.getPort());
        assertEquals("path/to/files/", argumentParser.getFilePath());
    }

    @Test
    public void argumentsCanBeInAnyOrder() {
        String[] args = {"-d", "path/to/files/", "-p", "5000"};
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);
        assertEquals("path/to/files/", argumentParser.getFilePath());
        assertEquals(5000, argumentParser.getPort());
    }

    @Test
    public void reportsErrorWhenInsufficientArguments() {
        String[] args ={};
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);
        List<String> errors = Arrays.asList("Insufficient arguments. Expected: -p <port number> -d <path to files>");
        assertTrue(argumentParser.hasErrors());
        assertEquals(errors, argumentParser.getErrors());
    }

    @Test
    public void reportsInvalidPortIfNotInteger() {
        String[] args = {"-d", "path/to/files/", "-p", "invalid_port5000"};
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);
        List<String> errors = Arrays.asList("Invalid port number. <port number> must be an integer");
        assertTrue(argumentParser.hasErrors());
        assertEquals(errors, argumentParser.getErrors());
    }
}