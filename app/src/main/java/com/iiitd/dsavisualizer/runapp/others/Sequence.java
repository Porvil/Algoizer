package com.iiitd.dsavisualizer.runapp.others;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public abstract class Sequence {

    public int size;
    public int curSeqNo;
    public Context context;
    public View[] views;
    public int[] positions;
    public AnimateViews animateViews;
    public ArrayList<AnimationState> animationStates;

    public abstract boolean backward();
    public abstract boolean forward();

}
