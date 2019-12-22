package com.markscottwright.adventofcode2019;

import java.util.List;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.Pipe;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;

public class AmplifierSet {

    private IntcodeComputer a;
    private IntcodeComputer b;
    private IntcodeComputer c;
    private IntcodeComputer d;
    private IntcodeComputer e;
    private Pipe pipe0;

    public AmplifierSet(List<Long> instructions, Integer... phaseSettings) {
        pipe0 = new Pipe(phaseSettings[0]);

        // initial value into amplifier set
        pipe0.put(0);

        var pipe1 = new Pipe(phaseSettings[1]);
        var pipe2 = new Pipe(phaseSettings[2]);
        var pipe3 = new Pipe(phaseSettings[3]);
        var pipe4 = new Pipe(phaseSettings[4]);
        a = new IntcodeComputer(instructions, pipe0, pipe1);
        b = new IntcodeComputer(instructions, pipe1, pipe2);
        c = new IntcodeComputer(instructions, pipe2, pipe3);
        d = new IntcodeComputer(instructions, pipe3, pipe4);
        e = new IntcodeComputer(instructions, pipe4, pipe0);
        a.setPauseWhenInputEmpty(true);
        b.setPauseWhenInputEmpty(true);
        c.setPauseWhenInputEmpty(true);
        d.setPauseWhenInputEmpty(true);
        e.setPauseWhenInputEmpty(true);
    }

    long run() throws IntcodeException {
        while (!a.isHalted() && !b.isHalted() && !c.isHalted() && !d.isHalted()
                && !e.isHalted()) {
            a.runUntilInputEmpty();
            b.runUntilInputEmpty();
            c.runUntilInputEmpty();
            d.runUntilInputEmpty();
            e.runUntilInputEmpty();
        }
        return pipe0.firstValueInPipe();
    }
}
