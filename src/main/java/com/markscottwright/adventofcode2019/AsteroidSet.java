package com.markscottwright.adventofcode2019;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class AsteroidSet {
    TreeSet<Position> asteroids = new TreeSet<>();
    int maxX;
    int maxY;

    public AsteroidSet(String input) {
        String[] rows = input.split("\r\n");
        maxY = rows.length - 1;
        maxX = rows[0].length() - 1;
        for (int y = 0; y <= maxY; ++y) {
            for (int x = 0; x <= maxX; ++x) {
                if (rows[y].charAt(x) == '#')
                    asteroids.add(new Position(x, y));
            }
        }
    }

    @Override
    public String toString() {
        StringWriter out = new StringWriter();
        for (int y = 0; y <= maxY; ++y) {
            for (int x = 0; x <= maxX; ++x) {
                if (asteroids.contains(new Position(x, y))) {
                    out.write("*");
                } else {
                    out.write(" ");
                }
            }
            out.write("\r\n");
        }
        return out.toString();
    }

    public String toSolutionString() {
        StringWriter out = new StringWriter();
        for (int y = 0; y <= maxY; ++y) {
            for (int x = 0; x <= maxX; ++x) {
                if (asteroids.contains(new Position(x, y))) {
                    int detectableBy = asteroidsDetectableBy(new Position(x, y))
                            .size();
                    out.write(String.format("%01d", detectableBy));
                } else {
                    out.write(".");
                }
            }
            out.write("\r\n");
        }
        return out.toString();
    }

    public int pointsBlockedFor(Position asteroid) {
        TreeSet<Position> blocked = new TreeSet<>();
        for (Position asteroid2 : asteroids) {
            if (!asteroid.equals(asteroid2))
                blocked.addAll(asteroid.pointsBlockedBy(asteroid2, maxX, maxY));
        }
        return blocked.size();
    }

    Set<Position> asteroidsDetectableBy(Position asteroid) {
        var detectable = new TreeSet<Position>();

        nextTargetAsteroid: for (var targetAsteroid : asteroids) {
            if (!asteroid.equals(targetAsteroid)) {
                Collection<Position> positionsBetween = asteroid
                        .evenPositionsBetween(targetAsteroid);
                // if any positions between a2 and asteroid are occupied, a2 is
                // not detectable from asteroid
                for (var pos : positionsBetween)
                    if (asteroids.contains(pos))
                        continue nextTargetAsteroid;
                detectable.add(targetAsteroid);
            }
        }
        return detectable;
    }

    public Position bestMonitoringPosition() {
        int mostDetected = 0;
        Position bestAsteroid = null;
        for (Position asteroid : asteroids) {
            Set<Position> asteroidsDetectibleBy = asteroidsDetectableBy(
                    asteroid);
            final int numDetected = asteroidsDetectibleBy.size();
            if (numDetected > mostDetected) {
                mostDetected = numDetected;
                bestAsteroid = asteroid;
            }
        }

        return bestAsteroid;
    }

    public int asteroidsVisibleFromBestMonitoringPosition() {
        int mostDetected = 0;
        Set<Position> largestSet = null;
        for (Position asteroid : asteroids) {
            Set<Position> asteroidsDetectibleBy = asteroidsDetectableBy(
                    asteroid);
            final int numDetected = asteroidsDetectibleBy.size();
            if (numDetected > mostDetected) {
                mostDetected = numDetected;
                largestSet = asteroidsDetectibleBy;
            }
        }
        return largestSet.size();
    }
    

    TreeMap<Angle, ArrayList<Position>> asteroidsAtAngleFrom(Position pos) {
        TreeMap<Angle, ArrayList<Position>> out = new TreeMap<>();
        for (var asteroid: asteroids) {
            if (!asteroid.equals(pos)) {
                Angle ratio = pos.angleTo(asteroid);
                if (!out.containsKey(ratio))
                    out.put(ratio, new ArrayList<>());
                out.get(ratio).add(asteroid);
            }
        }
        return out;
    }
}