package com.iiitd.dsavisualizer.datastructures.graphs;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    public boolean directed;
    public boolean weighted;
    public int noOfVertices;
    public Map<Integer, ArrayList<Pair<Integer, Integer>>> map;
    public Map<Integer, Vertex> vertexMap;

    public Graph(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        this.noOfVertices = 0;
        map = new HashMap<>();
        vertexMap = new HashMap<>();
    }

    // Add edges to the graph
    void addEdge(int src, int des, int weight) {
        map.get(src).add(new Pair<>(des, weight));
    }

    // Add edges to the graph
    void addVertex(int v, int row, int col) {
        if(map.containsKey(v)){
            System.out.println("Vertex present already");
            return;
        }

        noOfVertices++;
        map.put(v, new ArrayList<Pair<Integer, Integer>>());
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
        for(Map.Entry<Integer, ArrayList<Pair<Integer, Integer>>> entry : map.entrySet()){
            for(Pair<Integer, Integer> i : new ArrayList<>(entry.getValue())){
                if(i.first == v)
                    entry.getValue().remove(i);
            }
//            entry.getValue().removeIf(i -> i == v);
        }
    }

    // Remove edges from the graph
    void removeEdge(int src, int des) {
        for(Pair<Integer, Integer> i : new ArrayList<>(map.get(src))){
            if(i.first == des)
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

        for(Map.Entry<Integer, ArrayList<Pair<Integer, Integer>>> entry : map.entrySet()){
            System.out.println();
            System.out.print(entry.getKey() + " -> ");
            for(Pair<Integer, Integer> i : entry.getValue()){
                System.out.print(i + " " );
            }
        }

        System.out.println();
    }

    public int getNewVertexNumber(){
        for(int i=0;i<noOfVertices+1;i++){
            if(!vertexMap.containsKey(i))
                return i;
        }

        return noOfVertices+1;
    }
}
