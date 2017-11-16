package com.matthewglover.util;

public class ArgumentParserDouble extends ArgumentParser {
    public ArgumentParserDouble() {
        super(new String[]{});
    }

    public void addError(String error) {
        errors.add(error);
    }
}
