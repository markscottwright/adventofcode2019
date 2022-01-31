package com.markscottwright.adventofcode2019;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UtilTest {

	@Test
	public void testReverseInPlace() {
		int[] array1 = new int[] { 0, 1, 2, 3, 4 };
		Util.reverseInPlace(array1);
		Assertions.assertThat(array1).containsExactly(new int[] { 4, 3, 2, 1, 0 });
		int[] array2 = new int[] { 0, 1, 2, 3, 4, 5 };
		Util.reverseInPlace(array2);
		Assertions.assertThat(array2).containsExactly(new int[] { 5, 4, 3, 2, 1, 0 });
	}

}
