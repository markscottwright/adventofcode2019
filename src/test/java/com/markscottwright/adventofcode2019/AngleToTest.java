package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AngleToTest {

    @Test
    public void test() {
        Position origin = new Position(5, 5);
        Position[] points = {
                // @formatter:off
                new Position(5, 0), 
                new Position(6, 0), 
                new Position(7, 0), 
                new Position(6, 4), 
                new Position(10, 5),
                new Position(10, 6),
                new Position(10, 7),
                new Position(6, 6),
                new Position(5, 10), 
                new Position(0, 5),
                // @formatter:on
        };

        for (int i = 0; i < points.length - 1; ++i) {
            Angle angle1 = origin.angleTo(points[i]);
            Angle angle2 = origin.angleTo(points[i + 1]);
            assertThat(angle1).isLessThan(angle2);
        }
    }

}
