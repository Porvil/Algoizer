package com.iiitd.dsavisualizer.datastructures.graphs;

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

}
