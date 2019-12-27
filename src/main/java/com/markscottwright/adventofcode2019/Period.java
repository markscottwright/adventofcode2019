package com.markscottwright.adventofcode2019;

import java.util.Optional;

public class Period {
    static public Optional<Integer> periodOf(int[] ints) {
        for (int period = 1; period <= ints.length / 2; ++period) {
            if (ints[0] == ints[period]) {
                boolean mismatch = false;
                for (int j = 1; j < period && !mismatch; ++j) {
                    if (ints[j] != ints[j + period])
                        mismatch = true;
                }
                if (!mismatch)
                    return Optional.of(period);
            }
        }
        return Optional.empty();
    }
}
