package com.iiitd.dsavisualizer.datastructures.graphs;

import android.graphics.Rect;

public class Data {

    public Rect rect;
    public int row;
    public int column;
    Vertex vertex;
    boolean state;

    public Data() {
        state = false;
        vertex = null;
    }
}
