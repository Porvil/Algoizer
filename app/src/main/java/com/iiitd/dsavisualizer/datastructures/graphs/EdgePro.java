package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

// Used by BFS, DFS
// Contains information about Edge Classes [ used in BFS Tree and DFS Tree ]
public class EdgePro implements Comparable<EdgePro>{

    public int src;
    public int des;
    public int weight;
    public boolean isFirstEdge;
    public EdgeClass edgeClass;

    public EdgePro(Edge edge, EdgeClass edgeClass) {
        this.src = edge.src;
        this.des = edge.des;
        this.weight = edge.weight;
        this.edgeClass = edgeClass;
        this.isFirstEdge = edge.isFirstEdge;
    }

    @Override
    public String toString() {
        return "EdgePro{" +
                "src=" + src +
                ", des=" + des +
                ", weight=" + weight +
                ", edgeClass=" + edgeClass +
                ", isFirstEdge=" + isFirstEdge +
                '}';
    }

    // Used by GraphTree
    // should only be used in undirected graphs
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EdgePro edgePro = (EdgePro) o;

        return (src == edgePro.src && des == edgePro.des) ||
                (src == edgePro.des && des == edgePro.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, des, weight);
    }

    @Override
    public int compareTo(EdgePro o2) {
        EdgePro o1 = this;

        if(o1.edgeClass == o2.edgeClass){
            return o1.src-o2.src;
        }
        else{
            if(o1.edgeClass == EdgeClass.TREE){
                return -1;
            }

            return 1;
        }
    }

}