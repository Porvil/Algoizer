package com.iiitd.dsavisualizer.algorithms.sorting;

import android.view.View;

import com.iiitd.dsavisualizer.runapp.others.AnimateViews;

import java.util.ArrayList;

public abstract class SortingSequence {

    public int size;
    public int curSeqNo;
    public View[] views;
    public int[] positions;
    public AnimateViews animateViews;
    public ArrayList<AnimationState> animationStates;

    public abstract boolean backward();
    public abstract boolean forward();

}
