package com.markscottwright.adventofcode2019;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.math3.util.ArithmeticUtils;

public class Position implements Comparable<Position> {
    final int x;
    final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Set<Position> pointsBlockedBy(Position other, int gridX, int gridY) {
        HashSet<Position> out = new HashSet<>();
        int xDiff = Math.abs(x - other.x);
        int yDiff = Math.abs(y - other.y);
        Position t = new Position(other.x + xDiff, other.y + yDiff);
        while (t.inGrid(gridX, gridY)) {
            out.add(t);
            t = new Position(t.x + xDiff, t.y + yDiff);
        }
        return out;
    }

    Collection<Position> evenPositionsBetween(Position other) {
        var out = new ArrayList<Position>();
        int xDiff = other.x - x;
        int yDiff = other.y - y;
        int gcd = Math.abs(ArithmeticUtils.gcd(xDiff, yDiff));
        xDiff = xDiff / gcd;
        yDiff = yDiff / gcd;
        for (int testX = x + xDiff, testY = y + yDiff; !(testX == other.x
                && testY == other.y); testX += xDiff, testY += yDiff) {
            out.add(new Position(testX, testY));
        }
        return out;
    }

    Angle angleTo(Position other) {
        return new Angle(this, other);
    }

    /**
     * In theory, order by this for rotational laser? But... I don't like
     * switching to floating point...
     * 
     * What if I do for x = this.x to xMax, for y =
     */
    double polarAngleTo(Position other) {
        double cartesianX = other.x - x;
        double cartesianY = other.y - y;
        return Math.atan2(cartesianY, (float) cartesianX) + Math.PI / 2.0;
    }

    private boolean inGrid(int gridX, int gridY) {
        return x <= gridX && y <= gridY && x >= 0 && y >= 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public int compareTo(Position o) {
        if (Integer.compare(x, o.x) == 0)
            return Integer.compare(y, o.y);
        else
            return Integer.compare(x, o.x);

    }

    public Optional<Position> closestOf(ArrayList<Position> asteroidsAtAngle) {
        return asteroidsAtAngle.stream().min((p1, p2) -> Integer
                .compare(this.distanceTo(p1), this.distanceTo(p2)));
    }

    /**
     * Manhattan distance
     */
    private int distanceTo(Position other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
}
