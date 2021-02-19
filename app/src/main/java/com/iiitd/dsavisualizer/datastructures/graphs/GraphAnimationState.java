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

    public GraphAnimationState() {
        this.graphAnimationStateShadow = new ArrayList<>();
    }

    public static GraphAnimationState create(){
        return new GraphAnimationState();
    }

    public GraphAnimationState setState(String state){
        this.state = state;
        return this;
    }

    public GraphAnimationState setInfo(String info){
        this.info = info;
        return this;
    }

    public GraphAnimationState addGraphAnimationStateShadow(GraphAnimationStateShadow graphAnimationStateShadow){
        this.graphAnimationStateShadow.add(graphAnimationStateShadow);
        return this;
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
