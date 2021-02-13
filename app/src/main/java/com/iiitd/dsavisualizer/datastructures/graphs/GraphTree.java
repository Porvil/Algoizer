package com.iiitd.dsavisualizer.datastructures.graphs;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphTree {

    public int noOfRows;
    public int noOfCols;

    public boolean directed;
    public boolean weighted;
    public int noOfVertices;
    public ArrayList<EdgePro> edgePros;                                  // Vertex number -> all (edges object)
    public Map<Integer, Pair<Integer, Integer>> vertexMap;               // Vertex number -> vertex object

    public GraphTree(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        this.noOfVertices = 0;
        edgePros = new ArrayList<>();
        vertexMap = new HashMap<>();
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
