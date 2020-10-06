package com.iiitd.dsavisualizer.runapp.others;

import java.util.ArrayList;

public class AnimationState {
    public String state;
    public String info;
    public ArrayList<Integer> lineNumbers;
    public ArrayList<ElementAnimationData> aldDatta;

    public AnimationState(String state, ArrayList<ElementAnimationData> aldDatta) {
        this.state = state;
        this.aldDatta = aldDatta;
        this.lineNumbers = new ArrayList<>();
    }

    public AnimationState(String state, String info) {
        this.state = state;
        this.info = info;
        this.aldDatta = new ArrayList<>();
        this.lineNumbers = new ArrayList<>();
    }

    public void add(ElementAnimationData elementAnimationData) {
        this.aldDatta.add(elementAnimationData);
    }

    public void addLineNumbers(int... lineNumbers){
        for(int i:lineNumbers)
            this.lineNumbers.add(i);
    }

    @Override
    public String toString() {
        return state + "|" + aldDatta.toString();
    }

}
