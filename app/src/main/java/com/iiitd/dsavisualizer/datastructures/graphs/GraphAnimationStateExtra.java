package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class GraphAnimationStateExtra {
    public ArrayList<Integer> queues;
    public ArrayList<Integer> stacks;
    public HashMap<Integer, Integer> map; // vertex number -> vertex weight
    public ArrayList<Edge> edges; // used by kruskal's
    public ArrayList<PriorityQueueElementState> priorityQueueElementStates; // Used by dijkstra

    public GraphAnimationStateExtra() {
        this.queues = new ArrayList<>();
        this.stacks = new ArrayList<>();
        this.map = new HashMap<>();
        this.priorityQueueElementStates = new ArrayList<>();
        this.edges = new ArrayList<>();
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

    public GraphAnimationStateExtra addPriorityQueueElementState(HashMap<Integer, VertexCLRS> oldMap){
        for (Map.Entry<Integer, VertexCLRS> entry : oldMap.entrySet()) {
            this.priorityQueueElementStates.add(
                    new PriorityQueueElementState(entry.getKey(), entry.getValue().visited, entry.getValue().dijkstraDist));
        }

        return this;
    }

}
