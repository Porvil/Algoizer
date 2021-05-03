package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;

// This class is used by Graph Algorithms for maintaining animation sequences
public class GraphSequence {

    public GraphAlgorithmType graphAlgorithmType;
    public int size;
    public int curSeqNo;
    public ArrayList<GraphAnimationState> graphAnimationStates;

    public GraphSequence(GraphAlgorithmType graphAlgorithmType) {
        this.graphAlgorithmType = graphAlgorithmType;
        this.curSeqNo = 0;
        this.size = 0;
        this.graphAnimationStates = new ArrayList<>();
    }

    public void addGraphAnimationState(GraphAnimationState graphAnimationState){
        graphAnimationStates.add(graphAnimationState);
        size++;
    }

    @Override
    public String toString() {
        return "TreeSequence{" +
                "size = " + size +
                ", curSeqNo = " + curSeqNo +
                ", graphAnimationStates = " + graphAnimationStates +
                '}';
    }

    public int getSize(){
        return graphAnimationStates.size();
    }

    public boolean backward(){
        if(curSeqNo <= 0)
            return false;

        curSeqNo--;
        return true;
    }

    public boolean forward(){
        if(size <= 0)
            return false;

        if(curSeqNo >= size-1)
            return false;

        curSeqNo++;
        return true;
    }

}