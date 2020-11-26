package com.iiitd.dsavisualizer.datastructures.trees;

import java.util.ArrayList;

public class TreeSequence {

    public int size;
    public int curSeqNo;
    public ArrayList<TreeAnimationState> animationStates;


    public TreeSequence() {
        this.animationStates = new ArrayList<>();
    }

    public TreeSequence(ArrayList<TreeAnimationState> animationStates) {
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

