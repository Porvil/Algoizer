package com.iiitd.dsavisualizer.datastructures.graphs;

public class GraphElementAnimationData {
    public int row;
    public int col;
    public String info;

    public GraphElementAnimationData(int row, int col, String info) {
        this.row = row;
        this.col = col;
        this.info = info;
    }

    public GraphElementAnimationData(int row, int col) {
        this.row = row;
        this.col = col;
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
