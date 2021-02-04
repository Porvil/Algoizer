package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DFS {

    Graph g;

    public DFS(Graph g){
        this.g = g;
    }

    void DFSUtil(int v, HashMap<Integer, Boolean> visited){
        // Mark the current node as visited and print it
        visited.put(v, true);
        System.out.print(v + " ");


        ArrayList<Edge> i = g.map.get(v);
        for(Edge edge : i){
            if(visited.get(edge.des) == false){
                System.out.println("EDGE IN DFS :-    " + v + " --> " + edge);
                DFSUtil(v, visited);
            }
        }
        
    }

    // The function to do DFS traversal.
    // It uses recursive
    // DFSUtil()
    void DFS(int v){
        // Mark all the vertices as
        // not visited(set as
        // false by default in java)
        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Edge>> entry : g.map.entrySet()){
            visited.put(entry.getKey(), false);
        }
        // Call the recursive helper
        // function to print DFS
        // traversal
        DFSUtil(v, visited);
    }
}
