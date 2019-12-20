package com.markscottwright.adventofcode2019.intcode;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputer {
    static public class IntcodeException extends Exception {
        public IntcodeException(String string) {
            super(string);
        }
    }

    private static final long POSITION_MODE = 0;

    public enum State {
        halted, waiting
    };

    private State state = State.waiting;
    private boolean pauseWhenInputEmpty = false;
    private List<Long> instructions = new ArrayList<>();
    private int instructionPointer;
    private Iterator<Long> input = Collections.emptyIterator();
    private IntcodeOutput output = new InvalidOutput();

    public IntcodeComputer(List<Long> list) {
        this.instructions.addAll(list);
        this.instructionPointer = 0;
    }

    public IntcodeComputer(List<Long> list, Iterator<Long> input,
            IntcodeOutput output) {
        this.instructions.addAll(list);
        this.instructionPointer = 0;
        this.input = input;
        this.output = output;
    }

    public IntcodeComputer set(int pos, long newVal) {
        instructions.set(pos, newVal);
        return this;
    }

    public List<Long> run() throws IntcodeException {
        runUntilInputEmpty();
        return instructions;
    }

    public State runUntilInputEmpty() throws IntcodeException {
        if (state == State.halted)
            return state;

        while (instructionPointer < instructions.size()) {
            long instruction = instructions.get(instructionPointer);
            long opCode = instruction % 100;
            long aMode = (instruction / 100) % 10;
            long bMode = (instruction / 1000) % 10;
            long cMode = (instruction / 10000) % 10;
            long aVal, bVal;

            // System.out.print("instruction = " + instruction);
            // System.out.print(", opCode = " + opCode);
            // System.out.print(", aMode = " + aMode);
            // System.out.print(", bMode = " + bMode);
            // System.out.println(", cMode = " + cMode);

            // add/multiply
            if (opCode == 1 || opCode == 2) {
                Long a = instructions.get(instructionPointer + 1);
                Long b = instructions.get(instructionPointer + 2);
                Long c = instructions.get(instructionPointer + 3);
                if (aMode == POSITION_MODE)
                    aVal = instructions.get(a.intValue());
                else
                    aVal = a;
                if (bMode == POSITION_MODE)
                    bVal = instructions.get(b.intValue());
                else
                    bVal = b;
                assert (cMode == POSITION_MODE);
                if (opCode == 1)
                    instructions.set(c.intValue(), aVal + bVal);
                else
                    instructions.set(c.intValue(), aVal * bVal);
                instructionPointer += 4;
            }

            // input
            else if (opCode == 3) {
                if (pauseWhenInputEmpty && !input.hasNext()) {
                    state = State.waiting;
                    return state;
                }

                Long a = instructions.get(instructionPointer + 1);
                assert (aMode == POSITION_MODE);
                instructions.set(a.intValue(), input.next());
                instructionPointer += 2;
            }

            // output
            else if (opCode == 4) {
                Long a = instructions.get(instructionPointer + 1);
                if (aMode == POSITION_MODE)
                    aVal = instructions.get(a.intValue());
                else
                    aVal = a;
                output.put(aVal);
                instructionPointer += 2;
            }

            // jump if true/false
            else if (opCode == 5 || opCode == 6) {
                Long a = instructions.get(instructionPointer + 1);
                Long b = instructions.get(instructionPointer + 2);
                if (aMode == POSITION_MODE)
                    aVal = instructions.get(a.intValue());
                else
                    aVal = a;
                if (bMode == POSITION_MODE)
                    bVal = instructions.get(b.intValue());
                else
                    bVal = b;
                if (opCode == 5 && aVal != 0 || opCode == 6 && aVal == 0)
                    instructionPointer = (int) bVal;
                else
                    instructionPointer += 3;
            }

            // less than/equals
            else if (opCode == 7 || opCode == 8) {
                Long a = instructions.get(instructionPointer + 1);
                Long b = instructions.get(instructionPointer + 2);
                Long c = instructions.get(instructionPointer + 3);
                if (aMode == POSITION_MODE)
                    aVal = instructions.get(a.intValue());
                else
                    aVal = a;
                if (bMode == POSITION_MODE)
                    bVal = instructions.get(b.intValue());
                else
                    bVal = b;
                assert (cMode == POSITION_MODE);
                if (opCode == 7 && aVal < bVal || opCode == 8 && aVal == bVal)
                    instructions.set(c.intValue(), 1L);
                else
                    instructions.set(c.intValue(), 0L);
                instructionPointer += 4;
            }

            else if (opCode == 99)
                break;
            else
                throw new IntcodeException("unknown instruction "
                        + instructions.get(instructionPointer) + " at "
                        + instructionPointer);
        }

        state = State.halted;
        return state;
    }

    public static List<Long> parse(String input) {
        return Arrays.stream(input.split(",")).map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public List<Long> getInstructions() {
        return instructions;
    }

    public void setPauseWhenInputEmpty(boolean pauseWhenInputEmpty) {
        this.pauseWhenInputEmpty = pauseWhenInputEmpty;
    }

    public boolean isHalted() {
        return state == State.halted;
    }

    public void printCurrentStateOn(PrintStream out) {
        out.println("state:" + state);
        out.println("  instructionPointer:" + instructionPointer);
        out.println("  input:" + input.toString());
        out.println("  output:" + output.toString());
    }
}
