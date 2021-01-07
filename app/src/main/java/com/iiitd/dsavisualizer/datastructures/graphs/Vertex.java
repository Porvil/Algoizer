package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Vertex {

    int name;
    boolean isVisited;
    int row;
    int col;
    Set<Edge> edges;

    public Vertex(int name, int row, int col) {
        this.name = name;
        this.isVisited = false;
        this.row = row;
        this.col = col;
        edges = new HashSet<>();
    }

    public boolean createEdge(Edge edge) {
        if (edges.contains(edge))
            return false;

        edges.add(edge);
        return true;
    }

    public void printEdges() {
        Iterator it = edges.iterator();
        System.out.print(name);
        while (it.hasNext()) {
            System.out.print(" --> " + ((Edge) (it.next())).dest.name);
        }
        System.out.println(" --> NULL ");
    }

}