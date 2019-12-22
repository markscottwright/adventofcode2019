package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.markscottwright.adventofcode2019.intcode.CollectingOutput;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;
import com.markscottwright.adventofcode2019.intcode.Pipe;

public class IntcodeComputerTest {

    @Test
    public void testOpcodes3And4() throws IntcodeException {
        Iterator<Long> input = List.of(500L).iterator();
        CollectingOutput output = new CollectingOutput();
        IntcodeComputer computer = new IntcodeComputer(
                List.of(3L, 0L, 4L, 0L, 99L), input, output);
        computer.run();
        assertThat(output.getValues()).containsExactly(500L);
    }

    @Test
    public void testResumable() throws IntcodeException {
        List<Long> input = new ArrayList<>();
        input.add(1l);

        // one input opcodes, one input value
        IntcodeComputer computer1 = new IntcodeComputer(
                List.of(3L, 3L, 99L, 0L), input.iterator(),
                new CollectingOutput());
        computer1.setPauseWhenInputEmpty(true);
        assertThat(computer1.runUntilInputEmpty())
                .isEqualTo(IntcodeComputer.State.halted);

        // two input opcodes, one input value
        IntcodeComputer computer2 = new IntcodeComputer(
                List.of(3L, 5L, 3L, 5L, 99L, 0L), input.iterator(),
                new CollectingOutput());
        computer2.setPauseWhenInputEmpty(true);
        assertThat(computer2.runUntilInputEmpty())
                .isEqualTo(IntcodeComputer.State.waiting);
    }

    public static List<Long> longListOf(int... ints) {
        var out = new ArrayList<Long>();
        for (int anInt : ints)
            out.add((long) anInt);
        return out;
    }

    @Test
    public void day9Sample1() throws Exception {
        Pipe output = new Pipe();
        var instructions = longListOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008,
                100, 16, 101, 1006, 101, 0, 99);
        new IntcodeComputer(instructions, Collections.emptyIterator(), output)
                .run();
        assertThat(output.asList()).isEqualTo(instructions);

    }

    @Test
    public void day9Sample2() throws Exception {
        Pipe output = new Pipe();
        var instructions = longListOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0);
        new IntcodeComputer(instructions, Collections.emptyIterator(), output)
                .run();
        assertThat(Long.toString(output.firstValueInPipe()).toString().length())
                .isEqualTo(16);
    }

    @Test
    public void day9Sample3() throws Exception {
        Pipe output = new Pipe();
        var instructions = List.of(104L, 1125899906842624L, 99L);
        new IntcodeComputer(instructions, Collections.emptyIterator(), output)
                .run();
        assertThat(output.firstValueInPipe()).isEqualTo(1125899906842624L);
    }

}
