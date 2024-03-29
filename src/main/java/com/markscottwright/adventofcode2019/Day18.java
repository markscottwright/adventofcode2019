package com.markscottwright.adventofcode2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

public class Day18 {

    public static class Endpoint {

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((endpoints == null) ? 0 : endpoints.hashCode());
            result = prime * result + ((keys == null) ? 0 : keys.hashCode());
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
            Endpoint other = (Endpoint) obj;
            if (endpoints == null) {
                if (other.endpoints != null)
                    return false;
            } else if (!endpoints.equals(other.endpoints))
                return false;
            if (keys == null) {
                if (other.keys != null)
                    return false;
            } else if (!keys.equals(other.keys))
                return false;
            return true;
        }

        private Set<Character> keys;
        private List<Character> endpoints;

        public Endpoint(List<Character> endpoints, Set<Character> keys) {
            this.endpoints = endpoints;
            this.keys = keys;
        }

    }

    static class Queue<T> extends LinkedList<T> {
        public T pop() {
            return poll();
        }

        public void push(T elem) {
            offer(elem);
        }
    }

    static class Map {

        @Override
        public String toString() {
            return "Map [locations=" + locations + ", distances=" + distances + ", allKeys="
                    + allKeys + "]";
        }

        static class MapPoint extends Point {
            public MapPoint(int x, int y) {
                super(x, y);
            }

            public MapPoint[] getNeighbors() {
                // no diagonals
                return new MapPoint[] { new MapPoint(x - 1, y), new MapPoint(x + 1, y),
                        new MapPoint(x, y - 1), new MapPoint(x, y + 1)

                };
            }

            @Override
            public String toString() {
                return "(" + x + "," + y + ")";
            }
        }

        char[][] map;
        HashMap<Character, MapPoint> locations;
        HashSet<MapPoint> paths;
        HashMap<Pair<Character, Character>, Pair<Integer, HashSet<Character>>> distances;
        Set<Character> allKeys;

        static boolean isDoor(Character c) {
            return Character.isUpperCase(c);
        }

        static boolean isKey(Character c) {
            return Character.isLowerCase(c);
        }

        public Map(char[][] map, HashMap<Character, MapPoint> locations, HashSet<MapPoint> paths) {
            this.map = map;
            this.locations = locations;
            this.paths = paths;
            this.distances = findDistances(locations, paths);
            this.allKeys = locations.keySet().stream().filter(Map::isKey)
                    .collect(Collectors.toSet());
        }

        static private HashMap<Pair<Character, Character>, Pair<Integer, HashSet<Character>>> findDistances(
                HashMap<Character, MapPoint> locations, HashSet<MapPoint> paths) {
            var distances = new HashMap<Pair<Character, Character>, Pair<Integer, HashSet<Character>>>();

            var doorLocations = new HashMap<MapPoint, Character>();
            for (Entry<Character, MapPoint> location : locations.entrySet()) {
                if (isDoor(location.getKey())) {
                    doorLocations.put(location.getValue(), location.getKey());
                }
            }

            for (Character start : locations.keySet()) {
                if (isDoor(start))
                    continue;
                for (Character end : locations.keySet()) {
                    if (start == end || isDoor(end))
                        continue;

                    MapPoint endPosition = locations.get(end);
                    var visited = new HashSet<MapPoint>();
                    Queue<MazePathSearchState> frontier = new Queue<>();
                    frontier.add(new MazePathSearchState(locations.get(start), null));

                    while (!frontier.isEmpty()) {
                        var candidate = frontier.pop();
                        if (endPosition.equals(candidate.position)) {
                            distances.put(Pair.of(start, end), Pair.of(candidate.length,
                                    candidate.doorsPassed(doorLocations)));
                            break;
                        }

                        visited.add(candidate.position);
                        for (var neighbor : candidate.position.getNeighbors()) {
                            if (!visited.contains(neighbor) && paths.contains(neighbor))
                                frontier.push(new MazePathSearchState(neighbor, candidate));
                        }
                    }
                }
            }

            return distances;
        }

        static Map parse(String[] lines) {
            char[][] map = new char[lines.length][];
            int y = 0;
            for (String line : lines) {
                map[y] = line.toCharArray();
                y++;
            }

            var locations = new HashMap<Character, MapPoint>();
            var paths = new HashSet<MapPoint>();
            for (y = 0; y < map.length; ++y) {
                for (int x = 0; x < map[y].length; ++x) {
                    if (map[y][x] == '@' || isDoor(map[y][x]) || isKey(map[y][x])) {
                        locations.put(map[y][x], new MapPoint(x, y));
                    }
                    if (map[y][x] != '#') {
                        paths.add(new MapPoint(x, y));
                    }
                }
            }

            return new Map(map, locations, paths);
        }

        static List<String[]> divide(String[] lines) {
            int entranceX = -1, entranceY = -1;
            outer: for (int y = 0; y < lines.length; ++y) {
                for (int x = 0; x < lines[y].length(); x++) {
                    if (lines[y].charAt(x) == '@') {
                        entranceX = x;
                        entranceY = y;
                        break outer;
                    }
                }
            }

            String[] mapNW = new String[entranceY + 1];
            String[] mapNE = new String[entranceY + 1];
            String[] mapSW = new String[(lines.length - entranceY)];
            String[] mapSE = new String[(lines.length - entranceY)];
            for (int y = 0; y < lines.length; ++y) {
                String currentLine = lines[y];
                for (int x = 0; x < currentLine.length(); x++) {
                    if (y == entranceY) {
                        char[] lineChars = currentLine.toCharArray();
                        lineChars[entranceX - 1] = '#';
                        lineChars[entranceX] = '#';
                        lineChars[entranceX + 1] = '#';
                        currentLine = new String(lineChars);
                    } else if (y == entranceY - 1 || y == entranceY + 1) {
                        char[] lineChars = currentLine.toCharArray();
                        lineChars[entranceX - 1] = '@';
                        lineChars[entranceX] = '#';
                        lineChars[entranceX + 1] = '@';
                        currentLine = new String(lineChars);
                    }
                    if (y <= entranceY) {
                        mapNW[y] = currentLine.substring(0, entranceX + 1);
                        mapNE[y] = currentLine.substring(entranceX);
                    }
                    if (y >= entranceY) {
                        mapSW[y - entranceY] = currentLine.substring(0, entranceX + 1);
                        mapSE[y - entranceY] = currentLine.substring(entranceX);
                    }
                }
            }

            return Arrays.asList(mapNW, mapNE, mapSW, mapSE);
        }

        static class MazePathSearchState {
            public MazePathSearchState(MapPoint position, MazePathSearchState previous) {
                this.position = position;
                this.previous = previous;
                this.length = (previous == null) ? 0 : previous.length + 1;
            }

            public HashSet<Character> doorsPassed(HashMap<MapPoint, Character> doorLocations) {
                HashSet<Character> doorsPassed = new HashSet<Character>();
                MazePathSearchState cur = this.previous;
                while (cur != null && cur.previous != null) {
                    if (doorLocations.containsKey(cur.position))
                        doorsPassed.add(doorLocations.get(cur.position));
                    cur = cur.previous;
                }
                return doorsPassed;
            }

            final private int length;
            final MapPoint position;
            final MazePathSearchState previous;
        };

        class RouteSolution implements Comparable<RouteSolution> {
            private Character endpoint;

            @Override
            public String toString() {
                return "RouteSolution [endpoint=" + endpoint + ", length=" + length + ", keys="
                        + keys + ", order=" + getKeyOrder() + "]";
            }

            private Set<Character> keys;
            private RouteSolution previous;
            private int length;

            public RouteSolution(Character endpoint, Set<Character> keys, RouteSolution previous,
                    int length) {
                this.endpoint = endpoint;
                this.keys = keys;
                this.previous = previous;
                this.length = length;

                var remainingKeys = new HashSet<Character>(allKeys);
                remainingKeys.removeAll(keys);
            }

            @Override
            public int compareTo(RouteSolution o) {
                return Integer.compare(length, o.length);
            }

            public boolean canOpenAllDoors(HashSet<Character> doors) {
                for (Character door : doors) {
                    if (!keys.contains(doorToKey(door)))
                        return false;
                }
                return true;
            }

            public ArrayList<Character> getKeyOrder() {
                var order = new ArrayList<Character>();
                var step = this;
                while (step != null) {
                    order.add(0, step.endpoint);
                    step = step.previous;
                }
                return order;
            }
        }

        static class MultiRouteSolution implements Comparable<MultiRouteSolution> {
            private Set<Character> keys;
            private int length;
            private List<Character> endpoints;

            public MultiRouteSolution(List<Character> endpoints, Set<Character> keys,
                    MultiRouteSolution previous, int length, HashSet<Character> allKeys) {
                this.endpoints = endpoints;
                this.keys = keys;
                this.length = length;

                var remainingKeys = new HashSet<Character>(allKeys);
                remainingKeys.removeAll(keys);
            }

            @Override
            public int compareTo(MultiRouteSolution o) {
                return Integer.compare(length, o.length);
            }

            public boolean canOpenAllDoors(HashSet<Character> doors) {
                for (Character door : doors) {
                    if (!keys.contains(doorToKey(door)))
                        return false;
                }
                return true;
            }

            public Endpoint toEndpoint() {
                return new Endpoint(endpoints, keys);
            }
        }

        static int solve(List<Map> maps) {
            var allKeys = new HashSet<Character>();
            maps.forEach(
                    m -> m.locations.keySet().stream().filter(Map::isKey).forEach(allKeys::add));
            var start = new MultiRouteSolution(Arrays.asList('@', '@', '@', '@'),
                    new TreeSet<Character>(), null, 0, allKeys);
            var frontier = new PriorityQueue<MultiRouteSolution>();
            frontier.add(start);

            // states to not repeat are four positions plus a set of keys
            java.util.Map<Endpoint, Integer> explored = new HashMap<>();
            explored.put(start.toEndpoint(), 0);

            while (!frontier.isEmpty()) {
                var candidate = frontier.poll();

                // done
                if (candidate.keys.equals(allKeys)) {
                    return candidate.length;
                }

                // find next states
                for (Character key : allKeys) {
                    // already have this key
                    if (candidate.keys.contains(key))
                        continue;

                    for (int i = 0; i < maps.size(); ++i) {
                        Map mapContainingKey = maps.get(i);
                        if (!mapContainingKey.locations.containsKey(key))
                            continue;

                        var distanceAndDoors = mapContainingKey.distances
                                .get(Pair.of(candidate.endpoints.get(i), key));
                        if (candidate.canOpenAllDoors(distanceAndDoors.getRight())) {
                            var newCandidateKeys = new TreeSet<Character>(candidate.keys);
                            newCandidateKeys.add(key);

                            int newDistance = candidate.length + distanceAndDoors.getLeft();
                            var newEndpoints = new ArrayList<Character>(candidate.endpoints);
                            newEndpoints.set(i, key);
                            var next = new MultiRouteSolution(newEndpoints, newCandidateKeys,
                                    candidate, newDistance, allKeys);
                            if (!explored.containsKey(next.toEndpoint())
                                    || explored.get(next.toEndpoint()) > newDistance) {
                                explored.put(next.toEndpoint(), newDistance);
                                frontier.add(next);
                            }
                        }
                    }
                }
            }
            return 0;
        }

        int solve() {
            Set<Character> allKeys = locations.keySet().stream().filter(Map::isKey)
                    .collect(Collectors.toSet());
            var start = new RouteSolution('@', new TreeSet<Character>(), null, 0);
            var frontier = new PriorityQueue<RouteSolution>();
            frontier.add(start);

            // states to not repeat are position plus a set of keys
            java.util.Map<Pair<Character, Set<Character>>, Integer> explored = new HashMap<>();
            explored.put(Pair.of(start.endpoint, start.keys), 0);

            while (!frontier.isEmpty()) {
                var candidate = frontier.poll();

                // done
                if (candidate.keys.equals(allKeys)) {
                    return candidate.length;
                }

                // find next states
                for (Character key : allKeys) {

                    // already have this key
                    if (candidate.keys.contains(key))
                        continue;

                    var distanceAndDoors = distances.get(Pair.of(candidate.endpoint, key));
                    if (candidate.canOpenAllDoors(distanceAndDoors.getRight())) {
                        var newCandidateKeys = new TreeSet<Character>(candidate.keys);
                        newCandidateKeys.add(key);

                        int newDistance = candidate.length + distanceAndDoors.getLeft();
                        var positionAndKeys = Pair.of(key, newCandidateKeys);
                        if (!explored.containsKey(positionAndKeys)
                                || explored.get(positionAndKeys) > newDistance) {
                            explored.put(Pair.of(key, newCandidateKeys), newDistance);
                            frontier.add(new RouteSolution(key, newCandidateKeys, candidate,
                                    newDistance));
                        }
                    }
                }
            }
            return 0;
        }

        static public Character doorToKey(Character door) {
            return Character.toLowerCase(door);
        }

    }

    public static void main(String[] args) {
        try {
            String[] mapLines = new String(
                    Day18.class.getResourceAsStream("/day18.txt").readAllBytes()).split("\n");
            Map map = Map.parse(mapLines);
            int length = map.solve();
            System.out.println("Day 18 part 1: " + length);
            List<Map> maps = Map.divide(mapLines).stream().map(Map::parse)
                    .collect(Collectors.toList());
            length = Map.solve(maps);
            System.out.println("Day 18 part 2: " + length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
