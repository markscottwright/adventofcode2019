package com.markscottwright.adventofcode2019;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.Set;

import com.markscottwright.adventofcode2019.intcode.IntcodeOutput;

public class EmergencyHullPaintingRobot
        implements IntcodeOutput, Iterator<Long> {

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    };

    Direction direction = Direction.UP;
    Point position = new Point(0, 0);
    Set<Point> paintedWhite = new HashSet<Point>();
    Set<Point> panelsPainted = new HashSet<>();
    boolean waitingForColorInstruction = true;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Long next() {
        if (paintedWhite.contains(position))
            return 1L;
        else
            return 0L;
    }

    @Override
    public void put(long aVal) {
        if (waitingForColorInstruction) {
            if (aVal == 1L)
                paintedWhite.add(position);
            else
                paintedWhite.remove(position);
            panelsPainted.add(position);
        } else {
            if (aVal == 1) {
                direction = Direction.values()[(direction.ordinal() + 1)
                        % Direction.values().length];
            } else
                direction = Direction.values()[(direction.ordinal() - 1
                        + Direction.values().length)
                        % Direction.values().length];
            if (direction == Direction.UP)
                position = position.moveUp(1);
            else if (direction == Direction.LEFT)
                position = position.moveLeft(1);
            else if (direction == Direction.RIGHT)
                position = position.moveRight(1);
            else
                position = position.moveDown(1);
        }

        // switch to next state
        waitingForColorInstruction = !waitingForColorInstruction;
    }

    public int numberOfPanelsPainted() {
        return panelsPainted.size();
    }

    public void printPaintingOn(PrintStream out) {
        IntSummaryStatistics widthInfo = paintedWhite.stream()
                .mapToInt(p -> p.x).summaryStatistics();
        IntSummaryStatistics heightInfo = paintedWhite.stream()
                .mapToInt(p -> p.y).summaryStatistics();

        for (int x = widthInfo.getMax(); x >= widthInfo.getMin(); --x) {
            for (int y = heightInfo.getMax(); y >= heightInfo.getMin(); --y) {
                if (paintedWhite.contains(new Point(x, y))) {
                    out.print("\u2588");
                } else {
                    out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
