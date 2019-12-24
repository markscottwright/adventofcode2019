package com.markscottwright.adventofcode2019;

import org.junit.Test;

public class AsteroidSetTest {

    @Test
    public void testSamples() {
        var asteroids = new AsteroidSet(
        //@formatter:off
                "......#.#.\r\n" + 
                "#..#.#....\r\n" + 
                "..#######.\r\n" + 
                ".#.#.###..\r\n" + 
                ".#..#.....\r\n" + 
                "..#....#.#\r\n" + 
                "#..#....#.\r\n" + 
                ".##.#..###\r\n" + 
                "##...#..#.\r\n" + 
                ".#....####\r\n");
        //@formatter:on
        System.out.println(asteroids.bestMonitoringPosition());

        asteroids = new AsteroidSet(
        //@formatter:off
                "#.#...#.#.\r\n" + 
                ".###....#.\r\n" + 
                ".#....#...\r\n" + 
                "##.#.#.#.#\r\n" + 
                "....#.#.#.\r\n" + 
                ".##..###.#\r\n" + 
                "..#...##..\r\n" + 
                "..##....##\r\n" + 
                "......#...\r\n" + 
                ".####.###.\r\n");
                //@formatter:on
        System.out.println(asteroids.bestMonitoringPosition());

        asteroids = new AsteroidSet(
        //@formatter:off
                ".#..#..###\r\n" + 
                "####.###.#\r\n" + 
                "....###.#.\r\n" + 
                "..###.##.#\r\n" + 
                "##.##.#.#.\r\n" + 
                "....###..#\r\n" + 
                "..#.#..#.#\r\n" + 
                "#..#.#.###\r\n" + 
                ".##...##.#\r\n" + 
                ".....#.#..\r\n");
                //@formatter:on
        System.out.println(asteroids.bestMonitoringPosition());
        asteroids = new AsteroidSet(
        //@formatter:off
                ".#..#\r\n" + 
                ".....\r\n" + 
                "#####\r\n" + 
                "....#\r\n" + 
                "...##\r\n");
                //@formatter:on
        System.out.println(asteroids.toSolutionString());
    }

}
