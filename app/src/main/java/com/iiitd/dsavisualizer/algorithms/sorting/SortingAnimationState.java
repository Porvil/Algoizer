package com.iiitd.dsavisualizer.algorithms.sorting;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

// This class is used to hold complete animation data for an instance of a sorting algorithm
public class SortingAnimationState {
    public final String state;
    public String info;
    public ArrayList<ElementAnimationData> elementAnimationData;
    public ArrayList<Integer> highlightIndexes;
    public ArrayList<Pair<Integer, String>> pointers;

    public SortingAnimationState(String state, String info) {
        this.state = state;
        this.info = info;
        this.elementAnimationData = new ArrayList<>();
        this.highlightIndexes = new ArrayList<>();
        this.pointers = new ArrayList<>();
    }

    public void addElementAnimationData(ElementAnimationData... elementAnimationDatas) {
        this.elementAnimationData.addAll(Arrays.asList(elementAnimationDatas));
    }

    public void addHighlightIndexes(int... indexes) {
        for(int i : indexes)
            this.highlightIndexes.add(i);
    }

    @SafeVarargs
    public final void addPointers(Pair<Integer, String>... pairs){
        this.pointers.addAll(Arrays.asList(pairs));
    }

    @Override
    public String toString() {
        return state + "|" + elementAnimationData.toString();
    }

}