package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;

public class AmplifierSetTest {

    @Test
    public void testProvidedSample1() throws IntcodeException {
        List<Long> input = IntcodeComputer
                .parse("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0");
        AmplifierSet amps = new AmplifierSet(input, 4, 3, 2, 1, 0);
        assertThat(amps.run()).isEqualTo(43210);
    }

    @Test
    public void testProvidedSample2() throws IntcodeException {
        List<Long> input = IntcodeComputer.parse(
                "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0");
        AmplifierSet amps = new AmplifierSet(input, 0, 1, 2, 3, 4);
        assertThat(amps.run()).isEqualTo(54321);
    }
}
