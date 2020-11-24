package com.iiitd.dsavisualizer.datastructures.trees;

import android.util.Pair;

public class TreeElementAnimationData {
    public int data;
    public int count;
    public int elementIndex;
    public int newElementIndex;
    public int row;
    public int col;
    public String info;

    public TreeElementAnimationData(int data, int count, int elementIndex) {
        this.data = data;
        this.count = count;
        this.elementIndex = elementIndex;
        if(elementIndex != -1) {
            Pair<Integer, Integer> pair = TreeLayout.map.get(elementIndex);
            this.row = pair.first;
            this.col = pair.second;
        }
    }

    public TreeElementAnimationData(int data, int count, int elementIndex, int newElementIndex) {
        this.data = data;
        this.count = count;
        this.elementIndex = elementIndex;
        this.newElementIndex = newElementIndex;
        if(elementIndex != -1) {
            Pair<Integer, Integer> pair = TreeLayout.map.get(elementIndex);
            this.row = pair.first;
            this.col = pair.second;
        }
    }

    public TreeElementAnimationData(int data, int count, int elementIndex, String info) {
        this.data = data;
        this.count = count;
        this.elementIndex = elementIndex;
        if(elementIndex != -1) {
            Pair<Integer, Integer> pair = TreeLayout.map.get(elementIndex);
            this.row = pair.first;
            this.col = pair.second;
        }
        this.info = info;
    }

    public TreeElementAnimationData(int row, int col, String info) {
        this.row = row;
        this.col = col;
        this.info = info;
    }

    public TreeElementAnimationData(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "TreeAnimationState{" +
                "data=" + data +
                ", count=" + count +
                ", elementIndex=" + elementIndex +
                ", newElementIndex=" + newElementIndex +
                ", row=" + row +
                ", col=" + col +
                ", info='" + info + '\'' +
                '}';
    }
}
