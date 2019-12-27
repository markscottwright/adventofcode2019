package com.markscottwright.adventofcode2019;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Moon {
    final int x, y, z;
    final int xV, yV, zV;

    public Moon(int x, int y, int z) {
        this(x, y, z, 0, 0, 0);
    }

    private Moon(int x, int y, int z, int xV, int yV, int zV) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xV = xV;
        this.yV = yV;
        this.zV = zV;
    }

    Moon applyGravity(Collection<Moon> moons) {
        int xVNext = xV, yVNext = yV, zVNext = zV;

        for (Moon m : moons) {
            if (this != m) {
                xVNext += vDiff(x, m.x);
                yVNext += vDiff(y, m.y);
                zVNext += vDiff(z, m.z);
            }
        }

        return new Moon(x + xVNext, y + yVNext, z + zVNext, xVNext, yVNext,
                zVNext);
    }

    private int vDiff(int axis1, int axis2) {
        return -Integer.compare(axis1, axis2);
    }

    @Override
    public String toString() {
        return String.format("pos=<x=%d,y=%d,z=%d>, vel=<x=%d,y=%d,z=%d>", x, y,
                z, xV, yV, zV);
    }

    public static Moon parseLine(String l) {
        Pattern withVelocity = Pattern.compile(
                "pos=<x=\\s*([-0-9]*), y=\\s*([-0-9]*), z=\\s*([-0-9]*)>, vel=<x=\\s*([-0-9]*), y=\\s*([-0-9]*), z=\\s*([-0-9]*)>");
        Pattern withoutVelocity = Pattern
                .compile("<x=\\s*([-0-9]*), y=\\s*([-0-9]*), z=\\s*([-0-9]*)>");
        Matcher matcher = withVelocity.matcher(l);
        if (matcher.matches()) {
            return new Moon(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5)),
                    Integer.parseInt(matcher.group(6)));
        } else {
            matcher = withoutVelocity.matcher(l);
            if (!matcher.matches())
                throw new InvalidParameterException(l);
            return new Moon(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)));
        }
    }

    public static List<Moon> parse(String string) {
        return Arrays.stream(string.split("\r?\n")).map(Moon::parseLine)
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, xV, y, yV, z, zV);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Moon other = (Moon) obj;
        return x == other.x && xV == other.xV && y == other.y && yV == other.yV
                && z == other.z && zV == other.zV;
    }

    int energy() {
        return (Math.abs(x) + Math.abs(y) + Math.abs(z))
                * (Math.abs(xV) + Math.abs(yV) + Math.abs(zV));
    }

    public static int totalEnergy(Collection<Moon> moons) {
        return moons.stream().mapToInt(Moon::energy).sum();
    }
}
