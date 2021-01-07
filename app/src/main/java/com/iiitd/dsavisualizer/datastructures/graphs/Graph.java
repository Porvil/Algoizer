package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.HashMap;
import java.util.Map;

public class Graph {

    int noOfVertices;
    HashMap<Integer, Vertex> vertices;

    public Graph() {
        noOfVertices = 0;
        vertices = new HashMap<>();
    }

    /*
        Returns 0 - If Vertex Creation Is Successful.
                1 - If Vertex Is Already Present.
    */
    public Vertex createVertex(int name, int row, int col){
        if(vertices.containsKey(name) )
            return null;

        noOfVertices++;

        Vertex v = new Vertex(name, row, col);
        vertices.put(name, v);
        return v;

    }

    /*
        Returns 0 - If Edge Creation Is Successful.
                1 - If Source Vertex Is Not Present In Graph.
                2 - If Destination Vertex Is Not Present In Graph.
                3 - If Source And Destination Vertex Are Not Present In Graph.
                4 - If Edge Is Already Present.
    */
    public int createEdge(int source, int destination, double weight){
        int errorCode = 0;
        Vertex sourceV = vertices.get(source);
        Vertex destinationV = vertices.get(destination);

        if(sourceV == null)
            errorCode += 1;

        if(destinationV == null)
            errorCode += 2;

        if(errorCode != 0)
            return errorCode;

        boolean isSuccess = sourceV.createEdge(new Edge(destinationV, weight));
        if(isSuccess)
            return errorCode;

        return 4;

    }

    public void print(){
        for(Map.Entry<Integer, Vertex> vertex : vertices.entrySet() ){
            vertex.getValue().printEdges();
        }
    }

}
