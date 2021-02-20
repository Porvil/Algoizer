package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;

public class GraphAnimationState {
    public String state;
    public String info;
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public GraphAnimationStateExtra graphAnimationStateExtra;

    public GraphAnimationState() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.graphAnimationStateExtra = null;
    }

    public GraphAnimationState setState(String state){
        this.state = state;
        return this;
    }

    public GraphAnimationState setInfo(String info){
        this.info = info;
        return this;
    }

    @Override
    public String toString() {
        return "GraphAnimationState{" +
                "state='" + state + '\'' +
                ", info='" + info + '\'' +
                ", vertices=" + vertices +
                ", edges=" + edges +
                ", graphAnimationStateExtra=" + graphAnimationStateExtra +
                '}';
    }

    public static GraphAnimationState create(){
        return new GraphAnimationState();
    }

    public GraphAnimationState addEdges(ArrayList<Edge> edges){
        this.edges.addAll(edges);
        return this;
    }

    public GraphAnimationState addVertices(ArrayList<Vertex> vertices){
        this.vertices.addAll(vertices);
        return this;
    }

    public GraphAnimationState addGraphAnimationStateExtra(GraphAnimationStateExtra graphAnimationStateExtra){
        this.graphAnimationStateExtra = graphAnimationStateExtra;
        return this;
    }

}
