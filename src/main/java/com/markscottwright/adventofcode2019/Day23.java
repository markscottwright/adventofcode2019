package com.markscottwright.adventofcode2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;
import com.markscottwright.adventofcode2019.intcode.IntcodeOutput;

public class Day23 {
	static class NAT {
		private Long x;
		private Long y;
		HashSet<Long> yValuesSent = new HashSet<>();
		private Long duplicateY = null;
		

		public void setInput(long x, long y) {
//			System.out.println("NAT received: " + x + "," + y);
			this.x = x;
			this.y = y;
		}

		public boolean isDuplicateYSeen() {
			return duplicateY != null;
		}

		public long getDuplicateY() {
			return duplicateY;
		}

		public void send(NIC nic) {
//			System.out.println("NAT sending: " + x + "," + y);
			if (duplicateY == null && yValuesSent.contains(y))
				duplicateY = y;
			yValuesSent.add(y);
			nic.addInput(x, y);
			x = y = null;
		}

		public boolean hasValue() {
			return (x != null && y != null);
		}
	}

	static class NIC implements Iterator<Long>, IntcodeOutput {
		private IntcodeComputer nicCode;
		private Queue<Pair<Long, Long>> pendingPackets = new Queue<>();
		private Map<Integer, NIC> nics;
		int pendingDestination;
		long pendingX;
		long pendingY;
		OutputState outputState = OutputState.WAITING_FOR_DESTINATION;
		InputState inputState = InputState.WAITING_FOR_ADDRESS;
		Optional<Long> valueSentTo255 = Optional.empty();
		private int address;
		private Optional<NAT> nat = Optional.empty();
		private boolean failingToReceive = false;

		enum OutputState {
			WAITING_FOR_DESTINATION, WAITING_FOR_X, WAITING_FOR_Y
		}

		enum InputState {
			WAITING_FOR_ADDRESS, RECEIVING_X, RECEIVING_Y
		}

		public NIC(int address) {
			this.address = address;
			nicCode = new IntcodeComputer(IntcodeComputer.parseResource("/day23.txt"), this, this);
		}

		public NIC(int address, NAT nat) {
			this(address);
			this.nat = Optional.of(nat);
		}

		boolean runOneInstruction() {
			try {
				nicCode.runOneInstruction();
				return valueSentTo255.isPresent();
			} catch (IntcodeException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public void put(long aVal) {
			if (outputState == OutputState.WAITING_FOR_DESTINATION) {
				pendingDestination = (int) aVal;
				outputState = OutputState.WAITING_FOR_X;
			} else if (outputState == OutputState.WAITING_FOR_X) {
				pendingX = aVal;
				outputState = OutputState.WAITING_FOR_Y;
			} else {
				pendingY = aVal;
				outputState = OutputState.WAITING_FOR_DESTINATION;
//				System.out.println(
//						"NIC " + address + " sending " + pendingX + "," + pendingY + " to " + pendingDestination);
				if (pendingDestination == 255) {
					valueSentTo255 = Optional.of(pendingY);
					nat.ifPresent(n -> {
						n.setInput(pendingX, pendingY);
					});
				} else {
					nics.get(pendingDestination).addInput(pendingX, pendingY);
				}
			}
		}

		private void addInput(long x, long y) {
			pendingPackets.add(Pair.of(x, y));
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Long next() {
			failingToReceive = false;
			if (inputState == InputState.WAITING_FOR_ADDRESS) {
				inputState = InputState.RECEIVING_X;
				return (long) address;
			} else if (pendingPackets.isEmpty()) {
				failingToReceive = true;
				return -1L;
			} else if (inputState == InputState.RECEIVING_X) {
				inputState = InputState.RECEIVING_Y;
				return pendingPackets.peek().getLeft();
			} else {
				assert inputState == InputState.RECEIVING_Y;
				inputState = InputState.RECEIVING_X;
				return pendingPackets.pop().getRight();
			}
		}

		public Map<Integer, NIC> getNics() {
			return nics;
		}

		public void setNics(Map<Integer, NIC> nics) {
			this.nics = nics;
		}

		public static boolean allFailingToReceive(HashMap<Integer, NIC> network) {
			return network.values().stream().allMatch(n -> n.failingToReceive);
		}

	}

	public static void main(String[] args) {
		{
			long part1 = 0;
			var network = new HashMap<Integer, NIC>();
			for (int i = 0; i < 50; ++i) {
				network.put(i, new NIC(i));
			}
			for (int i = 0; i < 50; ++i) {
				network.get(i).setNics(network);
			}
			outer: while (true) {
				for (int i = 0; i < 50; ++i) {
					if (network.get(i).runOneInstruction()) {
						part1 = network.get(i).valueSentTo255.get();
						break outer;
					}
				}
			}

			System.out.println("Day 23 part 1: " + part1);
		}

		{
			long part2 = 0;
			var network = new HashMap<Integer, NIC>();
			var nat = new NAT();
			for (int i = 0; i < 50; ++i) {
				network.put(i, new NIC(i, nat));
			}
			for (int i = 0; i < 50; ++i) {
				network.get(i).setNics(network);
			}
			outer: while (true) {
				for (int i = 0; i < 50; ++i) {
					network.get(i).runOneInstruction();
				}
				if (nat.hasValue() && NIC.allFailingToReceive(network)) {
					nat.send(network.get(0));
				}
				if (nat.isDuplicateYSeen()) {
					part2 = nat.getDuplicateY();
					break outer;
				}
			}

			System.out.println("Day 23 part 2: " + part2);
		}
	}
}
