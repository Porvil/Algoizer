package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class VertexCLRS {

    // Vertex Variables
    public int data;
    public int row;
    public int col;

    // Vertex CLRS Variables
    public VertexVisitState color;         //
    public int dist;                       //
    public int parent;                     //
    public int f;                          //

    // Used by BFS
    public VertexCLRS(Vertex vertex, VertexVisitState color, int dist, int parent) {
        this.data = vertex.data;
        this.row = vertex.row;
        this.col = vertex.col;
        this.color = color;
        this.dist = dist;
        this.parent = parent;
    }

    // Used by DFS
    public VertexCLRS(Vertex vertex, VertexVisitState color, int dist, int parent, int f) {
        this.data = vertex.data;
        this.row = vertex.row;
        this.col = vertex.col;
        this.color = color;
        this.dist = dist;
        this.parent = parent;
        this.f = f;
    }

    @Override
    public String toString() {
        return "VertexCLRS{" +
                "data=" + data +
                ", row=" + row +
                ", col=" + col +
                ", color='" + color + '\'' +
                ", dist=" + dist +
                ", parent=" + parent +
                ", f=" + f +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
