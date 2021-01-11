package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    public int noOfVertices;
    public Map<Integer, ArrayList<Integer>> map;
    public Map<Integer, Vertex> vertexMap;

    public Graph() {
        this.noOfVertices = 0;
        map = new HashMap<>();
        vertexMap = new HashMap<>();
    }

    // Add edges to the graph
    void addEdge(int src, int des) {
        map.get(src).add(des);
    }

    // Add edges to the graph
    void addVertex(int v, int row, int col) {
        if(map.containsKey(v)){
            System.out.println("Vertex present already");
            return;
        }

        noOfVertices++;
        map.put(v, new ArrayList<Integer>());
        vertexMap.put(v, new Vertex(row, col));
    }

    // Remove vertex from the graph, also removes edges associated with it
    void removeVertex(int v, int row, int col) {
        if(!map.containsKey(v)){
            System.out.println("Vertex not present already");
            return;
        }

        noOfVertices--;
        map.remove(v);
        vertexMap.remove(v);
        for(Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()){
            for(Integer i : new ArrayList<>(entry.getValue())){
                if(i == v)
                    entry.getValue().remove(i);
            }
//            entry.getValue().removeIf(i -> i == v);
        }
    }

    // Remove edges from the graph
    void removeEdge(int src, int des) {
        for(Integer i : new ArrayList<>(map.get(src))){
            if(i == des)
                map.get(src).remove(i);
        }
//        map.get(src).removeIf(i -> i == des);

        // if both sides(undirected graph)
//        for(Integer i : new ArrayList<>(map.get(des))){
//            if(i == src)
//                map.get(des).remove(i);
//        }
//        map.get(des).removeIf(i -> i == src);
    }

    void print(){
        System.out.println("no of vertices = " + noOfVertices);

        for(Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()){
            System.out.println();
            System.out.print(entry.getKey() + " -> ");
            for(int i : entry.getValue()){
                System.out.print(i + " " );
            }
        }

        System.out.println();
    }
}
