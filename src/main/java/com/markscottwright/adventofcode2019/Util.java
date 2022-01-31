package com.markscottwright.adventofcode2019;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Util {

    public static String[] getInputLines(String inputName) {
        return getInputString(inputName).split("\n");
    }

    public static String getInputString(String inputName) {
        ByteArrayOutputStream input = new ByteArrayOutputStream();
        try {
            Util.class.getResourceAsStream("/" + inputName).transferTo(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(input.toByteArray());
    }

	public static void reverseInPlace(int[] integers) {
		for (int i=0; i < integers.length/2; ++i) {
			int t = integers[i];
			integers[i] = integers[integers.length-i-1];
			integers[integers.length-i-1] = t;
		}
	}

	public static void reverseInPlace(long[] numbers) {
		for (int i=0; i < numbers.length/2; ++i) {
			long t = numbers[i];
			numbers[i] = numbers[numbers.length-i-1];
			numbers[numbers.length-i-1] = t;
		}
	}

}
