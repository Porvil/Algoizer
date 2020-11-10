package com.iiitd.dsavisualizer.datastructures.trees;

import android.util.Pair;

public class TreeAnimationState {
    public int data;
    public int count;
    public int elementIndex;
    public int row;
    public int col;
    public String info;

    public TreeAnimationState(int data, int count, int elementIndex) {
        this.data = data;
        this.count = count;
        this.elementIndex = elementIndex;
        if(elementIndex != -1) {
            Pair<Integer, Integer> pair = TreeLayout.map.get(elementIndex);
            this.row = pair.first;
            this.col = pair.second;
        }
    }
    public TreeAnimationState(int row, int col, String info) {
        this.row = row;
        this.col = col;
        this.info = info;
    }

    public TreeAnimationState(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "TreeAnimationState{" +
                "data=" + data +
                ", count=" + count +
                ", elementIndex=" + elementIndex +
                ", row=" + row +
                ", col=" + col +
                ", info='" + info + '\'' +
                '}';
    }
}
