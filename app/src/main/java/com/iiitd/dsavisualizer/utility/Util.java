package com.iiitd.dsavisualizer.utility;

import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortData;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortData;

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

}
