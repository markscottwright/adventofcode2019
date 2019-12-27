package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Percentage;
import org.junit.Test;

public class PositionTest {

    @Test
    public void testEvenPointsBetween() {
        var pointsBetween = new Position(0, 0)
                .evenPositionsBetween(new Position(5, 5));
        assertThat(pointsBetween).containsOnly(new Position(1, 1),
                new Position(2, 2), new Position(3, 3), new Position(4, 4));

        var pointsBetween2 = new Position(5, 5)
                .evenPositionsBetween(new Position(0, 0));
        assertThat(pointsBetween2).containsOnly(new Position(1, 1),
                new Position(2, 2), new Position(3, 3), new Position(4, 4));

        var pointsBetween3 = new Position(1, 1)
                .evenPositionsBetween(new Position(5, 1));
        assertThat(pointsBetween3).containsOnly(new Position(2, 1),
                new Position(3, 1), new Position(4, 1));

    }

    @Test
    public void testTrig() throws Exception {
        assertThat(new Position(1, 1).polarAngleTo(new Position(1, 0)))
                .isCloseTo(0.0, Percentage.withPercentage(0.0001));
        assertThat(new Position(1, 1).polarAngleTo(new Position(2, 1)))
                .isCloseTo(Math.PI / 2.0, Percentage.withPercentage(0.0001));
        assertThat(new Position(1, 1).polarAngleTo(new Position(1, 2)))
                .isCloseTo(Math.PI, Percentage.withPercentage(0.0001));
        assertThat(new Position(1, 1).polarAngleTo(new Position(0, 1)))
                .isCloseTo(Math.PI * 3.0 / 2.0,
                        Percentage.withPercentage(0.0001));
    }
}
