package com.markscottwright.adventofcode2019;

import org.apache.commons.math3.util.ArithmeticUtils;

/**
 * This... If I remembered trig, or if web resources were aimed at people who
 * want to use trig rather than do Algebra I homework, I bet I could make
 * one-liner.
 * 
 * The need to use rational numbers, rather than floats, complicates this too.
 * It's possible I could get away with floats, of course, but it seems like
 * there could be rounding errors.
 */
public class Angle implements Comparable<Angle> {

    enum Direction {
        N, NE, E, SE, S, SW, W, NW
    }

    static final int NORTH = 0;
    static final int NORTH_EAST = 1;
    static final int EAST = 2;
    static final int SOUTH_EAST = 3;
    static final int SOUTH = 4;
    static final int SOUTH_WEST = 5;
    static final int WEST = 6;
    static final int NORTH_WEST = 7;

    final private int x;
    final private int y;
    final private Direction direction;

    public Angle(Position p1, Position p2) {
        int x = (p2.x - p1.x);
        int y = (p2.y - p1.y);
        if (x == 0 && y < 0) {
            this.direction = Direction.N;
            this.x = 0;
            this.y = 1;
        } else if (x == 0 && y > 0) {
            this.direction = Direction.S;
            this.x = 0;
            this.y = -1;
        } else if (y == 0 && x > 0) {
            this.direction = Direction.E;
            this.x = 1;
            this.y = 0;
        } else if (y == 0 && x < 0) {
            this.direction = Direction.W;
            this.x = -1;
            this.y = 0;
        } else {
            if (x > 0 && y < 0)
                this.direction = Direction.NE;
            else if (x > 0 && y > 0)
                this.direction = Direction.SE;
            else if (x < 0 && y > 0)
                this.direction = Direction.SW;
            else
                this.direction = Direction.NW;
            int gcd = ArithmeticUtils.gcd(Math.abs(x), Math.abs(y));
            this.x = Math.abs(x) / gcd;
            this.y = Math.abs(y) / gcd;
        }
    }

    @Override
    public int compareTo(Angle o) {
        if (direction.ordinal() < o.direction.ordinal())
            return -1;
        else if (direction.ordinal() > o.direction.ordinal())
            return 1;
        else {
            if (direction == Direction.N || direction == Direction.E
                    || direction == Direction.S || direction == Direction.W)
                return 0;
            else if (direction == Direction.NE || direction == Direction.SW)
                return ratioCompare(x, y, o.x, o.y);
            else
                return -ratioCompare(x, y, o.x, o.y);
        }
    }

    private int ratioCompare(int numerator1, int denominator1, int numerator2,
            int denominator2) {
        return Integer.compare(numerator1 * denominator2,
                numerator2 * denominator1);

    }

    @Override
    public String toString() {
        return direction + ":" + x + "/" + y;
    }

}
