package com.iiitd.dsavisualizer.datastructures.graphs;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationStateType;

import java.util.ArrayList;

public class GraphAnimationState {
    public String state;
    public String info;
    public ArrayList<GraphElementAnimationData> elementAnimationData;

    public GraphAnimationState(String state) {
        this.state = state;
        this.elementAnimationData = new ArrayList<>();
    }

    public GraphAnimationState(String state, String info) {
        this.state = state;
        this.info = info;
        this.elementAnimationData = new ArrayList<>();
    }

    public void add(GraphElementAnimationData... graphElementAnimationDatas){
        for(GraphElementAnimationData graphElementAnimationData : graphElementAnimationDatas)
            elementAnimationData.add(graphElementAnimationData);
    }

    @Override
    public String toString() {
        return "GraphAnimationState{" +
                "state='" + state + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
