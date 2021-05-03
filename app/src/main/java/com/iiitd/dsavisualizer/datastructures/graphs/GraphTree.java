package com.iiitd.dsavisualizer.datastructures.graphs;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// GraphTree contains data for BFS/DFS Tree
public class GraphTree {
    public int noOfRows;
    public int noOfCols;

    public boolean directed;
    public boolean weighted;
    public int noOfVertices;
    public ArrayList<EdgePro> edgePros;                      // all edges arrayList
    public Map<Integer, Pair<Integer, Integer>> vertexMap;   // Vertex number -> {row, col} Pair in graph board coordinates

    public GraphTree(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        this.noOfVertices = 0;
        edgePros = new ArrayList<>();
        vertexMap = new HashMap<>();
    }

    public boolean addVertex(int value, int row, int col){
        vertexMap.put(value, Pair.create(row, col));
        return true;
    }

    public boolean addEdge(EdgePro edgePro){
        // Only when undirected graphs
        if(!directed && edgePros.contains(edgePro)){
            return false;
        }

        edgePros.add(edgePro);
        return true;
    }

    public void printEdges(){
        for(EdgePro edgePro: edgePros){
            System.out.println(edgePro);
        }
    }

    public void printVertices(){
        for(Map.Entry<Integer, Pair<Integer, Integer>> entry: vertexMap.entrySet()){
            System.out.println(entry.getKey() + " [ " + entry.getValue().first + ", " + entry.getValue().second + " ]");
        }
    }

}