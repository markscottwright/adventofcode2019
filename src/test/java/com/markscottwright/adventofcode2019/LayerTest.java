package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LayerTest {

    @Test
    public void test() {
        var layers = Layer.parse(2, 2, "0222112222120000");
        var image = layers.stream().reduce((l1, l2) -> l1.overlay(l2)).get();
        assertThat(image.toString()).isEqualTo("01\n10\n");
    }

}
