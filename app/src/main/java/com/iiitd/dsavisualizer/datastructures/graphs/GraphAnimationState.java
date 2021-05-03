package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// This class is used to hold complete animation data for an instance of a graph algorithm
public class GraphAnimationState {
    public String state;
    public String info;
    public ArrayList<Edge> edges;
    public HashMap<Integer, Vertex> verticesState;
    public GraphAnimationStateExtra graphAnimationStateExtra;

    public GraphAnimationState() {
        this.edges = new ArrayList<>();
        this.verticesState = new HashMap<>();
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
                ", edges=" + edges +
                ", verticesState=" + verticesState +
                ", graphAnimationStateExtra=" + graphAnimationStateExtra +
                '}';
    }

    public GraphAnimationState setVerticesState(HashMap<Integer, Vertex> _verticesState){
        this.verticesState.clear();
        for (Map.Entry<Integer, Vertex> entry : _verticesState.entrySet()) {
            this.verticesState.put(entry.getKey(), Vertex.getClone(entry.getValue()));
        }
        return this;
    }

    public static GraphAnimationState create(){
        return new GraphAnimationState();
    }

    public GraphAnimationState addEdges(ArrayList<Edge> _edges){
        for(Edge edge : _edges){
            edges.add(Edge.getClone(edge));
        }

        return this;
    }

    public GraphAnimationState addGraphAnimationStateExtra(GraphAnimationStateExtra graphAnimationStateExtra){
        this.graphAnimationStateExtra = graphAnimationStateExtra;
        return this;
    }

}