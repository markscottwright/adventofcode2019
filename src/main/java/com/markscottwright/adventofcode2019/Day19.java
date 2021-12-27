package com.markscottwright.adventofcode2019;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;
import com.markscottwright.adventofcode2019.intcode.IntcodeOutput;

public class Day19 {

    static class TractorBeamIndicator implements Iterator<Long>, IntcodeOutput {

        private IntcodeComputer intcode;
        private boolean beamFound;
        private Optional<Long> maybeX = Optional.empty();
        private Optional<Long> maybeY = Optional.empty();
        private List<Long> instructions;

        public TractorBeamIndicator() {
            instructions = IntcodeComputer.parseResource("/day19.txt");
        }

        @Override
        public void put(long aVal) {
            this.beamFound = aVal != 0;
        }

        @Override
        public boolean hasNext() {
            return maybeX.isPresent() || maybeY.isPresent();
        }

        @Override
        public Long next() {
            Long out;
            if (maybeX.isPresent()) {
                out = maybeX.get();
                maybeX = Optional.empty();
            } else {
                out = maybeY.get();
                maybeY = Optional.empty();
            }
            return out;
        }

        boolean beamAt(Long x, Long y) {
            this.intcode = new IntcodeComputer(instructions, this, this);
            maybeX = Optional.of(x);
            maybeY = Optional.of(y);
            beamFound = false;
            try {
                intcode.run();
            } catch (IntcodeException e) {
                throw new RuntimeException(e);
            }
            return beamFound;
        }
    };

    public static void main(String[] args) {
        int pointsAffected = 0;
        TractorBeamIndicator indicator = new TractorBeamIndicator();
        for (long y = 0; y < 50; y++) {
            for (long x = 0; x < 50; x++) {
                if (indicator.beamAt(x, y))
                    pointsAffected += 1;
            }
        }

        System.out.println("Day 19 part 1: " + pointsAffected);
        System.out.println("Day 19 part 2: " + findSquare());
    }

    static long findSquare() {
        var indicator = new TractorBeamIndicator();

        long y = 100;
        long leftmost = 0;

        while (true) {
            while (!indicator.beamAt(leftmost, y))
                leftmost++;

            long boxLeft = leftmost;
            // possible row
            while (indicator.beamAt(boxLeft + 99, y)) {
                // try all 100x100 boxes starting with x=leftmost
                if (indicator.beamAt(boxLeft, y + 99) && indicator.beamAt(boxLeft + 99, y + 99)) {
                    return boxLeft * 10000 + y;
                }

                boxLeft++;
            }

            y++;
        }
    }
}
