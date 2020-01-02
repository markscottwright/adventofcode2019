package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

import com.markscottwright.adventofcode2019.Day14.Reaction;

public class Day14Test {

    @Test
    public void testParsing() {
        var reaction = Reaction.parse("2 AB, 3 BC, 4 CA => 1 FUEL");
        assertThat(reaction.output.name).isEqualTo("FUEL");
        assertThat(reaction.output.amount).isEqualTo(1);
    }

    @Test
    public void testSamples() throws Exception {
        var reactions = Day14.Reaction.parseReactions(
        //@formatter:off
                "10 ORE => 10 A\r\n" + 
                "1 ORE => 1 B\r\n" + 
                "7 A, 1 B => 1 C\r\n" + 
                "7 A, 1 C => 1 D\r\n" + 
                "7 A, 1 D => 1 E\r\n" + 
                "7 A, 1 E => 1 FUEL");
                //@formatter:on
        var recipes = Day14.Reaction.toRecipes(reactions);
        assertThat(new Day14.Solution(recipes).solveForFuel()).isEqualTo(31);
    }
}
