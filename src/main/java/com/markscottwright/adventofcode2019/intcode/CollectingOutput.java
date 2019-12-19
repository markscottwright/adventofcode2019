package com.markscottwright.adventofcode2019.intcode;

import java.util.ArrayList;
import java.util.List;

public class CollectingOutput implements IntcodeOutput {

    private List<Long> vals = new ArrayList<>();

    @Override
    public void put(long aVal) {
        vals.add(aVal);
    }

    public List<Long> getValues() {
        return vals;
    }

    public Long getLastValue() {
        return vals.get(vals.size()-1);
    }

}
