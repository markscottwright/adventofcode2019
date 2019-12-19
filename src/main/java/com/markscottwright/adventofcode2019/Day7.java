package com.markscottwright.adventofcode2019;

import java.util.List;

import org.apache.commons.collections4.iterators.PermutationIterator;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;

public class Day7 {
    public static final String INPUT = "3,8,1001,8,10,8,105,1,0,0,21,38,63,72,85,110,191,272,353,434,99999,3,9,102,4,9,9,101,2,9,9,102,3,9,9,4,9,99,3,9,1001,9,4,9,102,2,9,9,1001,9,5,9,1002,9,5,9,101,3,9,9,4,9,99,3,9,1001,9,2,9,4,9,99,3,9,1001,9,3,9,102,2,9,9,4,9,99,3,9,101,2,9,9,102,2,9,9,1001,9,2,9,1002,9,4,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,99";

    public static void main(String[] args) throws IntcodeException {
        var instructions = IntcodeComputer.parse(INPUT);
        PermutationIterator<Integer> phaseCodes = new PermutationIterator<Integer>(
                List.of(0, 1, 2, 3, 4));
        long maxOutput = -1;
        while (phaseCodes.hasNext()) {
            var phaseCode = phaseCodes.next();
            long output = new AmplifierSet(instructions, phaseCode.get(0), phaseCode.get(1),
                    phaseCode.get(2), phaseCode.get(3), phaseCode.get(4)).run();
            maxOutput = Math.max(output, maxOutput);
        }
        System.out.println("Part 1: " + maxOutput);
    }
}
