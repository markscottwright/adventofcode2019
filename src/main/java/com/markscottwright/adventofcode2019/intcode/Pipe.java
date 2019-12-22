package com.markscottwright.adventofcode2019.intcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;

/**
 * This class feeds the output from a previous run to the input of the next
 * run, with an initial value.
 */
public class Pipe implements IntcodeOutput, Iterator<Long> {
    private LinkedList<Long> values = new LinkedList<>();

    public Pipe(long initialValue) {
        values.add(initialValue);
    }

    public Pipe() {
    }

    @Override
    public boolean hasNext() {
        return !values.isEmpty();
    }

    @Override
    public Long next() {
        return values.pop();
    }

    @Override
    public void put(long aVal) {
        values.add(aVal);
    }

    public long firstValueInPipe() {
        return values.pop();
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public List<Long> asList() {
        return values;
    }

}