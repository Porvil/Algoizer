package com.iiitd.dsavisualizer.datastructures.trees;

import java.util.ArrayList;

public class TreeSequence {

    public int size;
    public int curSeqNo;
    public ArrayList<TreeElementAnimationData> elementAnimationData;
    public ArrayList<TreeAnimationState> animationStates;

//    public TreeSequence(ArrayList<TreeElementAnimationData> elementAnimationData) {
//        this.curSeqNo = 0;
//        this.size = elementAnimationData.size();
//        this.elementAnimationData = elementAnimationData;
//        this.animationStates = new ArrayList<>();
//    }

    public TreeSequence() {
        this.elementAnimationData = new ArrayList<>();
        this.animationStates = new ArrayList<>();
    }

    public TreeSequence(ArrayList<TreeAnimationState> animationStates) {
        this.animationStates = animationStates;
        this.curSeqNo = 0;
        this.size = animationStates.size();
        this.elementAnimationData = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "TreeSequence{" +
                "size=" + size +
                ", curSeqNo=" + curSeqNo +
                ", animationStates=" + elementAnimationData +
                '}';
    }

    public boolean backward(){
//        if(size <= 1)
//            return false;
//
//        if(curSeqNo == 0)
//            return false;

//        AnimationState old = animationStates.get(curSeqNo-1);
//
//        for(ElementAnimationData elementAnimationData : old.elementAnimationData){
//            ElementAnimationData inverse = ElementAnimationData.reverse(elementAnimationData);
//            for(Pair<AnimationDirection, Integer> inst : inverse.instructions){
//                int index = inverse.index;
//                if(inst.first == AnimationDirection.LEFT){
//                    positions[index] -= inst.second;
//                }
//                else if(inst.first == AnimationDirection.RIGHT){
//                    positions[index] += inst.second;
//                }
//                animateViews.animateInst(views[index], inst.second, inst.first);
//            }
//        }
        curSeqNo--;
        return true;
    }

    public boolean forward(){
//        if(size <= 1)
//            return false;
//
//        if(curSeqNo == size)
//            return false;

//        AnimationState now = animationStates.get(curSeqNo);
//        for(ElementAnimationData elementAnimationData : now.elementAnimationData){
//            for(Pair<AnimationDirection, Integer> inst : elementAnimationData.instructions){
//                int index = elementAnimationData.index;
//                if(inst.first == AnimationDirection.LEFT){
//                    positions[index] -= inst.second;
//                }
//                else if(inst.first == AnimationDirection.RIGHT){
//                    positions[index] += inst.second;
//                }
//                animateViews.animateInst(views[index], inst.second, inst.first);
//            }
//        }
        curSeqNo++;
        return true;
    }

}

