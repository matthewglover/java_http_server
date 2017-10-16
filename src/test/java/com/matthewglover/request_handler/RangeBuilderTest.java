package com.matthewglover.request_handler;

import org.junit.Test;

import static org.junit.Assert.*;

public class RangeBuilderTest {
    @Test
    public void createsASimpleRange() {
       RangeBuilder rangeBuilder = new RangeBuilder("bytes=0-4", 76);
       assertEquals(0, rangeBuilder.getStart());
       assertEquals(4, rangeBuilder.getEnd());
    }

    @Test
    public void createsAFromEndRange() {
        RangeBuilder rangeBuilder = new RangeBuilder("bytes=-6", 76);
        assertEquals(71, rangeBuilder.getStart());
        assertEquals(76, rangeBuilder.getEnd());
    }

    @Test
    public void createsAFromStartToEndRange() {
        RangeBuilder rangeBuilder = new RangeBuilder("bytes=4-", 76);
        assertEquals(4, rangeBuilder.getStart());
        assertEquals(76, rangeBuilder.getEnd());
    }
}