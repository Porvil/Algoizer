package com.iiitd.dsavisualizer.datastructures.graphs;

public class GraphElementAnimationData {
    public int row;
    public int col;
    public int src;
    public int des;
    public String info;

    public GraphElementAnimationData(int row, int col, int src) {
        this.row = row;
        this.col = col;
        this.src = src;
        this.des = -1;
    }

    public GraphElementAnimationData(int row, int col, int src, int des) {
        this.row = row;
        this.col = col;
        this.src = src;
        this.des = des;
    }

    @Override
    public String toString() {
        return "GraphElementAnimationData{" +
                "row=" + row +
                ", col=" + col +
                ", info='" + info + '\'' +
                '}';
    }
}
