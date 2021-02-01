package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;

public class GraphSequence {

    public int size;
    public int curSeqNo;
    public ArrayList<GraphAnimationState> graphAnimationStates;


    public GraphSequence() {
        this.curSeqNo = -1;
        this.graphAnimationStates = new ArrayList<>();
    }

    public GraphSequence(ArrayList<GraphAnimationState> graphAnimationStates) {
        this.graphAnimationStates = graphAnimationStates;
        this.curSeqNo = -1;
        this.size = graphAnimationStates.size();
    }

    @Override
    public String toString() {
        return "TreeSequence{" +
                "size = " + size +
                ", curSeqNo = " + curSeqNo +
                ", graphAnimationStates = " + graphAnimationStates +
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

