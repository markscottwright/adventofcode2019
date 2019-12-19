package com.markscottwright.adventofcode2019;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.markscottwright.adventofcode2019.intcode.CollectingOutput;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;
import com.markscottwright.adventofcode2019.intcode.IntcodeOutput;

public class AmplifierSet {

    private IntcodeComputer a;
    private IntcodeComputer b;
    private IntcodeComputer c;
    private IntcodeComputer d;
    private IntcodeComputer e;
    private CollectingOutput eOutput;

    /**
     * This class feeds the output from a previous run to the input of the next
     * run
     */
    static class PhaseAndPreviousOutput
            implements IntcodeOutput, Iterator<Long> {
        private long phase;
        private Long previousOutput = null;
        int pos = 0;

        public PhaseAndPreviousOutput(long phase) {
            this.phase = phase;
        }

        @Override
        public boolean hasNext() {
            return pos < 2;
        }

        @Override
        public Long next() {
            pos++;
            if (pos == 1)
                return phase;
            else
                return previousOutput;
        }

        @Override
        public void put(long aVal) {
            previousOutput = aVal;
        }

    }

    public AmplifierSet(List<Long> instructions, Integer... phaseSettings) {
        var aOutput = new PhaseAndPreviousOutput(phaseSettings[1]);
        var bOutput = new PhaseAndPreviousOutput(phaseSettings[2]);
        var cOutput = new PhaseAndPreviousOutput(phaseSettings[3]);
        var dOutput = new PhaseAndPreviousOutput(phaseSettings[4]);
        eOutput = new CollectingOutput();
        a = new IntcodeComputer(instructions,
                List.of((long) phaseSettings[0], 0L).iterator(), aOutput);
        b = new IntcodeComputer(instructions, aOutput, bOutput);
        c = new IntcodeComputer(instructions, bOutput, cOutput);
        d = new IntcodeComputer(instructions, cOutput, dOutput);
        e = new IntcodeComputer(instructions, dOutput, eOutput);
    }

    long run() throws IntcodeException {
        a.run();
        b.run();
        c.run();
        d.run();
        e.run();
        return eOutput.getLastValue();
    }
}
