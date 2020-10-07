package com.iiitd.dsavisualizer.runapp.others;

import android.util.Pair;

import java.util.ArrayList;

public class ElementAnimationData {

    public int index;
    public ArrayList<Pair<AnimationDirection, Integer>> instructions;

    public ElementAnimationData(int index, ArrayList<Pair<AnimationDirection, Integer>> instructions) {
        this.index = index;
        this.instructions = instructions;
    }

    public ElementAnimationData(int index, Pair<AnimationDirection, Integer> ... inst){
        this.index = index;
        this.instructions = new ArrayList<>();

        for (Pair<AnimationDirection, Integer> s:inst) {
            instructions.add(s);
        }
    }

    public void add(Pair<AnimationDirection, Integer>... inst){
        for (Pair<AnimationDirection, Integer> s:inst) {
            instructions.add(s);
        }
    }


    @Override
    public String toString() {
        return index + "->" + instructions.toString();
    }

    // MUST TAKE CARE OF THIS FUNCTION
    public static ElementAnimationData reverse(ElementAnimationData normal){
        ArrayList<Pair<AnimationDirection, Integer>> insts = new ArrayList<>();

        int index = normal.index;

        for(int i=normal.instructions.size()-1;i>=0;i--){
            Pair<AnimationDirection, Integer> s = normal.instructions.get(i);
            switch (s.first){
                case NULL:
                    insts.add(s);
                    break;
                case UP:
                    insts.add(new Pair<>(AnimationDirection.DOWN, s.second));
                    break;
                case RIGHT:
                    insts.add(new Pair<>(AnimationDirection.LEFT, s.second));
                    break;
                case DOWN:
                    insts.add(new Pair<>(AnimationDirection.UP, s.second));
                    break;
                case LEFT:
                    insts.add(new Pair<>(AnimationDirection.RIGHT, s.second));
                    break;
                default:
                    System.out.println("NO ANIMATION STATE FOUND -__-");
                    break;
            }
        }

        ElementAnimationData elementAnimationData = new ElementAnimationData(index, insts);
        return elementAnimationData;
    }

}
