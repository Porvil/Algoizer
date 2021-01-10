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

    void run(int s){
        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Integer>> entry : g.map.entrySet()){
            visited.put(entry.getKey(), false);
        }

        LinkedList<Integer> queue = new LinkedList<>();

        Vertex vertex = g.vertexMap.get(s);
        visited.put(s, true);
        queue.add(s);

        GraphAnimationState graphAnimationState = new GraphAnimationState("Visit = " + s);
        graphAnimationState.add(new GraphElementAnimationData(vertex.row, vertex.col));
        graphSequence.animationStates.add(graphAnimationState);

        while (queue.size() != 0) {
            s = queue.poll();
//            System.out.println(s + " ");

            ArrayList<Integer> i = g.map.get(s);
            for(int des : i){
                if(!visited.get(des)){
                    visited.put(des, true);
//                    System.out.println("EDGE IN BFS :-    " + s + " --> " + des);
                    queue.add(des);
                    Vertex vertex1 = g.vertexMap.get(des);
                    GraphAnimationState graphAnimationState1 = new GraphAnimationState("Visit = " + des);
                    graphAnimationState1.add(new GraphElementAnimationData(vertex1.row, vertex1.col));
                    graphSequence.animationStates.add(graphAnimationState1);
                }
            }

//            System.out.println();
        }
    }
}
