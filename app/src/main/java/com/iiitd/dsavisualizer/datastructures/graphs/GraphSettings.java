package com.iiitd.dsavisualizer.datastructures.graphs;

public class GraphSettings{

    // SMALL GRAPH
    private static final int SMALL_ROWS                     = 7;
    private static final int SMALL_COLS                     = 14;
    private static final int SMALL_NODE_SIZE                = 21;
    private static final int SMALL_NODE_TEXT_SIZE           = 12;
    private static final int SMALL_COORDINATES_TEXT_SIZE    = 6;
    private static final int SMALL_EDGE_WIDTH               = 3;
    private static final int SMALL_EDGE_ARROW_WIDTH         = 2;
    private static final int SMALL_EDGE_WEIGHT_TEXT_SIZE    = 10;

    // LARGE GRAPH
    private static final int LARGE_ROWS                     = 9;
    private static final int LARGE_COLS                     = 18;
    private static final int LARGE_NODE_SIZE                = 16;
    private static final int LARGE_NODE_TEXT_SIZE           = 10;
    private static final int LARGE_COORDINATES_TEXT_SIZE    = 4;
    private static final int LARGE_EDGE_WIDTH               = 2;
    private static final int LARGE_EDGE_ARROW_WIDTH         = 1;
    private static final int LARGE_EDGE_WEIGHT_TEXT_SIZE    = 8;

    public static final int nodeRect              = 100;
    public static final int nodeCircleRadius      = 25;
    public static final int nodeEdgeArrow         = 15;
    public static final int nodeText              = 50;
    public static final int nodeTextCoordinates   = 25;
    public static final float maxX = 2048;

    public static int getNoOfRows(boolean isLargeGraph){
        return isLargeGraph ? LARGE_ROWS : SMALL_ROWS;
    }

    public static int getNoOfCols(boolean isLargeGraph){
        return isLargeGraph ? LARGE_COLS : SMALL_COLS;
    }

    public static int getNodeSize(boolean isLargeGraph){
        return isLargeGraph ? LARGE_NODE_SIZE : SMALL_NODE_SIZE;
    }

    public static int getNodeTextSize(boolean isLargeGraph){
        return isLargeGraph ? LARGE_NODE_TEXT_SIZE : SMALL_NODE_TEXT_SIZE;
    }

    public static int getCoordinatesTextSize(boolean isLargeGraph){
        return isLargeGraph ? LARGE_COORDINATES_TEXT_SIZE : SMALL_COORDINATES_TEXT_SIZE;
    }

    public static int getEdgeWidth(boolean isLargeGraph){
        return isLargeGraph ? LARGE_EDGE_WIDTH : SMALL_EDGE_WIDTH;
    }

    public static int getEdgeArrowWidth(boolean isLargeGraph){
        return isLargeGraph ? LARGE_EDGE_ARROW_WIDTH : SMALL_EDGE_ARROW_WIDTH;
    }

    public static int getEdgeWeightTextSize(boolean isLargeGraph){
        return isLargeGraph ? LARGE_EDGE_WEIGHT_TEXT_SIZE : SMALL_EDGE_WEIGHT_TEXT_SIZE;
    }

}

//    // SMALL GRAPH
//    private static final int SMALL_ROWS                     = 7;
//    private static final int SMALL_COLS                     = 14;
//    private static final int SMALL_NODE_SIZE                = 25;
//    private static final int SMALL_NODE_TEXT_SIZE           = 12;
//    private static final int SMALL_COORDINATES_TEXT_SIZE    = 6;
//    private static final int SMALL_EDGE_WIDTH               = 3;
//    private static final int SMALL_EDGE_ARROW_WIDTH         = 2;
//    private static final int SMALL_EDGE_WEIGHT_TEXT_SIZE    = 10;
//
//    // LARGE GRAPH
//    private static final int LARGE_ROWS                     = 9;
//    private static final int LARGE_COLS                     = 18;
//    private static final int LARGE_NODE_SIZE                = 20;
//    private static final int LARGE_NODE_TEXT_SIZE           = 10;
//    private static final int LARGE_COORDINATES_TEXT_SIZE    = 4;
//    private static final int LARGE_EDGE_WIDTH               = 2;
//    private static final int LARGE_EDGE_ARROW_WIDTH         = 1;
//    private static final int LARGE_EDGE_WEIGHT_TEXT_SIZE    = 8;
