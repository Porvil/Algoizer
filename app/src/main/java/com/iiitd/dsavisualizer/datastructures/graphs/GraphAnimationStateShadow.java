package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.LinkedList;

public class GraphAnimationStateShadow {

    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public ArrayList<Integer> queues;

    public GraphAnimationStateShadow() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.queues = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "GraphAnimationStateShadow{" +
                "vertices=" + vertices +
                ", edges=" + edges +
                ", queues=" + queues +
                '}';
    }

    public static GraphAnimationStateShadow create(){
        return new GraphAnimationStateShadow();
    }

    public GraphAnimationStateShadow addEdges(ArrayList<Edge> edges){
        this.edges.addAll(edges);
        return this;
    }

    public GraphAnimationStateShadow addVertices(ArrayList<Vertex> vertices){
        this.vertices.addAll(vertices);
        return this;
    }

    public GraphAnimationStateShadow addQueues(ArrayList<Integer> queues){
        this.queues.addAll(queues);
        return this;
    }

    public GraphAnimationStateShadow addQueues(LinkedList<Integer> queues){
        this.queues.addAll(queues);
        return this;
    }

}
