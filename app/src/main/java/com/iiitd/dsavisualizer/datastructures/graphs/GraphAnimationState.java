package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;

public class GraphAnimationState {
    public String state;
    public String info;
    public ArrayList<GraphElementAnimationData> elementAnimationData;
    public ArrayList<GraphAnimationStateShadow> graphAnimationStateShadow;

    public GraphAnimationState(String state) {
        this.state = state;
        this.elementAnimationData = new ArrayList<>();
        this.graphAnimationStateShadow = new ArrayList<>();
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

    public void add(GraphAnimationStateShadow... graphAnimationStateShadows){
        for(GraphAnimationStateShadow graphAnimationStateShadow : graphAnimationStateShadows)
            this.graphAnimationStateShadow.add(graphAnimationStateShadow);
    }

    @Override
    public String toString() {
        return "GraphAnimationState{" +
                "state='" + state + '\'' +
                ", graphAnimationStateShadow=" + graphAnimationStateShadow +
                '}';
    }
}
