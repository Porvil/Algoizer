package com.iiitd.dsavisualizer.datastructures.graphs;

public class Edge {

    public int src;
    public int des;
    public int weight;
    public boolean isFirstEdge;

    public Edge(int src, int des) {
        this.src = src;
        this.des = des;
        this.weight = 1;
    }

    public Edge(int src, int des, int weight, boolean isFirstEdge) {
        this.src = src;
        this.des = des;
        this.weight = weight;
        this.isFirstEdge = isFirstEdge;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "src=" + src +
                ", des=" + des +
                ", weight=" + weight +
                '}';
    }
}
