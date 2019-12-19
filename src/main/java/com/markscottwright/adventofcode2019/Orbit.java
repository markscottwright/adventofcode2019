package com.markscottwright.adventofcode2019;

import java.util.Objects;

public class Orbit {
    final String id;
    Orbit orbit;

    public Orbit(String id) {
        this.id = id;
        this.orbit = null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orbit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Orbit other = (Orbit) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        if (orbit == null)
            return "Orbit [id=" + id + "]";
        else
            return "Orbit [id=" + id + ", orbit=" + orbit.id + "]";
    }

    public int numDirectAndIndirectOrbits() {
        int i = 0;
        Orbit o = orbit;
        while (o != null) {
            i++;
            o = o.orbit;
        }

        return i;
    }

    public void setOrbit(Orbit orbit) {
        this.orbit = orbit;
    }
}
