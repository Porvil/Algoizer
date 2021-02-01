package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS {
    Graph g;
    GraphSequence graphSequence;

    public BFS(Graph g){
        this.g = g;
        this.graphSequence = new GraphSequence();
    }

    GraphSequence run(int s){

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

        // DES == NODE FOR ANIMATION
        vertices.add(vertex);
        GraphAnimationState graphAnimationState = new GraphAnimationState("Visit = " + s);
        graphAnimationState.add(new GraphElementAnimationData(vertex.row, vertex.col, -1, s));


        GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
//        graphAnimationStateShadow.vertices.add(vertex);
        graphAnimationStateShadow.vertices.addAll(vertices);
        graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);


        graphSequence.graphAnimationStates.add(graphAnimationState);



        while (queue.size() != 0) {
            s = queue.poll();
//            System.out.println(s + " ");

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
                    graphAnimationState1.add(new GraphElementAnimationData(vertex1.row, vertex1.col, s, edge.des));

                    GraphAnimationStateShadow graphAnimationStateShadow1 = new GraphAnimationStateShadow();
//                    graphAnimationStateShadow1.vertices.add(vertex1);
//                    graphAnimationStateShadow1.edges.add(edge);
                    graphAnimationStateShadow1.vertices.addAll(vertices);
                    graphAnimationStateShadow1.edges.addAll(edges);
                    graphAnimationState1.graphAnimationStateShadow.add(graphAnimationStateShadow1);

                    graphSequence.graphAnimationStates.add(graphAnimationState1);
                }
            }

//            System.out.println();
        }

        return graphSequence;
    }
}
