package com.iiitd.dsavisualizer.runapp.others;

import android.util.Pair;

import java.util.ArrayList;

public class ElementAnimationData {

    int index;
    ArrayList<Pair<String, Integer>> instructions;

    public ElementAnimationData(int index, ArrayList<Pair<String, Integer>> instructions) {
        this.index = index;
        this.instructions = instructions;
    }

    public ElementAnimationData(int index, Pair<String, Integer> ... inst){
        this.index = index;
        this.instructions = new ArrayList<>();

        for (Pair<String, Integer> s:inst) {
            instructions.add(s);
        }
    }

    public void add(Pair<String, Integer>... inst){
        for (Pair<String, Integer> s:inst) {
            instructions.add(s);
        }
    }


    @Override
    public String toString() {
        return index + "->" + instructions.toString();
    }

    // MUST TAKE CARE OF THIS FUNCTION
    public static ElementAnimationData reverse(ElementAnimationData normal){
        ArrayList<Pair<String, Integer>> insts = new ArrayList<>();

        int index = normal.index;

        for(int i=normal.instructions.size()-1;i>=0;i--){
            Pair<String, Integer> s = normal.instructions.get(i);
            if(s.first.equals("L")){
                insts.add(new Pair<>("R", s.second));
            }
            else if(s.first.equals("R")){
                insts.add(new Pair<>("L", s.second));
            }
            else if(s.first.equals("U")){
                insts.add(new Pair<>("B", s.second));
            }
            else if(s.first.equals("B")){
                insts.add(new Pair<>("U", s.second));
            }
            else {
                insts.add(s);
            }
        }


        ElementAnimationData elementAnimationData = new ElementAnimationData(index, insts);

        return elementAnimationData;
    }

}
