package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class Edge{
    Vertex dest;
    double weight;

    public Edge(Vertex dest, double weight) {
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if((o == null) || (o.getClass() != this.getClass()))
            return false;

        return this.dest == ((Edge)o).dest;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dest);
        return hash;
    }

}
