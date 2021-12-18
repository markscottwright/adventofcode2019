package com.markscottwright.adventofcode2019;

import java.io.StringWriter;
import java.util.Iterator;

public class Day16 {

    // well, this really makes the case for streams...

    static class BasePattern implements Iterator<Integer> {
        int pos = 0;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            int out = pos == 0 ? 0 : pos == 1 ? 1 : pos == 2 ? 0 : -1;
            pos = (pos + 1) % 4;
            return out;
        }
    }

    static class RepeatingPattern implements Iterator<Integer> {
        final Iterator<Integer> source;
        final int digitNum;
        private int repeat;
        Integer curVal;

        public RepeatingPattern(Iterator<Integer> source, int digitNum) {
            this.source = source;
            this.digitNum = digitNum;
            this.repeat = 0;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            if (repeat == 0)
                curVal = source.next();
            repeat = (repeat + 1) % digitNum;
            return curVal;
        }
    }

    static class DropFirstItem implements Iterator<Integer> {

        final Iterator<Integer> source;
        boolean first = true;

        public DropFirstItem(Iterator<Integer> source) {
            this.source = source;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            if (first) {
                source.next();
                first = false;
            }
            return source.next();
        }
    }

    static int[] applyPattern(int offset, int[] digits) {

        int[] out = new int[digits.length];
        for (int outDigitNum = 0; outDigitNum < digits.length; ++outDigitNum) {
            var pattern = new DropFirstItem(new RepeatingPattern(
                    new BasePattern(), outDigitNum + 1 + offset));
            int outDigitVal = 0;
            for (int d : digits) {
                outDigitVal += d * pattern.next();
            }
            out[outDigitNum] = Math.abs(outDigitVal) % 10;
        }
        return out;
    }

    public static void main(String[] args) {
        final var input = "59773419794631560412886746550049210714854107066028081032096591759575145680294995770741204955183395640103527371801225795364363411455113236683168088750631442993123053909358252440339859092431844641600092736006758954422097244486920945182483159023820538645717611051770509314159895220529097322723261391627686997403783043710213655074108451646685558064317469095295303320622883691266307865809481566214524686422834824930414730886697237161697731339757655485312568793531202988525963494119232351266908405705634244498096660057021101738706453735025060225814133166491989584616948876879383198021336484629381888934600383957019607807995278899293254143523702000576897358";
        int[] digits = input.chars().map(c -> c - '0').toArray();
        System.out.print("Day 16 part 1: ");
        System.out.println(toString(first8(applyPattern(0, digits, 100))));


        // Shoot - I solved this *somewhere* already
        // OK - I think the key here is that the pattern repeats 10,000 / (4*digit num).
//        int messageLocation = Integer.parseInt(input.substring(0, 7));
//        StringWriter input2 = new StringWriter(input.length() * 10000);
//        for (int i = 0; i < 10000; ++i)
//            input2.append(input);
//        digits = input2.toString().chars().skip(messageLocation)
//                .map(c -> c - '0').toArray();
//        System.out.println(digits.length);
//
//        System.out.println(
//                toString(first8(applyPattern(messageLocation, digits, 100))));
    }

    private static String toString(int[] input) {
        StringWriter out = new StringWriter();
        for (int c : input) {
            out.write((char) (c + '0'));
        }
        return out.toString();
    }

    public static int[] applyPattern(int offset, int[] digits, int phases) {
        for (int i = 0; i < phases; ++i) {
            digits = applyPattern(offset, digits);
        }
        return digits;
    }

    public static int[] first8(int[] input) {
        var out = new int[8];
        System.arraycopy(input, 0, out, 0, 8);
        return out;
    }
}
