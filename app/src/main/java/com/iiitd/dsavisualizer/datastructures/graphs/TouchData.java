package com.iiitd.dsavisualizer.datastructures.graphs;

// TouchData class contains data about the area where used has pressed in graph activity's canvas
public class TouchData {
    public float x;
    public float y;
    public int row;
    public int col;
    public boolean isElement;               // element is inside normal boundaries
    public boolean isExtendedElement;       // element is not inside normal boundaries but inside extended boundaries

    @Override
    public String toString() {
        return "TouchData{" +
                "x=" + x +
                ", y=" + y +
                ", row=" + row +
                ", col=" + col +
                ", isElement=" + isElement +
                ", isExtendedElement=" + isExtendedElement +
                '}';
    }

}