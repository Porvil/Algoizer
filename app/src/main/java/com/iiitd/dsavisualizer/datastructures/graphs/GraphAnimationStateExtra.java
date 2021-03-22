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

    public GraphAnimationStateExtra() {
        this.queues = new ArrayList<>();
        this.stacks = new ArrayList<>();
        this.map = new HashMap<>();
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

    public GraphAnimationStateExtra addMap(HashMap<Integer, VertexCLRS> oldMap){
        System.out.println("@@@@@@@@@@@@@@@@@" + oldMap);
        for (Map.Entry<Integer, VertexCLRS> entry : oldMap.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue().dijkstraDist);
        }

        return this;
    }

}
