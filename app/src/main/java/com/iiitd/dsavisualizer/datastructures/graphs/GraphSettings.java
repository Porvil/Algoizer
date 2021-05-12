package com.iiitd.dsavisualizer.datastructures.graphs;

// Contains GraphSettings
public class GraphSettings{
    // cols = 2 * rows
    // SMALL GRAPH
    private static final int SMALL_ROWS           = 6;
    private static final int SMALL_COLS           = 12;

    // LARGE GRAPH
    private static final int LARGE_ROWS           = 8;
    private static final int LARGE_COLS           = 16;

    // nodeCircleRadius + nodeEdgeArrowLength == 50
    public static final int nodeRect              = 100;
    public static final int nodeCircleRadius      = 35;
    public static final int nodeEdge              = 10;
    public static final int nodeEdgeArrow         = 5;
    public static final int nodeEdgeArrowLength   = 15;
    public static final int nodeEdgeWeight        = 40;
    public static final int nodeText              = 35;
    public static final int nodeTextCoordinates   = 20;
    public static final float maxX = 2048; // Limit in pixels

    // Zoom Constants
    public static final float defZoom = 1f;

    public static int getNoOfRows(boolean isLargeGraph){
        return isLargeGraph ? LARGE_ROWS : SMALL_ROWS;
    }

    public static int getNoOfCols(boolean isLargeGraph){
        return isLargeGraph ? LARGE_COLS : SMALL_COLS;
    }

}