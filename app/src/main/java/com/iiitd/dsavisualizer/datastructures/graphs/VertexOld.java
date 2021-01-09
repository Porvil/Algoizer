package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class VertexOld {

    int name;
    boolean isVisited;
    int row;
    int col;
    Set<EdgeOld> edgeOlds;

    public VertexOld(int name, int row, int col) {
        this.name = name;
        this.isVisited = false;
        this.row = row;
        this.col = col;
        edgeOlds = new HashSet<>();
    }

    public boolean createEdge(EdgeOld edgeOld) {
        if (edgeOlds.contains(edgeOld))
            return false;

        edgeOlds.add(edgeOld);
        return true;
    }

    public void printEdges() {
        Iterator it = edgeOlds.iterator();
        System.out.print(name);
        while (it.hasNext()) {
            System.out.print(" --> " + ((EdgeOld) (it.next())).dest.name);
        }
        System.out.println(" --> NULL ");
    }

}