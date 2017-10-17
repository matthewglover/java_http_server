package com.matthewglover.util;

public class ArgumentParserDouble extends ArgumentParser {
    public ArgumentParserDouble(String[] args) {
        super(args);
    }

    public void parse() {
    }

    public void addError(String error) {
        errors.add(error);
    }
}
