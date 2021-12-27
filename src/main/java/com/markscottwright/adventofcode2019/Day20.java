package com.markscottwright.adventofcode2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isWhitespace;

public class Day20 {
    static class Point {

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
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
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

        public Point[] getNeighbors() {
            //@formatter:off
            return new Point[] { 
                    new Point(x - 1, y), 
                    new Point(x + 1, y), 
                    new Point(x, y - 1),
                    new Point(x, y + 1) };
            //@formatter:on
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    static class Maze {
        private Set<Point> paths;
        private Map<Point, Point> portals;
        private Point exit;
        private Point entrance;
        private Set<Point> decreasingPortals;

        public Maze(Set<Point> paths, Map<Point, Point> portals, Point entrance, Point exit,
                Set<Point> decreasingPortals) {
            this.paths = paths;
            this.portals = portals;
            this.entrance = entrance;
            this.exit = exit;
            this.decreasingPortals = decreasingPortals;
        }

        /**
         * @param lines
         * @return
         */
        static Maze parse(String[] lines) {
            Set<Point> paths = new HashSet<Point>();
            Map<Point, Point> portals = new HashMap<Point, Point>();
            Set<Point> decreasingPortals = new HashSet<>();

            // remember the paths
            for (int y = 0; y < lines.length; ++y) {
                for (int x = 0; x < lines[y].length(); ++x) {
                    if (lines[y].charAt(x) == '.')
                        paths.add(new Point(x, y));
                }
            }

            // find and link all the portals
            Map<String, Pair<Point, Point>> portalsSeen = new HashMap<>();
            for (int y = 0; y < lines.length - 1; ++y) {
                for (int x = 0; x < lines[y].length() - 1; ++x) {
                    String portalLabel = new String();
                    Point portalPosition = null;
                    Point portalExit = null;
                    if (isAlphabetic(lines[y].charAt(x)) && isAlphabetic(lines[y].charAt(x + 1))) {
                        portalLabel += lines[y].charAt(x);
                        portalLabel += lines[y].charAt(x + 1);
                        if (x > 0 && lines[y].charAt(x - 1) == '.') {
                            portalPosition = new Point(x, y);
                            portalExit = new Point(x - 1, y);

                            // are there any maze squares to the right of this label? If so, this is
                            // an inner (size-decreasing) portal
                            for (int x2 = x + 2; x2 < lines[y].length(); ++x2) {
                                if (!isWhitespace(lines[y].charAt(x2))) {
                                    decreasingPortals.add(portalPosition);
                                    break;
                                }
                            }
                        } else {
                            assert lines[y].charAt(x + 2) == '.';
                            portalPosition = new Point(x + 1, y);
                            portalExit = new Point(x + 2, y);

                            // are there any maze squares to the left of this label? If so, this is
                            // an inner (size-decreasing) portal
                            for (int x2 = x - 1; x2 >= 0; --x2) {
                                if (!isWhitespace(lines[y].charAt(x2))) {
                                    decreasingPortals.add(portalPosition);
                                    break;
                                }
                            }
                        }
                    } else if (isAlphabetic(lines[y].charAt(x))
                            && isAlphabetic(lines[y + 1].charAt(x))) {
                        portalLabel += lines[y].charAt(x);
                        portalLabel += lines[y + 1].charAt(x);
                        if (y > 0 && lines[y - 1].charAt(x) == '.') {
                            portalPosition = new Point(x, y);
                            portalExit = new Point(x, y - 1);

                            // are there any maze squares below this label? If so, this is an
                            // inner (size-decreasing) portal
                            for (int y2 = y + 2; y2 < lines.length; ++y2) {
                                if (!isWhitespace(lines[y2].charAt(x))) {
                                    decreasingPortals.add(portalPosition);
                                    break;
                                }
                            }
                        } else {
                            assert lines[y + 2].charAt(x) == '.';
                            portalPosition = new Point(x, y + 1);
                            portalExit = new Point(x, y + 2);

                            // are there any maze squares above this label? If so, this is an
                            // inner (size-decreasing) portal
                            for (int y2 = y - 1; y2 >= 0; --y2) {
                                if (!isWhitespace(lines[y2].charAt(x))) {
                                    decreasingPortals.add(portalPosition);
                                    break;
                                }
                            }
                        }
                    }

                    // portal found. Remember its position and nearest path, or link it with a
                    // previously found portal
                    if (portalPosition != null) {
                        if (portalsSeen.containsKey(portalLabel)) {
                            portals.put(portalPosition, portalsSeen.get(portalLabel).getRight());
                            portals.put(portalsSeen.get(portalLabel).getLeft(), portalExit);
                            portalsSeen.remove(portalLabel);
                        } else {
                            portalsSeen.put(portalLabel, Pair.of(portalPosition, portalExit));
                        }
                    }
                }
            }

            return new Maze(paths, portals, portalsSeen.get("AA").getRight(),
                    portalsSeen.get("ZZ").getRight(), decreasingPortals);
        }

        static class SearchNode {
            private Point position;
            private int dimension;
            private int distance;

            public SearchNode(Point position, SearchNode previous) {
                this.position = position;
                this.dimension = 0;
                this.distance = (previous == null) ? 0 : 1 + previous.distance;
            }

            public SearchNode(Point position, int dimension, SearchNode previous) {
                this.position = position;
                this.dimension = dimension;
                this.distance = (previous == null) ? 0 : 1 + previous.distance;
            }
        }

        public int findPath() {
            var frontier = new Queue<SearchNode>();
            frontier.add(new SearchNode(entrance, null));

            var explored = new HashMap<Point, Integer>();
            while (!frontier.isEmpty()) {
                var next = frontier.pop();
                if (!explored.containsKey(next.position)
                        || explored.get(next.position) > next.distance) {
                    explored.put(next.position, next.distance);
                    if (next.position.equals(exit)) {
                        return next.distance;
                    }

                    for (Point p : next.position.getNeighbors()) {
                        if (paths.contains(p)) {
                            frontier.add(new SearchNode(p, next));
                        } else if (portals.containsKey(p)) {
                            frontier.add(new SearchNode(portals.get(p), next));
                        }
                    }
                }
            }

            assert false;
            return -1;
        }

        /**
         * Find the length of the path if portals change to the same maze in a different
         * dimension.
         */
        public int findPath2() {
            var frontier = new Queue<SearchNode>();
            frontier.add(new SearchNode(entrance, 0, null));

            // explored space is now point+dimension
            var explored = new HashMap<Pair<Point, Integer>, Integer>();
            
            while (!frontier.isEmpty()) {
                var next = frontier.pop();
                
                // if not already explored, or if this is a better path to point+dimension...
                if (!explored.containsKey(Pair.of(next.position, next.dimension))
                        || explored.get(Pair.of(next.position, next.dimension)) > next.distance) {
                    
                    // record that we've reached here
                    explored.put(Pair.of(next.position, next.dimension), next.distance);
                    
                    // at exit - we're done
                    if (next.position.equals(exit) && next.dimension == 0) {
                        return next.distance;
                    }

                    for (Point p : next.position.getNeighbors()) {
                        
                        // regular path point
                        if (paths.contains(p)) {
                            frontier.add(new SearchNode(p, next.dimension, next));
                        } 
                        
                        // a portal
                        else if (portals.containsKey(p)) {
                            
                            // if we're decreasing in size, add one to dimension and move to portal exit
                            if (decreasingPortals.contains(p)) {
                                frontier.add(
                                        new SearchNode(portals.get(p), next.dimension + 1, next));
                            }

                            // if we're increasing in dimension size, and we're not already at
                            // dimension 0, subtract one from dimension and move to exit
                            else if (next.dimension != 0) {
                                frontier.add(
                                        new SearchNode(portals.get(p), next.dimension - 1, next));
                            }
                        }
                    }
                }
            }

            assert false;
            return -1;
        }
    }

    public static void main(String[] args) {
        Maze maze = Maze.parse(Util.getInputLines("day20.txt"));
        System.out.println("Day 20 part 1: " + maze.findPath());
        System.out.println("Day 20 part 2: " + maze.findPath2());
    }

}
