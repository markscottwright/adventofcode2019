package com.markscottwright.adventofcode2019;

import static org.apache.commons.lang3.StringUtils.join;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.markscottwright.adventofcode2019.intcode.EmptyIntcodeComputerInput;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;
import com.markscottwright.adventofcode2019.intcode.IntcodeOutput;

public class Day17 {

	public final static String INPUT = "1,330,331,332,109,4278,1101,0,1182,16,1102,1485,1,24,102,1,0,570,1006,570,36,102,1,571,0,1001,570,-1,570,1001,24,1,24,1105,1,18,1008,571,0,571,1001,16,1,16,1008,16,1485,570,1006,570,14,21102,1,58,0,1105,1,786,1006,332,62,99,21101,0,333,1,21101,0,73,0,1106,0,579,1101,0,0,572,1102,1,0,573,3,574,101,1,573,573,1007,574,65,570,1005,570,151,107,67,574,570,1005,570,151,1001,574,-64,574,1002,574,-1,574,1001,572,1,572,1007,572,11,570,1006,570,165,101,1182,572,127,1002,574,1,0,3,574,101,1,573,573,1008,574,10,570,1005,570,189,1008,574,44,570,1006,570,158,1105,1,81,21102,340,1,1,1105,1,177,21102,477,1,1,1106,0,177,21102,514,1,1,21101,0,176,0,1105,1,579,99,21102,184,1,0,1105,1,579,4,574,104,10,99,1007,573,22,570,1006,570,165,1002,572,1,1182,21101,0,375,1,21102,1,211,0,1105,1,579,21101,1182,11,1,21101,222,0,0,1106,0,979,21102,1,388,1,21101,0,233,0,1105,1,579,21101,1182,22,1,21101,244,0,0,1106,0,979,21101,401,0,1,21102,1,255,0,1105,1,579,21101,1182,33,1,21101,0,266,0,1106,0,979,21101,414,0,1,21101,277,0,0,1106,0,579,3,575,1008,575,89,570,1008,575,121,575,1,575,570,575,3,574,1008,574,10,570,1006,570,291,104,10,21101,0,1182,1,21102,1,313,0,1105,1,622,1005,575,327,1101,0,1,575,21102,1,327,0,1106,0,786,4,438,99,0,1,1,6,77,97,105,110,58,10,33,10,69,120,112,101,99,116,101,100,32,102,117,110,99,116,105,111,110,32,110,97,109,101,32,98,117,116,32,103,111,116,58,32,0,12,70,117,110,99,116,105,111,110,32,65,58,10,12,70,117,110,99,116,105,111,110,32,66,58,10,12,70,117,110,99,116,105,111,110,32,67,58,10,23,67,111,110,116,105,110,117,111,117,115,32,118,105,100,101,111,32,102,101,101,100,63,10,0,37,10,69,120,112,101,99,116,101,100,32,82,44,32,76,44,32,111,114,32,100,105,115,116,97,110,99,101,32,98,117,116,32,103,111,116,58,32,36,10,69,120,112,101,99,116,101,100,32,99,111,109,109,97,32,111,114,32,110,101,119,108,105,110,101,32,98,117,116,32,103,111,116,58,32,43,10,68,101,102,105,110,105,116,105,111,110,115,32,109,97,121,32,98,101,32,97,116,32,109,111,115,116,32,50,48,32,99,104,97,114,97,99,116,101,114,115,33,10,94,62,118,60,0,1,0,-1,-1,0,1,0,0,0,0,0,0,1,24,26,0,109,4,1201,-3,0,587,20102,1,0,-1,22101,1,-3,-3,21102,1,0,-2,2208,-2,-1,570,1005,570,617,2201,-3,-2,609,4,0,21201,-2,1,-2,1105,1,597,109,-4,2105,1,0,109,5,2102,1,-4,629,21001,0,0,-2,22101,1,-4,-4,21101,0,0,-3,2208,-3,-2,570,1005,570,781,2201,-4,-3,653,20102,1,0,-1,1208,-1,-4,570,1005,570,709,1208,-1,-5,570,1005,570,734,1207,-1,0,570,1005,570,759,1206,-1,774,1001,578,562,684,1,0,576,576,1001,578,566,692,1,0,577,577,21102,1,702,0,1106,0,786,21201,-1,-1,-1,1105,1,676,1001,578,1,578,1008,578,4,570,1006,570,724,1001,578,-4,578,21102,731,1,0,1106,0,786,1105,1,774,1001,578,-1,578,1008,578,-1,570,1006,570,749,1001,578,4,578,21101,0,756,0,1106,0,786,1105,1,774,21202,-1,-11,1,22101,1182,1,1,21101,774,0,0,1106,0,622,21201,-3,1,-3,1105,1,640,109,-5,2106,0,0,109,7,1005,575,802,21002,576,1,-6,21001,577,0,-5,1106,0,814,21101,0,0,-1,21102,0,1,-5,21102,1,0,-6,20208,-6,576,-2,208,-5,577,570,22002,570,-2,-2,21202,-5,57,-3,22201,-6,-3,-3,22101,1485,-3,-3,1202,-3,1,843,1005,0,863,21202,-2,42,-4,22101,46,-4,-4,1206,-2,924,21102,1,1,-1,1105,1,924,1205,-2,873,21101,35,0,-4,1105,1,924,2101,0,-3,878,1008,0,1,570,1006,570,916,1001,374,1,374,1202,-3,1,895,1101,0,2,0,2101,0,-3,902,1001,438,0,438,2202,-6,-5,570,1,570,374,570,1,570,438,438,1001,578,558,922,20101,0,0,-4,1006,575,959,204,-4,22101,1,-6,-6,1208,-6,57,570,1006,570,814,104,10,22101,1,-5,-5,1208,-5,49,570,1006,570,810,104,10,1206,-1,974,99,1206,-1,974,1101,1,0,575,21101,973,0,0,1106,0,786,99,109,-7,2105,1,0,109,6,21102,1,0,-4,21102,0,1,-3,203,-2,22101,1,-3,-3,21208,-2,82,-1,1205,-1,1030,21208,-2,76,-1,1205,-1,1037,21207,-2,48,-1,1205,-1,1124,22107,57,-2,-1,1205,-1,1124,21201,-2,-48,-2,1105,1,1041,21102,-4,1,-2,1106,0,1041,21102,1,-5,-2,21201,-4,1,-4,21207,-4,11,-1,1206,-1,1138,2201,-5,-4,1059,1202,-2,1,0,203,-2,22101,1,-3,-3,21207,-2,48,-1,1205,-1,1107,22107,57,-2,-1,1205,-1,1107,21201,-2,-48,-2,2201,-5,-4,1090,20102,10,0,-1,22201,-2,-1,-2,2201,-5,-4,1103,1202,-2,1,0,1106,0,1060,21208,-2,10,-1,1205,-1,1162,21208,-2,44,-1,1206,-1,1131,1105,1,989,21102,1,439,1,1106,0,1150,21102,1,477,1,1105,1,1150,21102,1,514,1,21102,1,1149,0,1106,0,579,99,21101,0,1157,0,1106,0,579,204,-2,104,10,99,21207,-3,22,-1,1206,-1,1138,1202,-5,1,1176,2101,0,-4,0,109,-6,2106,0,0,14,11,46,1,9,1,46,1,9,1,46,1,9,1,46,1,9,1,46,1,9,1,46,1,9,1,46,1,9,1,44,13,44,1,1,1,54,1,1,1,54,1,1,1,54,1,1,9,46,1,9,1,46,1,9,1,46,1,9,1,46,1,9,1,11,13,22,1,9,1,11,1,11,1,22,1,9,1,11,1,9,11,14,1,9,1,11,1,9,1,1,1,7,1,14,9,1,1,11,1,9,1,1,1,7,1,22,1,1,1,11,1,9,1,1,1,7,1,22,1,1,1,11,1,9,1,1,1,7,1,22,1,1,1,11,1,9,1,1,1,7,1,22,1,1,13,9,1,1,1,7,1,22,1,23,1,1,1,7,1,14,13,19,13,12,1,7,1,25,1,7,1,1,1,12,1,7,1,25,9,1,1,12,1,7,1,35,1,12,1,7,1,35,1,12,1,7,1,35,1,8,13,35,1,8,1,3,1,43,14,43,2,7,1,47,2,7,1,37,9,1,2,7,1,37,1,7,1,1,2,7,1,35,14,7,1,35,1,1,1,7,1,2,1,7,1,35,1,1,1,7,1,2,1,7,1,35,1,1,1,7,1,2,1,7,1,35,1,1,1,7,1,2,1,7,1,35,1,1,1,7,1,2,9,35,1,1,1,7,1,46,1,1,1,7,1,46,11,48,1,44,13,10";

	static class ScaffoldingMapBuilder implements IntcodeOutput {
		enum Direction {
			UP, RIGHT, DOWN, LEFT;

			static Direction from(char aVal) {
				switch (aVal) {
				case 'v':
					return DOWN;
				case '^':
					return UP;
				case '>':
					return RIGHT;
				default:
					return LEFT;
				}
			}

			Direction turnLeft() {
				switch (this) {
				case DOWN:
					return RIGHT;
				case LEFT:
					return DOWN;
				case RIGHT:
					return UP;
				case UP:
				default:
					return LEFT;
				}
			}

			Direction turnRight() {
				switch (this) {
				case DOWN:
					return LEFT;
				case LEFT:
					return UP;
				case RIGHT:
					return DOWN;
				case UP:
				default:
					return RIGHT;
				}
			}
		};

		int row = 0;
		int col = 0;

		HashSet<Point> scaffoldingPoints = new HashSet<>();
		Point initialRobotPosition = null;
		StringBuilder scaffoldingMap = new StringBuilder();
		private Direction initialRobotDirection;

		@Override
		public void put(long aVal) {
			scaffoldingMap.append((char) aVal);

			switch ((char) aVal) {
			case '.':
				col++;
				break;

			case '\n':
				col = 0;
				row++;
				break;

			case '^':
			case '>':
			case '<':
			case 'v':
				initialRobotDirection = Direction.from((char) aVal);
				initialRobotPosition = new Point(col, row);
				col++;
				break;

			case '#':
				scaffoldingPoints.add(new Point(col, row));
				col++;
				break;
			}
		}

		public String getMap() {
			return scaffoldingMap.toString();
		}

		public Set<Point> getIntersections() {
			return scaffoldingPoints.stream()
					.filter(p -> scaffoldingPoints.contains(p.moveUp(1)) && scaffoldingPoints.contains(p.moveDown(1))
							&& scaffoldingPoints.contains(p.moveLeft(1)) && scaffoldingPoints.contains(p.moveRight(1)))
					.collect(Collectors.toSet());
		}

		static long alignmentParameter(Point p) {
			return p.x * p.y;
		}

		ArrayList<String> getCompleteRobotPath() {
			ArrayList<String> path = new ArrayList<>();
			Point pos = initialRobotPosition;
			Direction dir = initialRobotDirection;
			while (true) {
				int distance = proceedForward(pos, dir);
				if (distance > 0) {
					path.add(Integer.toString(distance));
					pos = move(pos, dir, distance);
				} else if ((distance = proceedForward(pos, dir.turnLeft())) > 0) {
					path.add("L");
					path.add(Integer.toString(distance));
					dir = dir.turnLeft();
					pos = move(pos, dir, distance);
				} else if ((distance = proceedForward(pos, dir.turnRight())) > 0) {
					path.add("R");
					path.add(Integer.toString(distance));
					dir = dir.turnRight();
					pos = move(pos, dir, distance);
				} else {
					// no where to go but back - since this path is all one line, we're at the end
					break;
				}
			}

			return path;
		}

		private Point move(Point pos, Direction dir, int distance) {
			if (dir == Direction.DOWN) {
				return pos.moveDown(distance);
			} else if (dir == Direction.UP) {
				return pos.moveUp(distance);
			} else if (dir == Direction.RIGHT) {
				return pos.moveRight(distance);
			} else {
				assert dir == Direction.LEFT;
				return pos.moveLeft(distance);
			}
		}

		private int proceedForward(Point pos, Direction dir) {
			int count = 0;

			if (dir == Direction.DOWN)
				while (scaffoldingPoints.contains(pos.moveDown(count + 1)))
					count++;
			else if (dir == Direction.UP)
				while (scaffoldingPoints.contains(pos.moveUp(count + 1)))
					count++;
			else if (dir == Direction.RIGHT)
				while (scaffoldingPoints.contains(pos.moveRight(count + 1)))
					count++;
			else if (dir == Direction.LEFT)
				while (scaffoldingPoints.contains(pos.moveLeft(count + 1)))
					count++;

			return count;
		}
	}

	public static void main(String[] args) {
		try {
			ScaffoldingMapBuilder scaffoldingMap = new ScaffoldingMapBuilder();
			var intcodeComputer = new IntcodeComputer(IntcodeComputer.parse(INPUT), new EmptyIntcodeComputerInput(),
					scaffoldingMap);
			intcodeComputer.run();

			System.out.println(scaffoldingMap.getMap());

			long sumOfAlignmentParameters = scaffoldingMap.getIntersections().stream()
					.mapToLong(ScaffoldingMapBuilder::alignmentParameter).sum();
			System.out.println("Day 17 part 1: " + sumOfAlignmentParameters);
			System.out.println("Day 17 part 2: " + scaffoldingMap.getCompleteRobotPath());
			System.out.println(StringUtils.join(scaffoldingMap.getCompleteRobotPath(), ",").length());

			String[] movementInstructions = build(scaffoldingMap.getCompleteRobotPath(), new ArrayList<>(),
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
			System.out.println(movementInstructions);

		} catch (IntcodeException e) {
			e.printStackTrace();
		}
	}

	private static String[] build(ArrayList<String> path, ArrayList<String> mainRoutine, ArrayList<String> functionA,
			ArrayList<String> functionB, ArrayList<String> functionC) {
		//System.out.println(path);
		//System.out.println("main:" + mainRoutine);
		//System.out.println("A:   " + functionA);
		//System.out.println("B:   " + functionB);
		//System.out.println("C:   " + functionC);
		if (join(mainRoutine, ",").length() > 20 || join(functionA, ",").length() > 20
				|| join(functionB, ",").length() > 20 || join(functionC, ",").length() > 20) {
			return null;
		}

		if (startsWith(path, functionA)) {
			removeFirstN(path, functionA.size());
			mainRoutine.add("A");
			String[] results = build(path, mainRoutine, functionA, functionB, functionC);
			if (results != null)
				return results;
		}
		if (startsWith(path, functionB)) {
			removeFirstN(path, functionB.size());
			mainRoutine.add("B");
			String[] results = build(path, mainRoutine, functionA, functionB, functionC);
			if (results != null)
				return results;
			path.addAll(0, functionB);
			removeLast(mainRoutine);
		}
		if (startsWith(path, functionC)) {
			removeFirstN(path, functionC.size());
			mainRoutine.add("C");
			String[] results = build(path, mainRoutine, functionA, functionB, functionC);
			if (results != null)
				return results;
			path.addAll(0, functionC);
			removeLast(mainRoutine);
		}

		if (functionA.isEmpty()) {
			mainRoutine.add("A");
			for (int i = 0; i < 20 && !path.isEmpty(); ++i) {
				functionA.add(path.remove(0));
				String[] results = build(path, mainRoutine, functionA, functionB, functionC);
				if (results != null)
					return results;
			}
			path.addAll(0, functionA);
			functionA.clear();
			removeLast(mainRoutine);
		} else if (functionB.isEmpty()) {
			mainRoutine.add("B");
			for (int i = 0; i < 20 && !path.isEmpty(); ++i) {
				functionB.add(path.remove(0));
				String[] results = build(path, mainRoutine, functionA, functionB, functionC);
				if (results != null)
					return results;
			}
			path.addAll(0, functionB);
			functionB.clear();
			removeLast(mainRoutine);
		} else {
			mainRoutine.add("C");
			for (int i = 0; i < 20 && !path.isEmpty(); ++i) {
				functionC.add(path.remove(0));
				String[] results = build(path, mainRoutine, functionA, functionB, functionC);
				if (results != null)
					return results;
			}
			path.addAll(0, functionC);
			functionC.clear();
			removeLast(mainRoutine);
		}

		return null;
	}

	private static void removeLast(ArrayList<String> list) {
		list.remove(list.size() - 1);
	}

	private static void removeFirstN(ArrayList<String> path, int size) {
		for (int i = 0; i < size; ++i)
			path.remove(0);
	}

	private static boolean startsWith(ArrayList<String> list, ArrayList<String> possiblePrefix) {
		if (possiblePrefix.size() == 0)
			return false;

		for (int i = 0; i < possiblePrefix.size(); i++) {
			if (!list.get(i).equals(possiblePrefix.get(i)))
				return false;
		}

		return true;
	}

}
