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

}
