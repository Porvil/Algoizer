package com.iiitd.dsavisualizer.datastructures.graphs;

public class Vertex {
    public int data;
    public int row;
    public int col;
//
//    public Vertex(int row, int col) {
//        this.row = row;
//        this.col = col;
//    }

    public Vertex(int data, int row, int col) {
        this.data = data;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "data=" + data +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
