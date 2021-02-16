package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class EdgePro {

    public int src;
    public int des;
    public int weight;
    public boolean isDirected;
    public EdgeClass edgeClass;

    public EdgePro(int src, int des, EdgeClass edgeClass) {
        this.src = src;
        this.des = des;
        this.weight = 1;
        this.edgeClass = edgeClass;
    }

    public EdgePro(int src, int des, int weight, EdgeClass edgeClass) {
        this.src = src;
        this.des = des;
        this.weight = weight;
        this.edgeClass = edgeClass;
    }

    public EdgePro(Edge edge, EdgeClass edgeClass) {
        this.src = edge.src;
        this.des = edge.des;
        this.weight = edge.weight;
        this.edgeClass = edgeClass;
    }

    @Override
    public String toString() {
        return "EdgePro{" +
                "src=" + src +
                ", des=" + des +
                ", weight=" + weight +
                ", isDirected=" + isDirected +
                ", edgeClass=" + edgeClass +
                '}';
    }

    // need to complete this function [ currently should only be used in undirected graphs ]
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EdgePro edgePro = (EdgePro) o;

        return (src == edgePro.src && des == edgePro.des) ||
                (src == edgePro.des && des == edgePro.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, des, weight, isDirected);
    }
}
