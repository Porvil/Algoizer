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
    Graph g;
    GraphSequence graphSequence;

    public BFS(Graph g){
        this.g = g;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.BFS);
    }

    public GraphSequence run(int s){

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Edge>> entry : g.map.entrySet()){
            visited.put(entry.getKey(), false);
        }

        LinkedList<Integer> queue = new LinkedList<>();

        Vertex vertex = g.vertexMap.get(s);
        visited.put(s, true);
        queue.add(s);

        vertices.add(vertex);
        GraphAnimationState graphAnimationState = new GraphAnimationState("Visit = " + s);
        GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
        graphAnimationStateShadow.vertices.addAll(vertices);
        graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
        graphSequence.graphAnimationStates.add(graphAnimationState);


        while (queue.size() != 0) {
            s = queue.poll();
            System.out.println(s + " ");

            ArrayList<Edge> i = g.map.get(s);
            for(Edge edge : i){
                if(!visited.get(edge.des)){
                    visited.put(edge.des, true);
                    System.out.println("EDGE IN BFS :-    " + s + " --> " + edge.des);
                    queue.add(edge.des);
                    Vertex vertex1 = g.vertexMap.get(edge.des);

                    vertices.add(vertex1);
                    edges.add(edge);
                    GraphAnimationState graphAnimationState1 = new GraphAnimationState("Visit = " + edge.des);
                    GraphAnimationStateShadow graphAnimationStateShadow1 = new GraphAnimationStateShadow();
                    graphAnimationStateShadow1.vertices.addAll(vertices);
                    graphAnimationStateShadow1.edges.addAll(edges);
                    graphAnimationState1.graphAnimationStateShadow.add(graphAnimationStateShadow1);
                    graphSequence.graphAnimationStates.add(graphAnimationState1);
                }
            }

        }

        return graphSequence;
    }
}
