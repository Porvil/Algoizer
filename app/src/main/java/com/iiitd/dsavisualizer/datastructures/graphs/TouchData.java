package com.iiitd.dsavisualizer.datastructures.graphs;

import android.graphics.Rect;

public class TouchData {
    public float x;
    public float y;
    public int row;
    public int col;

//    Rect rect;
    boolean isElement;

    public static TouchData getInstance(int row, int col){
        TouchData touchData = new TouchData();
//        touchData.row =
        return  touchData;
    }

    @Override
    public String toString() {
        return "TouchData{" +
                "x=" + x +
                ", y=" + y +
                ", row=" + row +
                ", col=" + col +
//                ", rect=" + rect +
                ", isElement=" + isElement +
                '}';
    }
}
