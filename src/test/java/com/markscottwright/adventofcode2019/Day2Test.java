package com.markscottwright.adventofcode2019;

import static com.markscottwright.adventofcode2019.IntcodeComputer.parse;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.markscottwright.adventofcode2019.IntcodeComputer.IntcodeException;

public class Day2Test {

    @Test
    public void testIntcodeComputer() throws IntcodeException {
        assertThat(new IntcodeComputer(parse("1,0,0,0,99")).run())
                .isEqualTo(parse("2,0,0,0,99"));
        assertThat(new IntcodeComputer(parse("2,3,0,3,99")).run())
                .isEqualTo(parse("2,3,0,6,99"));
        assertThat(new IntcodeComputer(parse("2,4,4,5,99,0")).run())
                .isEqualTo(parse("2,4,4,5,99,9801"));
        assertThat(new IntcodeComputer(parse("1,1,1,4,99,5,6,0,99")).run())
                .isEqualTo(parse("30,1,1,4,2,5,6,0,99"));
    }

}
