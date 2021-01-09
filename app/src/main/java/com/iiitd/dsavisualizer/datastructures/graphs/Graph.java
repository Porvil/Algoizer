package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    public int noOfVertices;
    public Map<Integer, ArrayList<Integer>> map;

    public Graph() {
        this.noOfVertices = 0;
        map = new HashMap<>();
    }

    // Add edges to the graph
    void addEdge(int src, int des) {
        map.get(src).add(des);
    }

    // Add edges to the graph
    void addVertex(int v) {
        if(map.containsKey(v)){
            System.out.println("Vertex present already");
            return;
        }

        noOfVertices++;
        map.put(v, new ArrayList<Integer>());
    }
}
