package com.markscottwright.adventofcode2019;

import java.util.Iterator;

public class StringChunkIterator implements Iterator<String> {
    private int pos;
    private int chunkSize;
    private String input;

    public StringChunkIterator(int chunkSize, String input) {
        this.chunkSize = chunkSize;
        this.input = input;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < input.length();
    }

    @Override
    public String next() {
        var out = input.substring(pos, pos + chunkSize);
        pos += chunkSize;
        return out;
    }
}