package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.markscottwright.adventofcode2019.intcode.CollectingOutput;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;

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

}
