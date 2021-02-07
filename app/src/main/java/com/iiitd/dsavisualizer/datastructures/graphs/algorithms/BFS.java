package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateShadow;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS {
    Graph graph;
    GraphSequence graphSequence;

    public BFS(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.BFS);
    }

    public GraphSequence run(int s) {

        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        {
            GraphAnimationState graphAnimationState = new GraphAnimationState("start");

            GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
            graphAnimationStateShadow.vertices.addAll(vertices);
            graphAnimationStateShadow.edges.addAll(edges);
            graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Edge>> entry : graph.map.entrySet()){
            visited.put(entry.getKey(), false);
        }

        LinkedList<Integer> queue = new LinkedList<>();

        Vertex vertex = graph.vertexMap.get(s);
        visited.put(s, true);
        queue.add(s);

        vertices.add(vertex);
        GraphAnimationState graphAnimationState = new GraphAnimationState("Visit = " + s);
        GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
        graphAnimationStateShadow.vertices.addAll(vertices);
        graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
        graphSequence.addGraphAnimationState(graphAnimationState);


        while (queue.size() != 0) {
            s = queue.pop();
            System.out.println(s + " ");

            ArrayList<Edge> edgeArrayList = graph.map.get(s);
            for(Edge edge : edgeArrayList){
                if(!visited.get(edge.des)){
                    visited.put(edge.des, true);
                    System.out.println("EDGE IN BFS :-    " + s + " --> " + edge.des);
                    queue.add(edge.des);
                    Vertex vertex1 = graph.vertexMap.get(edge.des);

                    vertices.add(vertex1);
                    edges.add(edge);
                    GraphAnimationState graphAnimationState1 = new GraphAnimationState("Visit = " + edge.des);
                    GraphAnimationStateShadow graphAnimationStateShadow1 = new GraphAnimationStateShadow();
                    graphAnimationStateShadow1.vertices.addAll(vertices);
                    graphAnimationStateShadow1.edges.addAll(edges);
                    graphAnimationState1.graphAnimationStateShadow.add(graphAnimationStateShadow1);
                    graphSequence.addGraphAnimationState(graphAnimationState1);
                }
            }

        }

        return graphSequence;
    }
}
