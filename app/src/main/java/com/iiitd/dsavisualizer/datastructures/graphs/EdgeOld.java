package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class EdgeOld {
    VertexOld dest;
    double weight;

    public EdgeOld(VertexOld dest, double weight) {
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if((o == null) || (o.getClass() != this.getClass()))
            return false;

        return this.dest == ((EdgeOld)o).dest;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dest);
        return hash;
    }

}
