package com.markscottwright.adventofcode2019;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class Day17Test {

	@Test
	public void testPathBuilding() {
		var path = Arrays.asList("R,8,R,8,R,4,R,4,R,8,L,6,L,2,R,4,R,4,R,8,R,8,R,8,L,6,L,2".split(","));
		String[] instructions = Day17.build(path, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		System.out.println(Arrays.toString(instructions));
		Assert.assertNotNull(instructions);
	}

}
