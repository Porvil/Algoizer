package com.iiitd.dsavisualizer.runapp.others;

import android.view.View;

import java.util.ArrayList;

public abstract class Sequence {

    public int size;
    public int curSeqNo;
    public View[] views;
    public int[] positions;
    public AnimateViews animateViews;
    public ArrayList<AnimationState> animationStates;

    public abstract boolean backward();
    public abstract boolean forward();

}
