package com.iiitd.dsavisualizer.utility;

import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortData;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortData;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortData;

public class Util {

    public static void swap(QuickSortData a, QuickSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    public static void swap(BubbleSortData a, BubbleSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    public static void swap(SelectionSortData a, SelectionSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static double calculateDegree(float x1, float x2, float y1, float y2) {
        float startRadians = (float) Math.atan((y2 - y1) / (x2 - x1));
        startRadians += ((x2 >= x1) ? 90 : -90) * Math.PI / 180;
        return Math.toDegrees(startRadians);
    }
}
