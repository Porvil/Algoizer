package com.iiitd.dsavisualizer.datastructures.graphs;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    public boolean directed;
    public boolean weighted;
    public int noOfVertices;
    public Map<Integer, ArrayList<Edge>> map;
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
        map.get(src).add(new Edge(src, des, weight));
//        map.get(src).add(new Pair<>(des, weight));
    }

    // Add edges to the graph
    void addVertex(int v, int row, int col) {
        if(map.containsKey(v)){
            System.out.println("Vertex present already");
            return;
        }

        noOfVertices++;
        map.put(v, new ArrayList<Edge>());
        vertexMap.put(v, new Vertex(v, row, col));
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
        for(Map.Entry<Integer, ArrayList<Edge>> entry : map.entrySet()){
            for(Edge i : new ArrayList<>(entry.getValue())){
                if(i.des == v)
                    entry.getValue().remove(i);
            }
//            entry.getValue().removeIf(i -> i == v);
        }
    }

    // Remove edges from the graph
    void removeEdge(int src, int des) {
        for(Edge i : new ArrayList<>(map.get(src))){
            if(i.des == des)
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

        for(Map.Entry<Integer, ArrayList<Edge>> entry : map.entrySet()){
            System.out.println();
            System.out.print(entry.getKey() + " -> ");
            for(Edge i : entry.getValue()){
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

    public void clearGraph(){
        this.noOfVertices = 0;
        map = new HashMap<>();
        vertexMap = new HashMap<>();
    }

}
