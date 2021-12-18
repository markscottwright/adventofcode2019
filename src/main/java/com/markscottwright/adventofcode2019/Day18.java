package com.markscottwright.adventofcode2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

public class Day18 {

	static class Queue<T> extends LinkedList<T> {
		public T pop() {
			return poll();
		}

		public void push(T elem) {
			offer(elem);
		}
	}

	static class Map {

		static class MapPoint extends Point {
			public MapPoint(int x, int y) {
				super(x, y);
			}

			public MapPoint[] getNeighbors() {
				// no diagonals
				return new MapPoint[] { new MapPoint(x - 1, y), new MapPoint(x + 1, y), new MapPoint(x, y - 1),
						new MapPoint(x, y + 1)

				};
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
			this.allKeys = locations.keySet().stream().filter(Map::isKey).collect(Collectors.toSet());
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
				for (Character end : locations.keySet()) {
					if (start == end || isDoor(start) || isDoor(end))
						continue;

					MapPoint endPosition = locations.get(end);
					var visited = new HashSet<MapPoint>();
					Queue<MazePathSearchState> frontier = new Queue<>();
					frontier.add(new MazePathSearchState(locations.get(start), null));

					while (!frontier.isEmpty()) {
						var candidate = frontier.pop();
						if (endPosition.equals(candidate.position)) {
							distances.put(Pair.of(start, end),
									Pair.of(candidate.length, candidate.doorsPassed(doorLocations)));
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
				return "RouteSolution [endpoint=" + endpoint + ", length=" + length + ", remainingLength="
						+ remainingLength + ", keys=" + keys + ", order=" + getKeyOrder() + "]";
			}

			private Set<Character> keys;
			private RouteSolution previous;
			private int length;
			private int remainingLength;

			public RouteSolution(Character endpoint, Set<Character> keys, RouteSolution previous, int length) {
				this.endpoint = endpoint;
				this.keys = keys;
				this.previous = previous;
				this.length = length;

				var remainingKeys = new HashSet<Character>(allKeys);
				remainingKeys.removeAll(keys);
				this.remainingLength = 0; // worstRouteIncluding(endpoint, remainingKeys);
			}

			@Override
			public int compareTo(RouteSolution o) {
				return Integer.compare(remainingLength + length, o.remainingLength + o.length);
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

		int solve() {
			Set<Character> allKeys = locations.keySet().stream().filter(Map::isKey).collect(Collectors.toSet());
			var start = new RouteSolution('@', new TreeSet<Character>(), null, 0);
			var frontier = new PriorityQueue<RouteSolution>();
			frontier.add(start);

			java.util.Map<Pair<Character, Set<Character>>, Integer> explored = new HashMap<>();
			explored.put(Pair.of(start.endpoint, start.keys), 0);

			while (!frontier.isEmpty()) {
				var candidate = frontier.poll();
				//System.out.println(candidate);

				// done
				if (candidate.keys.equals(allKeys)) {
					var solutionLength = candidate.length;
//					var solution = new ArrayList<RouteSolution>();
//					while (candidate != null) {
//						solution.add(candidate);
//						candidate = candidate.previous;
//					}
//					Collections.reverse(solution);
//					solution.forEach(System.out::println);
					return solutionLength;
				}

				// find next states
				for (Character key : allKeys) {
					if (candidate.keys.contains(key))
						continue;

					var distanceAndDoors = distances.get(Pair.of(candidate.endpoint, key));
					if (candidate.canOpenAllDoors(distanceAndDoors.getRight())) {
						TreeSet<Character> newCandidateKeys = new TreeSet<>(candidate.keys);
						newCandidateKeys.add(key);

						int newDistance = candidate.length + distanceAndDoors.getLeft();
						var positionAndKeys = Pair.of(key, newCandidateKeys);
						if (!explored.containsKey(positionAndKeys) || explored.get(positionAndKeys) > newDistance) {
							explored.put(Pair.of(key, newCandidateKeys), newDistance);
							frontier.add(new RouteSolution(key, newCandidateKeys, candidate, newDistance));
						}
					}
				}
			}
			return 0;
		}

		public Character doorToKey(Character door) {
			return Character.toLowerCase(door);
		}

	}

	public static void main(String[] args) {
		try {
			Map map = Map.parse(new String(Day18.class.getResourceAsStream("/day18.txt").readAllBytes()).split("\n"));
			int length = map.solve();
			System.out.println("Day 18 part 1: " + length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
