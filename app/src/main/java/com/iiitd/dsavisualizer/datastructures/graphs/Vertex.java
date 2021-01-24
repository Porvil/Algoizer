package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Comparator;

public class Vertex implements Comparable<Vertex> {
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

    @Override
    public int compareTo(Vertex o2) {

        Vertex o1 = this;

        if(o1.row == o2.row && o1.col == o2.col){
            return o1.data - o2.data;
        }
        else{
            if(o2.row == -1 || o2.col == -1){
                return -1;
            }
            else if(o1.row == -1 || o1.col == -1){
                return 1;
            }
            else{
                return o1.row - o2.row;
            }
        }
    }
}
