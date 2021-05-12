package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

// This class is used to hold extra animation data for an instance of a graph algorithm
public class GraphAnimationStateExtra {
    public ArrayList<Integer> queues;                                       // used by BFS
    public ArrayList<Integer> stacks;                                       // used by DFS
    public HashMap<Integer, Integer> map; // vertex number -> vertex weight // used by Dijkstra and Bellmanford
    public ArrayList<Edge> edges;                                           // used by Kruskal's
    public ArrayList<Edge> cycles;                                          // used by Bellmanford's negative cycles
    public ArrayList<PriorityQueueElementState> priorityQueueElementStates; // used by Dijkstra and Prim's

    public GraphAnimationStateExtra() {
        this.queues = new ArrayList<>();
        this.stacks = new ArrayList<>();
        this.map = new HashMap<>();
        this.priorityQueueElementStates = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.cycles = new ArrayList<>();
    }

    public static GraphAnimationStateExtra create(){
        return new GraphAnimationStateExtra();
    }

    public GraphAnimationStateExtra addQueues(LinkedList<Integer> queues){
        this.queues.addAll(queues);
        return this;
    }

    public GraphAnimationStateExtra addStacks(Stack<Integer> stacks){
        this.stacks.addAll(stacks);
        return this;
    }

    public GraphAnimationStateExtra addMapDijkstra(HashMap<Integer, VertexCLRS> oldMap){
        for (Map.Entry<Integer, VertexCLRS> entry : oldMap.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue().dijkstraDist);
        }

        return this;
    }

    public GraphAnimationStateExtra addMapBellmanford(HashMap<Integer, VertexCLRS> oldMap){
        for (Map.Entry<Integer, VertexCLRS> entry : oldMap.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue().bellmanFordDist);
        }

        return this;
    }

    public GraphAnimationStateExtra addEdges(ArrayList<Edge> _edges){
        for(Edge edge : _edges){
            edges.add(Edge.getClone(edge));
        }
        return this;
    }

    public GraphAnimationStateExtra addCycle(ArrayList<Edge> _edges){
        for(Edge edge : _edges){
            cycles.add(Edge.getClone(edge));
        }
        return this;
    }

    public GraphAnimationStateExtra addPriorityQueueElementState(HashMap<Integer, VertexCLRS> oldMap){
        for (Map.Entry<Integer, VertexCLRS> entry : oldMap.entrySet()) {
            this.priorityQueueElementStates.add(
                    new PriorityQueueElementState(entry.getKey(), entry.getValue().visited, entry.getValue().dijkstraDist));
        }

        Collections.sort(priorityQueueElementStates, new Comparator<PriorityQueueElementState>() {
            @Override
            public int compare(PriorityQueueElementState o1, PriorityQueueElementState o2) {
                return Integer.compare(o1.distance, o2.distance);
            }
        });

        return this;
    }

}