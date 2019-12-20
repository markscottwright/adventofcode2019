package com.markscottwright.adventofcode2019;

import java.io.PrintStream;
import java.util.ArrayList;

public class Layer {
    ArrayList<String> rows = new ArrayList<>();

    public Layer(int width, int height, String pixels) {
        var i = new StringChunkIterator(width, pixels);
        while (i.hasNext())
            rows.add(i.next());
    }

    private Layer() {

    }

    Layer overlay(Layer layerBelow) {
        var out = new Layer();
        for (int i = 0; i < rows.size(); ++i) {
            var outRow = "";
            for (int j = 0; j < rows.get(0).length(); ++j) {
                char myPixel = rows.get(i).charAt(j);
                char belowPixel = layerBelow.rows.get(i).charAt(j);
                if (myPixel == '2')
                    outRow += belowPixel;
                else
                    outRow += myPixel;
            }
            out.rows.add(outRow);
        }
        return out;
    }

    static ArrayList<Layer> parse(int width, int height, String pixels) {
        ArrayList<Layer> out = new ArrayList<>();
        var i = new StringChunkIterator(width * height, pixels);
        while (i.hasNext()) {
            var layer = new Layer(width, height, i.next());
            out.add(layer);
        }
        return out;
    }

    public String toString() {
        String out = "";
        for (var r : rows)
            out += r + "\n";
        return out;
    }
    
    public void printOn(PrintStream out) {
        for (var r : rows) {
            for (var p : r.toCharArray()) {
                if (p == '0')
                    out.print(" ");
                else
                    out.print("\u2588");
            }
            out.println();
        }
    }
}
