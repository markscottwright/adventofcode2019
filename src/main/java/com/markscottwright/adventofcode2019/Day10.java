package com.markscottwright.adventofcode2019;

import java.util.ArrayList;
import java.util.Optional;

public class Day10 {
    final static String INPUT =
    // @formatter:off
              "#...##.####.#.......#.##..##.#.\r\n"
            + "#.##.#..#..#...##..##.##.#.....\r\n"
            + "#..#####.#......#..#....#.###.#\r\n"
            + "...#.#.#...#..#.....#..#..#.#..\r\n"
            + ".#.....##..#...#..#.#...##.....\r\n"
            + "##.....#..........##..#......##\r\n"
            + ".##..##.#.#....##..##.......#..\r\n"
            + "#.##.##....###..#...##...##....\r\n"
            + "##.#.#............##..#...##..#\r\n"
            + "###..##.###.....#.##...####....\r\n"
            + "...##..#...##...##..#.#..#...#.\r\n"
            + "..#.#.##.#.#.#####.#....####.#.\r\n"
            + "#......###.##....#...#...#...##\r\n"
            + ".....#...#.#.#.#....#...#......\r\n"
            + "#..#.#.#..#....#..#...#..#..##.\r\n"
            + "#.....#..##.....#...###..#..#.#\r\n"
            + ".....####.#..#...##..#..#..#..#\r\n"
            + "..#.....#.#........#.#.##..####\r\n"
            + ".#.....##..#.##.....#...###....\r\n"
            + "###.###....#..#..#.....#####...\r\n"
            + "#..##.##..##.#.#....#.#......#.\r\n"
            + ".#....#.##..#.#.#.......##.....\r\n"
            + "##.##...#...#....###.#....#....\r\n"
            + ".....#.######.#.#..#..#.#.....#\r\n"
            + ".#..#.##.#....#.##..#.#...##..#\r\n"
            + ".##.###..#..#..#.###...#####.#.\r\n"
            + "#...#...........#.....#.......#\r\n"
            + "#....##.#.#..##...#..####...#..\r\n"
            + "#.####......#####.....#.##..#..\r\n"
            + ".#...#....#...##..##.#.#......#\r\n"
            + "#..###.....##.#.......#.##...##\r\n";
            // @formatter:on

    public static void main(String[] args) {
        System.out.print("Day 10 part 1: ");

        AsteroidSet asteroids = new AsteroidSet(INPUT);
        var pos = asteroids.bestMonitoringPosition();
        System.out.println(
                asteroids.asteroidsVisibleFromBestMonitoringPosition());

        System.out.print("Day 10 part 2: ");
        var laserPlan = asteroids.asteroidsAtAngleFrom(pos);
        int blastedAsteroids = 0;
        sweepLaser: while (true) {
            for (Angle angle : laserPlan.keySet()) {
                ArrayList<Position> asteroidsAtAngle = laserPlan.get(angle);
                Optional<Position> closest = pos.closestOf(asteroidsAtAngle);
                if (closest.isPresent()) {
                    asteroidsAtAngle.remove(closest.get());
                    blastedAsteroids++;
                    if (blastedAsteroids == 200) {
                        System.out.println(closest.get().x * 100 + closest.get().y);
                        break sweepLaser;
                    }
                }
            }
        }
    }
}
