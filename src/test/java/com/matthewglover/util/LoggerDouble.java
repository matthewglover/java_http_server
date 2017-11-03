package com.matthewglover.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LoggerDouble extends Logger {
    public static final String INFO = "INFO";
    public static final String WARNING = "WARNING";
    public static final String SEVERE = "SEVERE";

    private List<String> infoMessages = new ArrayList<>();
    private List<String> warningMessages = new ArrayList<>();
    private List<String> severeMessages = new ArrayList<>();

    public LoggerDouble() {
        super(null, null);
    }

    @Override
    public void info(String message) {
        infoMessages.add(message);
    }

    @Override
    public void warning(String message) {
        warningMessages.add(message);
    }

    @Override
    public void severe(String message) {
        severeMessages.add(message);
    }

    public String popFromMessageType(String messageType) {
        return popFromList(getMessageTypeList(messageType));
    }

    private List<String> getMessageTypeList(String messageType) {
        switch (messageType) {
            case SEVERE: return severeMessages;
            case WARNING: return warningMessages;
            case INFO: return infoMessages;
            default: throw new RuntimeException("Invalid messageType");
        }
    }

    private String popFromList(List<String> messages) {
        return messages.isEmpty() ? null : messages.remove(messages.size() - 1);
    }
}
