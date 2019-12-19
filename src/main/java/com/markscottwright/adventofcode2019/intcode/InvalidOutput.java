package com.markscottwright.adventofcode2019.intcode;

public class InvalidOutput implements IntcodeOutput {

    @Override
    public void put(long aVal) {
        throw new RuntimeException("Invalid put");
    }

}
