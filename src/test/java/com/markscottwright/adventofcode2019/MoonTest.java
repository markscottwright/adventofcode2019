package com.markscottwright.adventofcode2019;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class MoonTest {

    @Test
    public void testParsing() throws Exception {
        assertThat(Moon.parseLine("<x=-1, y=0, z=2>").x).isEqualTo(-1);
        assertThat(Moon.parse("<x=-1, y=0, z=2>\r\n" + "<x=2, y=-10, z=-7>\r\n"
                + "<x=4, y=-8, z=8>\r\n" + "<x=3, y=5, z=-1>")).hasSize(4);
    }

    @Test
    public void test() {
        HashSet<Moon> moons = new HashSet<>();
        moons.add(new Moon(-1, 0, 2));
        moons.add(new Moon(2, -10, -7));
        moons.add(new Moon(4, -8, 8));
        moons.add(new Moon(3, 5, -1));

        var moons2 = moons.stream().map(m -> m.applyGravity(moons))
                .collect(Collectors.toSet());
        assertThat(moons2).containsOnlyElementsOf(
                Moon.parse("pos=<x= 2, y=-1, z= 1>, vel=<x= 3, y=-1, z=-1>\r\n"
                        + "pos=<x= 3, y=-7, z=-4>, vel=<x= 1, y= 3, z= 3>\r\n"
                        + "pos=<x= 1, y=-7, z= 5>, vel=<x=-3, y= 1, z=-3>\r\n"
                        + "pos=<x= 2, y= 2, z= 0>, vel=<x=-1, y=-3, z= 1>"));
    }

}
