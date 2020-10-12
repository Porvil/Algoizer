package com.iiitd.dsavisualizer.runapp.others;

import java.util.ArrayList;

public class AnimationState {
    public String state;
    public String info;
    public ArrayList<ElementAnimationData> aldData;
    public ArrayList<Integer> highlightIndexes;

    public AnimationState(String state, String info) {
        this.state = state;
        this.info = info;
        this.aldData = new ArrayList<>();
        this.highlightIndexes = new ArrayList<>();
    }

    public void addElementAnimationData(ElementAnimationData elementAnimationData) {
        this.aldData.add(elementAnimationData);
    }

    public void addHighlightIndexes(int... indexes) {
        for(int i : indexes)
            this.highlightIndexes.add(i);
    }

    @Override
    public String toString() {
        return state + "|" + aldData.toString();
    }

}
