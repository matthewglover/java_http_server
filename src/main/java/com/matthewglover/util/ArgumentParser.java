package com.matthewglover.util;

import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {
    private static String InsufficientArguments = "Insufficient arguments. Expected: -p <port number> -d <path to files>";
    private static String InvalidPortNumber = "Invalid port number. <port number> must be an integer";
    private static final String PortNumber = "-p";
    private static final String DirectoryPath = "-d";

    private int port;
    private String filePath;
    protected List<String> errors = new ArrayList<>();

    public ArgumentParser(String[] args) {
        if (args.length < 4) {
            errors.add(InsufficientArguments);
        } else {
            parseArgPair(args[0], args[1]);
            parseArgPair(args[2], args[3]);
        }
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getPort() {
        return port;
    }

    public String getFilePath() {
        return filePath;
    }

    private void parseArgPair(String argName, String argValue) {
        if (argName.equals(PortNumber)) {
            parsePortNumber(argValue);
        } else if (argName.equals(DirectoryPath)) {
            parseDirectoryPath(argValue);
        }
    }

    private void parsePortNumber(String argValue) {
        if (argValue.matches("^\\d+$")) {
            port = Integer.parseInt(argValue);
        } else {
            errors.add(InvalidPortNumber);
        }
    }

    private void parseDirectoryPath(String argValue) {
        filePath = argValue;
    }
}
