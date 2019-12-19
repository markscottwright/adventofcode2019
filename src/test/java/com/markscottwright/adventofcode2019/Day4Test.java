package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day4Test {

    @Test
    public void testDigits() {
        assertThat(Day4.digits(111_111))
                .isEqualTo(new int[] { 1, 1, 1, 1, 1, 1 });
        assertThat(Day4.digits(123_456))
                .isEqualTo(new int[] { 1, 2, 3, 4, 5, 6 });
    }

}
