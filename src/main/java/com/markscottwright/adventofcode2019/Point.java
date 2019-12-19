package com.markscottwright.adventofcode2019;

import java.util.Objects;

public class Point {
    final int x;
    final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point moveLeft(int distance) {
        return new Point(x - distance, y);
    }

    Point moveRight(int distance) {
        return new Point(x + distance, y);
    }

    Point moveUp(int distance) {
        return new Point(x, y + distance);
    }

    Point moveDown(int distance) {
        return new Point(x, y - distance);
    }

    int manhattanDistance(Point o) {
        return Math.abs(o.x - x) + Math.abs(o.y - y);
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
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }
}
