package com.iiitd.dsavisualizer.runapp.others;

import java.util.ArrayList;

public class AnimationState {
    String info;
    ArrayList<ElementAnimationData> aldDatta;

    public AnimationState(String info, ArrayList<ElementAnimationData> aldDatta) {
        this.info = info;
        this.aldDatta = aldDatta;
    }

    public AnimationState(String info) {
        this.info = info;
        this.aldDatta = new ArrayList<>();
    }

    public void add(ElementAnimationData elementAnimationData) {
        this.aldDatta.add(elementAnimationData);
    }

    @Override
    public String toString() {
        return info + "|" + aldDatta.toString();
    }

}
