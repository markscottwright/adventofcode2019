package com.markscottwright.adventofcode2019;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markscottwright.adventofcode2019.Day18.Map;

public class Day18Test {

	@Test
	public void testSample1() {
		String sample1 = """
				#########
				#b.A.@.a#
				#########""";
		
		int sample1Solution = Map.parse(sample1.split("\n")).solve();
		assertEquals(8, sample1Solution);
	}

	@Test
	public void testSample2() {
		String sample = """
				########################
				#f.D.E.e.C.b.A.@.a.B.c.#
				######################.#
				#d.....................#
				########################""";
		
		int sample1Solution = Map.parse(sample.split("\n")).solve();
		assertEquals(86, sample1Solution);
		
	}
	@Test
	public void testSample3() {
		String sample = """
				########################
				#...............b.C.D.f#
				#.######################
				#.....@.a.B.c.d.A.e.F.g#
				########################""";
		
		Map map = Map.parse(sample.split("\n"));
		map.distances.forEach((fromTo, distanceDoors) -> {
			System.out.println(fromTo + ":" + distanceDoors);
		});
		int sample1Solution = map.solve();
		assertEquals(132, sample1Solution);
	}

}
