package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;

public class GraphAnimationState {
    public String state;
    public String info;
    public ArrayList<GraphAnimationStateShadow> graphAnimationStateShadow;

    public GraphAnimationState(String state) {
        this.state = state;
        this.graphAnimationStateShadow = new ArrayList<>();
    }

    public GraphAnimationState(String state, String info) {
        this.state = state;
        this.info = info;
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
