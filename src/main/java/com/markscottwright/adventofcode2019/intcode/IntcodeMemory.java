package com.markscottwright.adventofcode2019.intcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntcodeMemory {

    private HashMap<Long, Long> memory;
    long size = 0;

    public IntcodeMemory() {
        memory = new HashMap<Long, Long>();
    }

    public void put(int i, Long value) {
        memory.put((long) i, value);
        size = Math.max(i, size);
    }

    public long get(int i) {
        return memory.getOrDefault((long) i, 0L);
    }

    public long get(long i) {
        return memory.getOrDefault(i, 0L);
    }

    public List<Long> toList() {
        long lastIndex = memory.keySet().stream().max(Long::compareTo).get();
        if (lastIndex > 10000)
            throw new RuntimeException("Cant make array of:" + lastIndex);
        ArrayList<Long> out = new ArrayList<>((int) lastIndex);
        for (int i = 0; i <= lastIndex; ++i)
            out.add(memory.getOrDefault(i, 0L));
        return out;
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    public void put(Long index, long value) {
        memory.put(index, value);
    }

    public long size() {
        return size;
    }
}
