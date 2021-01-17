package com.iiitd.dsavisualizer.algorithms.sorting;

import android.content.Context;
import android.view.View;

import com.iiitd.dsavisualizer.runapp.others.AnimateViews;

import java.util.ArrayList;

// Abstract Class used by Sorting Algorithms, Sub-classes must provide definition for backward() and forward() methods.
public abstract class SortingSequence {

    public int size;
    public int curSeqNo;
    public View[] views;
    public int[] positions;
    public AnimateViews animateViews;
    public ArrayList<AnimationState> animationStates;

    public abstract boolean backward();
    public abstract boolean forward();

    public SortingSequence() {
        this.curSeqNo = 0;
        this.size = 0;
        this.animationStates = new ArrayList<>();
    }

    public void setAnimateViews(float height, float width, Context context) {
        this.animateViews = new AnimateViews(height, width, context);
    }

    public void addAnimSeq(AnimationState animationState){
        animationStates.add(animationState);
        size++;
    }

    public void setViews(View[] views) {
        this.views = views;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public String printAnimationStates() {
        StringBuilder stringBuilder = new StringBuilder();
        for(AnimationState animationState : animationStates){
            stringBuilder.append(animationState.toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "size = " + size + ", curSeqNo = " + curSeqNo + "\n" + printAnimationStates();
    }

}
