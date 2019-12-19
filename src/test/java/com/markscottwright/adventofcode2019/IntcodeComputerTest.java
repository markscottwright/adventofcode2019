package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

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

}
