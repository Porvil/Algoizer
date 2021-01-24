package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    public int data;
    public int row;
    public int col;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Vertex vertex = (Vertex) o;
        return data == vertex.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
