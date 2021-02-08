package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class VertexBFS implements Comparable<VertexBFS> {
    public int data;
    public int row;
    public int col;
    public String color;
    public int dist;
    public int parent;

    public VertexBFS(int data, int row, int col) {
        this.data = data;
        this.row = row;
        this.col = col;
    }

    public VertexBFS(Vertex vertex, String color, int dist, int parent) {
        this.data = vertex.data;
        this.row = vertex.row;
        this.col = vertex.col;
        this.color = color;
        this.dist = dist;
        this.parent = parent;
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
    public int compareTo(VertexBFS o2) {

        VertexBFS o1 = this;

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

        VertexBFS vertex = (VertexBFS) o;
        return data == vertex.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
