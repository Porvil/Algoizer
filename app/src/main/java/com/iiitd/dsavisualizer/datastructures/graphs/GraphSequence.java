package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;

public class GraphSequence {

    public int size;
    public int curSeqNo;
    public ArrayList<GraphAnimationState> animationStates;


    public GraphSequence() {
        this.animationStates = new ArrayList<>();
    }

    public GraphSequence(ArrayList<GraphAnimationState> animationStates) {
        this.animationStates = animationStates;
        this.curSeqNo = 0;
        this.size = animationStates.size();
    }

    @Override
    public String toString() {
        return "TreeSequence{" +
                "size=" + size +
                ", curSeqNo=" + curSeqNo +
                ", animationStates=" + animationStates +
                '}';
    }

    public boolean backward(){
        curSeqNo--;
        return true;
    }

    public boolean forward(){
        curSeqNo++;
        return true;
    }

}

