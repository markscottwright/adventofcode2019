package com.markscottwright.adventofcode2019;

import static com.markscottwright.adventofcode2019.Period.periodOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

public class PeriodTest {

    @Test
    public void testPeriod2() throws Exception {
        assertThat(periodOf(new int[] { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 }))
                .isPresent();
        assertThat(periodOf(new int[] { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5 }))
                .isEmpty();
        assertThat(periodOf(
                new int[] { 4, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 }))
                        .isEmpty();
        Optional<Integer> p = periodOf(
                new int[] { 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 });
        assertThat(p).isPresent();
        assertThat(p.get()).isEqualTo(6);
    }

}
