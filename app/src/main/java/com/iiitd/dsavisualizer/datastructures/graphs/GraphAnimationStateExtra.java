package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class GraphAnimationStateExtra {
    public ArrayList<Integer> queues;
    public ArrayList<Integer> stacks;

    public GraphAnimationStateExtra() {
        this.queues = new ArrayList<>();
        this.stacks = new ArrayList<>();
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

}
