package com.iiitd.dsavisualizer.runapp.others;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public abstract class Sequence {

    int size;
    int curSeqNo;
    Context context;
    View[] views;
    AnimateViews animateViews;
    ArrayList<AnimationState> animationStates;

    public abstract boolean backward();
    public abstract boolean forward();

}
