package com.iiitd.dsavisualizer.datastructures.trees;

import java.util.ArrayList;

// This class is used by Tree Algorithms for maintaining animation sequences
public class TreeSequence {

    public int size;
    public int curSeqNo;
    public ArrayList<TreeAnimationState> treeAnimationStates;


    public TreeSequence() {
        this.treeAnimationStates = new ArrayList<>();
    }

    public TreeSequence(ArrayList<TreeAnimationState> treeAnimationStates) {
        this.treeAnimationStates = treeAnimationStates;
        this.curSeqNo = 0;
        this.size = treeAnimationStates.size();
    }

    @Override
    public String toString() {
        return "TreeSequence{" +
                "size = " + size +
                ", curSeqNo = " + curSeqNo +
                ", treeAnimationStates = " + treeAnimationStates +
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