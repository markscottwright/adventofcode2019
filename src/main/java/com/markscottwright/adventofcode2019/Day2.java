package com.markscottwright.adventofcode2019;

import java.util.List;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;

public class Day2 {
    public static void main(String[] args) throws IntcodeException {

        var inputString = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,19,10,23,2,10,23,27,1,27,6,31,1,13,31,35,1,13,35,39,1,39,10,43,2,43,13,47,1,47,9,51,2,51,13,55,1,5,55,59,2,59,9,63,1,13,63,67,2,13,67,71,1,71,5,75,2,75,13,79,1,79,6,83,1,83,5,87,2,87,6,91,1,5,91,95,1,95,13,99,2,99,6,103,1,5,103,107,1,107,9,111,2,6,111,115,1,5,115,119,1,119,2,123,1,6,123,0,99,2,14,0,0";
        List<Long> input = IntcodeComputer.parse(inputString);

        IntcodeComputer computer = new IntcodeComputer(input);
        Long part1 = computer.set(1, 12).set(2, 2).run().get(0L);
        System.out.println("Day 2 Part 1: " + part1);

        // Expand our "box" of noun,verb one at a time, testing the "edges".
        // like:
        // .
        //
        // ..
        // ..
        //
        // ...
        // ...
        // ...
        long v = 0;
        long noun = 0;
        long verb = 0;
        int solution = 19690720;
        outer: while (v < 1_000_000) {
            for (noun = 0, verb = v; noun <= v; ++noun) {
                long answer = new IntcodeComputer(input).set(1, noun)
                        .set(2, verb).run().get(0L);
                if (answer == solution) {
                    break outer;
                }
            }
            for (noun = v, verb = 0; verb <= v; ++verb) {
                long answer = new IntcodeComputer(input).set(1, noun)
                        .set(2, verb).run().get(0L);
                if (answer == solution) {
                    break outer;
                }
            }
            v++;
        }
        System.out.println("Day 2 Part 2: " + (noun * 100 + verb));
    }
}
