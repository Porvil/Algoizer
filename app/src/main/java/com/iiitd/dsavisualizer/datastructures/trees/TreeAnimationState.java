package com.iiitd.dsavisualizer.datastructures.trees;

import java.util.ArrayList;

public class TreeAnimationState {
    public String info;
    public ArrayList<TreeElementAnimationData> elementAnimationData;

    public TreeAnimationState(String info) {
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
                "info='" + info + '\'' +
                '}';
    }
}
