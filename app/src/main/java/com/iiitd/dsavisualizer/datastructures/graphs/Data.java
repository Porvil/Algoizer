package com.iiitd.dsavisualizer.datastructures.graphs;

import android.graphics.Rect;

public class Data {

    public Rect rect;
    public int row;
    public int column;
    public int data;
//    VertexOld vertexOld;
    boolean state;

    public Data() {
        state = false;
        data = -1;
//        vertexOld = null;
    }
}
