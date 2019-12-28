package com.markscottwright.adventofcode2019;

import java.util.Iterator;

public class EmptyIntcodeComputerInput implements Iterator<Long> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Long next() {
        return null;
    }

}