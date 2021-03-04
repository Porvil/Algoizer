package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

public class VertexCLRS {

    // Vertex Variables
    public int data;
    public int row;
    public int col;

    // Vertex CLRS Variables
    public VertexVisitState color;         // used by both bfs and dfs
    public int dist;                       // used as start time in dfs, and dist in case of bfs [ bfs order traversal ]
    public int parent;                     // used by both bfs and dfs
    public int f;                          // used by dfs end time
    public int dfsDepth;                   // used for dfs depth
//    public int l;                          // used by bfs level

    // Used by BFS
    public VertexCLRS(Vertex vertex, VertexVisitState color, int dist, int parent) {
        this.data = vertex.data;
        this.row = vertex.row;
        this.col = vertex.col;
        this.color = color;
        this.dist = dist;
        this.parent = parent;
//        this.l = 0;
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
        this.dfsDepth = 0;
    }

    @Override
    public String toString() {
        return "VertexCLRS{" +
                "data=" + data +
                ", row=" + row +
                ", col=" + col +
                ", color=" + color +
                ", dist=" + dist +
                ", parent=" + parent +
                ", f=" + f +
                ", dfsDepth=" + dfsDepth +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
