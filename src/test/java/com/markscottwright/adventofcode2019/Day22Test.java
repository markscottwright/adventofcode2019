package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import com.markscottwright.adventofcode2019.Day22.Cut;
import com.markscottwright.adventofcode2019.Day22.DealIntoNewStack;
import com.markscottwright.adventofcode2019.Day22.DealWithIncrement;
import com.markscottwright.adventofcode2019.Day22.Deck;
import com.markscottwright.adventofcode2019.Day22.DeckPosition;
import com.markscottwright.adventofcode2019.Day22.Instruction;

public class Day22Test {

	@Test
	public void testSample1() {
		//@formatter:off
		String instructionsString = "deal with increment 7\r\n"
				+ "deal into new stack\r\n"
				+ "deal into new stack";
		//@formatter:on
		var instructions = Arrays.stream(instructionsString.split("\r\n")).map(Day22.Instruction::parse)
				.collect(Collectors.toList());
		assertThat(new Deck(10).apply(instructions).cards).containsExactly(0, 3, 6, 9, 2, 5, 8, 1, 4, 7);
	}

	@Test
	public void testSample2() {
		//@formatter:off
		String instructionsString ="cut 6\r\n"
				+ "deal with increment 7\r\n"
				+ "deal into new stack";
		//@formatter:on
		var instructions = Arrays.stream(instructionsString.split("\r\n")).map(Day22.Instruction::parse)
				.collect(Collectors.toList());
		assertThat(new Deck(10).apply(instructions).cards).containsExactly(3, 0, 7, 4, 1, 8, 5, 2, 9, 6);
	}

	@Test
	public void testSample3() {
		//@formatter:off
		String instructionsString ="deal with increment 7\r\n"
				+ "deal with increment 9\r\n"
				+ "cut -2";
		//@formatter:on
		var instructions = Arrays.stream(instructionsString.split("\r\n")).map(Day22.Instruction::parse)
				.collect(Collectors.toList());
		assertThat(new Deck(10).apply(instructions).cards).containsExactly(6, 3, 0, 7, 4, 1, 8, 5, 2, 9);
	}

	@Test
	public void testSample4() {
		//@formatter:off
		String instructionsString ="deal into new stack\r\n"
				+ "cut -2\r\n"
				+ "deal with increment 7\r\n"
				+ "cut 8\r\n"
				+ "cut -4\r\n"
				+ "deal with increment 7\r\n"
				+ "cut 3\r\n"
				+ "deal with increment 9\r\n"
				+ "deal with increment 3\r\n"
				+ "cut -1";
		//@formatter:on
		var instructions = Arrays.stream(instructionsString.split("\r\n")).map(Day22.Instruction::parse)
				.collect(Collectors.toList());
		assertThat(new Deck(10).apply(instructions).cards).containsExactly(9, 2, 5, 8, 1, 4, 7, 0, 3, 6);
	}
	
	@Test
	public void testPositions() throws Exception {
		assertThat(new DeckPosition(0, 5).reverseApply(new DealIntoNewStack()).position).isEqualTo(4);
		assertThat(new DeckPosition(0, 5).reverseApply(new Cut(1)).position).isEqualTo(1);
		assertThat(new DeckPosition(0, 5).reverseApply(new Cut(-1)).position).isEqualTo(4);
		assertThat(new DeckPosition(0, 5).reverseApply(new Cut(2)).position).isEqualTo(2);
		assertThat(new DeckPosition(4, 5).reverseApply(new Cut(2)).position).isEqualTo(1);
		assertThat(new DeckPosition(0, 5).reverseApply(new Cut(-2)).position).isEqualTo(3);
		assertThat(new DeckPosition(4, 5).reverseApply(new Cut(-2)).position).isEqualTo(2);
		assertThat(new DeckPosition(0, 5).reverseApply(new Day22.DealWithIncrement(2)).position).isEqualTo(0);
		assertThat(new DeckPosition(1, 5).reverseApply(new Day22.DealWithIncrement(2)).position).isEqualTo(3);
		assertThat(new DeckPosition(2, 5).reverseApply(new Day22.DealWithIncrement(2)).position).isEqualTo(1);
		assertThat(new DeckPosition(3, 5).reverseApply(new Day22.DealWithIncrement(2)).position).isEqualTo(4);
		assertThat(new DeckPosition(4, 5).reverseApply(new Day22.DealWithIncrement(2)).position).isEqualTo(2);
	}
}
