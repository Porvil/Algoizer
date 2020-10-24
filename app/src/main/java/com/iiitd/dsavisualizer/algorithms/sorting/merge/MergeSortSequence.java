package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.content.Context;
import android.util.Pair;
import android.view.View;

import com.iiitd.dsavisualizer.runapp.others.AnimateViews;
import com.iiitd.dsavisualizer.runapp.others.AnimationDirection;
import com.iiitd.dsavisualizer.runapp.others.AnimationState;
import com.iiitd.dsavisualizer.runapp.others.ElementAnimationData;
import com.iiitd.dsavisualizer.runapp.others.Sequence;

import java.util.ArrayList;

public class MergeSortSequence extends Sequence {

    public MergeSortSequence(int curSeqNo) {
        this.curSeqNo = curSeqNo;
        size = 0;
        this.animationStates = new ArrayList<>();
    }

    public void setAnimateViews(int height, int width, Context context) {
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

    @Override
    public String toString() {
        return curSeqNo + "\n" + call();
    }

    private String call() {
        StringBuilder stringBuilder = new StringBuilder();
        for(AnimationState s : animationStates){
            stringBuilder.append(s.toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean backward(){
        if(size <= 1)
            return false;

        if(curSeqNo == 0)
            return false;

        AnimationState old = animationStates.get(curSeqNo-1);

        for(ElementAnimationData elementAnimationData : old.elementAnimationData){
            ElementAnimationData inverse = ElementAnimationData.reverse(elementAnimationData);
            for(Pair<AnimationDirection, Integer> inst : inverse.instructions){
                int index = inverse.index;
                if(inst.first == AnimationDirection.LEFT){
                    positions[index] -= inst.second;
                }
                else if(inst.first == AnimationDirection.RIGHT){
                    positions[index] += inst.second;
                }
                animateViews.animateInst(views[index], inst.second, inst.first);
            }
        }
        curSeqNo--;
        return true;
    }

    @Override
    public boolean forward(){
        if(size <= 1)
            return false;

        if(curSeqNo == size)
            return false;

        AnimationState now = animationStates.get(curSeqNo);
        for(ElementAnimationData elementAnimationData : now.elementAnimationData){
            for(Pair<AnimationDirection, Integer> inst : elementAnimationData.instructions){
                int index = elementAnimationData.index;
                if(inst.first == AnimationDirection.LEFT){
                    positions[index] -= inst.second;
                }
                else if(inst.first == AnimationDirection.RIGHT){
                    positions[index] += inst.second;
                }
                animateViews.animateInst(views[index], inst.second, inst.first);
            }
        }
        curSeqNo++;
        return true;
    }

}
