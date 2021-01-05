package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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


class Vertex{

    int name;
    boolean isVisited;
    int row;
    int col;
    Set<Edge> edges;

    public Vertex(int name, int row, int col) {
        this.name = name;
        this.isVisited = false;
        this.row = row;
        this.col = col;
        edges = new HashSet<>();
    }

    public boolean createEdge(Edge edge){
        if(edges.contains(edge))
            return false;

        edges.add(edge);
        return true;

    }

    public void printEdges(){
        Iterator it = edges.iterator();
        System.out.print(name);
        while(it.hasNext()){
            System.out.print( " --> " + ((Edge)(it.next())).dest.name );
        }
        System.out.println(" --> NULL ");
    }

}

class Edge{
    Vertex dest;
    double weight;

    public Edge(Vertex dest, double weight) {
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {

        if((o == null) || (o.getClass() != this.getClass())) return false;

        return this.dest == ((Edge)o).dest;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dest);
        return hash;
    }

}
