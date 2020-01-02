package com.markscottwright.adventofcode2019;

import static com.markscottwright.adventofcode2019.Period.periodOf;

import java.util.stream.Collectors;

import org.apache.commons.math3.util.ArithmeticUtils;

public class Day12 {
    public static void main(String[] args) {
        System.out.print("Day 12 part 1: ");

        var moons = Moon.parse(
        //@formatter:off
                "<x=-4, y=-14, z=8>\r\n" + 
                "<x=1, y=-8, z=10>\r\n" + 
                "<x=-15, y=2, z=1>\r\n" + 
                "<x=-17, y=-17, z=16>"
                //@formatter:on
        );

        for (int i = 0; i < 1000; ++i) {
            final var currentMoons = moons;
            moons = moons.stream().map(m -> m.applyGravity(currentMoons))
                    .collect(Collectors.toList());
        }
        System.out.println(Moon.totalEnergy(moons));

        // find how many times until the system repeats on each axis. The lowest
        // common multiple of each of those repeating periods is how often the
        // system itself repeats (and our answer)
        System.out.print("Day 12 part 2: ");
        int maxCollected = 1_000_000;
        int[] xVals = new int[maxCollected];
        int[] yVals = new int[maxCollected];
        int[] zVals = new int[maxCollected];
        for (int i = 0; i < maxCollected; ++i) {

            xVals[i] = moons.get(0).x;
            yVals[i] = moons.get(0).y;
            zVals[i] = moons.get(0).z;

            final var currentMoons = moons;
            moons = moons.stream().map(m -> m.applyGravity(currentMoons))
                    .collect(Collectors.toList());
        }
        long xPeriod = periodOf(xVals).get();
        long yPeriod = periodOf(yVals).get();
        long zPeriod = periodOf(zVals).get();
        System.out.println(ArithmeticUtils.lcm(zPeriod,
                ArithmeticUtils.lcm(xPeriod, yPeriod)));
    }
}
