package com.iiitd.dsavisualizer.algorithms.sorting.insertion;

import android.util.Pair;

import com.iiitd.dsavisualizer.algorithms.sorting.SortingAnimationState;
import com.iiitd.dsavisualizer.algorithms.sorting.ElementAnimationData;
import com.iiitd.dsavisualizer.algorithms.sorting.SortingSequence;
import com.iiitd.dsavisualizer.algorithms.sorting.AnimationDirection;

// InsertionSort Sequence used by InsertionSort
public class InsertionSortSortingSequence extends SortingSequence {

    @Override
    public boolean backward(){
        if(size <= 0)
            return false;

        if(curSeqNo == 0)
            return false;

        SortingAnimationState old = sortingAnimationStates.get(curSeqNo-1);

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
        if(size <= 0)
            return false;

        if(curSeqNo == size)
            return false;

        SortingAnimationState now = sortingAnimationStates.get(curSeqNo);
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