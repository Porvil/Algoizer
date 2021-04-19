package com.iiitd.dsavisualizer.datastructures.graphs;

public class TouchData {
    public float x;
    public float y;
    public int row;
    public int col;
    public boolean isElement;
    public boolean isExtendedElement;

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
