package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// GraphActivity BackEnd
public class Graph {

    public boolean directed;
    public boolean weighted;
    public int noOfVertices;
    public Map<Integer, ArrayList<Edge>> edgeListMap;        // Vertex number -> all (edges object)
    public Map<Integer, Vertex> vertexMap;                   // Vertex number -> vertex object

    public Graph(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        this.noOfVertices = 0;
        edgeListMap = new HashMap<>();
        vertexMap = new HashMap<>();
    }

    // Add Edge to the graph
    boolean addEdge(int src, int des, int weight, boolean isFirstEdge) {
        if(checkContainsVertices(src, des)) {
            edgeListMap.get(src).add(new Edge(src, des, weight, isFirstEdge));
            return true;
        }

        return false;
    }

    // Add Vertex to the graph
    boolean addVertex(int v, int row, int col) {
        if(edgeListMap.containsKey(v)){
            System.out.println("Vertex present already");
            return false;
        }

        noOfVertices++;
        edgeListMap.put(v, new ArrayList<Edge>());
        vertexMap.put(v, new Vertex(v, row, col));

        return true;
    }

    // Remove vertex from the graph, also removes edges associated with it
    void removeVertex(int v) {
        if(!edgeListMap.containsKey(v)){
            System.out.println("Vertex not present already");
            return;
        }

        noOfVertices--;
        edgeListMap.remove(v);
        vertexMap.remove(v);
        for(Map.Entry<Integer, ArrayList<Edge>> entry : edgeListMap.entrySet()){
            for(Edge i : new ArrayList<>(entry.getValue())){
                if(i.des == v)
                    entry.getValue().remove(i);
            }
        }
    }

    // Remove Edge from the graph
    void removeEdge(int src, int des) {
        for(Edge i : new ArrayList<>(edgeListMap.get(src))){
            if(i.des == des)
                edgeListMap.get(src).remove(i);
        }
    }

    // earlier length == 0 -> returns true
    // returns true if all vertices are present, or passed vertices length is 0
    boolean checkContainsVertices(int... vertices){
        if(vertices.length == 0)
            return false;

        for(int vertex : vertices){
            if(!edgeListMap.containsKey(vertex))
                return false;
        }

        return true;
    }

    boolean checkContainsEdge(int src, int des){
        if(!checkContainsVertices(src, des)){
            return false;
        }

        // Undirected
        if(!directed){
            for(Edge edge : edgeListMap.get(src)){
                if(edge.des == des){
                    return true;
                }
            }

            for(Edge edge : edgeListMap.get(des)){
                if(edge.des == src){
                    return true;
                }
            }
        }
        else{
            for(Edge edge : edgeListMap.get(src)){
                if(edge.des == des){
                    return true;
                }
            }
        }

        return false;
    }

    public ArrayList<Edge> getAllEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        for(Map.Entry<Integer, ArrayList<Edge>> entry : edgeListMap.entrySet()){
            for(Edge i : entry.getValue()){
                edges.add(i);
            }
        }

        return edges;
    }

    void print(){
        System.out.println("no of vertices = " + noOfVertices);

        for(Map.Entry<Integer, ArrayList<Edge>> entry : edgeListMap.entrySet()){
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

    public Edge getEdge(int src, int des){
        if(!checkContainsVertices(src, des)){
            return null;
        }

        // Undirected
        if(!directed){
            for(Edge edge : edgeListMap.get(src)){
                if(edge.des == des){
                    return edge;
                }
            }

            for(Edge edge : edgeListMap.get(des)){
                if(edge.des == src){
                    return edge;
                }
            }
        }
        else{
            for(Edge edge : edgeListMap.get(src)){
                if(edge.des == des){
                    return edge;
                }
            }
        }

        return null;
    }

    // Returns edge which is firstEdge in case of undirected graph
    public Edge getEdgeFirstEdge(int src, int des){
        if(!checkContainsVertices(src, des)){
            return null;
        }

        // Undirected
        if(!directed){
            for(Edge edge : edgeListMap.get(src)){
                if(edge.des == des && edge.isFirstEdge){
                    return edge;
                }
            }

            for(Edge edge : edgeListMap.get(des)){
                if(edge.des == src && edge.isFirstEdge){
                    return edge;
                }
            }
        }
        else{
            for(Edge edge : edgeListMap.get(src)){
                if(edge.des == des && edge.isFirstEdge){
                    return edge;
                }
            }
        }

        return null;
    }

    // Returns true if contain negative edge
    public boolean hasNegativeEdges(){
        for(Map.Entry<Integer, ArrayList<Edge>> entry : edgeListMap.entrySet()){
            for(Edge i : entry.getValue()){
                if(i.weight < 0) {
                    return true;
                }
            }
        }

        return false;
    }

}