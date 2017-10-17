package com.matthewglover.request_handler;

public class RangeBuilder {
    private final int rangeMax;
    private int start;
    private int end;

    public RangeBuilder(String rangeString, int rangeMax) {
        this.rangeMax = rangeMax;
        buildRangeData(rangeString);
    }

    private void buildRangeData(String rangeString) {
        String[] keyValuePair = getKeyValuePair(rangeString);
        String[] bounds = getBounds(keyValuePair[1]);
        buildStart(bounds);
        buildEnd(bounds);
    }

    private String[] getKeyValuePair(String rangeString) {
        return rangeString.split("=");
    }

    private String[] getBounds(String s) {
        return s.split("-");
    }

    private void buildStart(String[] bounds) {
        if (bounds[0].length() == 0) {
            start = rangeMax - Integer.parseInt(bounds[1]) + 1;
        } else {
            start = Integer.parseInt(bounds[0]);
        }
    }

    private void buildEnd(String[] bounds) {
        if (bounds.length == 1 || bounds[0].length() == 0) {
            end = rangeMax;
        } else {
            end = Integer.parseInt(bounds[1]);
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
