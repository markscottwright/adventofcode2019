package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.markscottwright.adventofcode2019.intcode.CollectingOutput;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;

public class Day5Test {

    @Test
    public void testExample() throws IntcodeException {
        List<Long> instructions = IntcodeComputer
                .parse("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,"
                        + "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,"
                        + "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99");
        CollectingOutput output = new CollectingOutput();
        new IntcodeComputer(instructions, List.of(7L).iterator(), output).run();
        assertThat(output.getLastValue()).isEqualTo(999L);

        output = new CollectingOutput();
        new IntcodeComputer(instructions, List.of(8L).iterator(), output).run();
        assertThat(output.getLastValue()).isEqualTo(1000L);

        output = new CollectingOutput();
        new IntcodeComputer(instructions, List.of(9L).iterator(), output).run();
        assertThat(output.getLastValue()).isEqualTo(1001L);
    }

}
