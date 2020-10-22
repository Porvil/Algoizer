package com.iiitd.dsavisualizer.runapp.others;

import android.util.Pair;

import java.util.ArrayList;

public class AnimationState {
    public String state;
    public String info;
    public ArrayList<ElementAnimationData> aldData;
    public ArrayList<Integer> highlightIndexes;
    public ArrayList<Pair<Integer, String>> pointers;

    public AnimationState(String state, String info) {
        this.state = state;
        this.info = info;
        this.aldData = new ArrayList<>();
        this.highlightIndexes = new ArrayList<>();
        this.pointers = new ArrayList<>();
    }

    public void addElementAnimationData(ElementAnimationData elementAnimationData) {
        this.aldData.add(elementAnimationData);
    }

    public void addHighlightIndexes(int... indexes) {
        for(int i : indexes)
            this.highlightIndexes.add(i);
    }

    public void addPointers(Pair<Integer, String> pair){
        this.pointers.add(pair);
    }

    @Override
    public String toString() {
        return state + "|" + aldData.toString();
    }

}
