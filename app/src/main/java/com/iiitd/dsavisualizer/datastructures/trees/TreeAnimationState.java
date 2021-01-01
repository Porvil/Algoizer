package com.iiitd.dsavisualizer.datastructures.trees;

import java.util.ArrayList;

public class TreeAnimationState {
    public TreeAnimationStateType state;
    public String info;
    public ArrayList<TreeElementAnimationData> elementAnimationData;

    public TreeAnimationState(TreeAnimationStateType state) {
        this.state = state;
        this.elementAnimationData = new ArrayList<>();
    }

    public TreeAnimationState(TreeAnimationStateType state, String info) {
        this.state = state;
        this.info = info;
        this.elementAnimationData = new ArrayList<>();
    }

    public void add(TreeElementAnimationData... treeElementAnimationDatas){
        for(TreeElementAnimationData treeElementAnimationData : treeElementAnimationDatas)
            elementAnimationData.add(treeElementAnimationData);
    }

    @Override
    public String toString() {
        return "TreeAnimationState{" +
                "info='" + state + '\'' +
                '}';
    }
}
