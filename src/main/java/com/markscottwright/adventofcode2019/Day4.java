package com.markscottwright.adventofcode2019;

public class Day4 {
    public static void main(String[] args) {

        int part1Solution = 0;
        for (int i = 197487; i < 673251; ++i) {
            var digits = digits(i);
            if (hasDouble(digits) && doesntDecrease(digits))
                part1Solution++;
        }
        System.out.println("Part 1: " + part1Solution);

        int part2Solution = 0;
        for (int i = 197487; i < 673251; ++i) {
            var digits = digits(i);
            if (hasDoubleNotPartOfLargerGroup(digits) && doesntDecrease(digits))
                part2Solution++;
        }
        System.out.println("Part 2: " + part2Solution);
    }

    private static boolean hasDoubleNotPartOfLargerGroup(int[] digits) {
        for (int i = 1; i < digits.length; ++i)
            if (digits[i] == digits[i - 1]
                    && !(i > 1 && (digits[i] == digits[i - 2]))
                    && !(i < 5 && (digits[i] == digits[i + 1])))
                return true;
        return false;

    }

    static int[] digits(int v) {
        int[] out = new int[6];
        out[0] = v / 100_000;
        v = v % 100_000;
        out[1] = v / 10_000;
        v = v % 10_000;
        out[2] = v / 1_000;
        v = v % 1_000;
        out[3] = v / 100;
        v = v % 100;
        out[4] = v / 10;
        v = v % 10;
        out[5] = v;
        return out;
    }

    private static boolean hasDouble(int[] digits) {
        for (int i = 1; i < digits.length; ++i)
            if (digits[i] == digits[i - 1])
                return true;
        return false;
    }

    private static boolean doesntDecrease(int[] digits) {
        for (int i = 1; i < digits.length; ++i) {
            if (digits[i] < digits[i - 1])
                return false;
        }
        return true;
    }
}
