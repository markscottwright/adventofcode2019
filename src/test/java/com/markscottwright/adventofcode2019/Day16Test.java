package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day16Test {

    static int[] digits(String s) {
        var out = new int[s.length()];
        for (int i = 0; i < s.length(); ++i)
            out[i] = s.charAt(i) - '0';
        return out;
    }

    @Test
    public void test() {
        assertThat(Day16.applyPattern(0, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }))
                .isEqualTo(new int[] { 4, 8, 2, 2, 6, 1, 5, 8 });
        assertThat(
                Day16.applyPattern(0, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }, 1))
                        .isEqualTo(new int[] { 4, 8, 2, 2, 6, 1, 5, 8 });
        assertThat(
                Day16.applyPattern(0, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }, 2))
                        .isEqualTo(new int[] { 3, 4, 0, 4, 0, 4, 3, 8 });

        assertThat(Day16.first8(Day16.applyPattern(0,
                digits("80871224585914546619083218645595"), 100)))
                        .isEqualTo(digits("24176176"));
    }

}
