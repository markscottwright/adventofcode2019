package com.markscottwright.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputer {
    static public class IntcodeException extends Exception {
        public IntcodeException(String string) {
            super(string);
        }
    }

    List<Long> instructions = new ArrayList<>();
    private int instructionPointer;

    public IntcodeComputer(List<Long> list) {
        this.instructions.addAll(list);
        this.instructionPointer = 0;
    }

    IntcodeComputer set(int pos, long newVal) {
        instructions.set(pos, newVal);
        return this;
    }

    public List<Long> run() throws IntcodeException {
        
        while (instructionPointer < instructions.size()) {
            Long instruction = instructions.get(instructionPointer);
            if (instruction == 1) {
                Long aAddr = instructions.get(instructionPointer + 1);
                Long bAddr = instructions.get(instructionPointer + 2);
                Long outAddr = instructions.get(instructionPointer + 3);
                instructions.set(outAddr.intValue(),
                        instructions.get(aAddr.intValue())
                                + instructions.get(bAddr.intValue()));
                instructionPointer += 4;
            } else if (instruction == 2) {
                Long aAddr = instructions.get(instructionPointer + 1);
                Long bAddr = instructions.get(instructionPointer + 2);
                Long outAddr = instructions.get(instructionPointer + 3);
                instructions.set(outAddr.intValue(),
                        instructions.get(aAddr.intValue())
                                * instructions.get(bAddr.intValue()));
                instructionPointer += 4;
            } else if (instruction == 99)
                break;
            else
                throw new IntcodeException("unknown instruction "
                        + instructions.get(instructionPointer) + " at "
                        + instructionPointer);
        }
        return instructions;
    }

    public static List<Long> parse(String input) {
        return Arrays.stream(input.split(",")).map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public List<Long> getInstructions() {
        return instructions;
    }
}
