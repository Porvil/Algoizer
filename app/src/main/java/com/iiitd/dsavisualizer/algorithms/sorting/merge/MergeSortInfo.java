package com.iiitd.dsavisualizer.algorithms.sorting.merge;

public class MergeSortInfo {

    public static final String LS = "Sorting Left Half";
    public static final String RS = "Sorting Right Half";

    public static final String[] psuedocode = new String[]{
            "MergeSort(data, start, end):",
            "    if start > end",
            "        return",
            "    mid = (start+end)/2",
            "    mergeSort(data, start, mid)",
            "    mergeSort(data, mid+1, end)",
            "    merge(data, start, mid, end)",
            "",
            "merge(data, start, mid, end)",
            "Copy both arrays into 2 temporary arrays",
            "While(any element is left in any array):",
            "    if(left array's element <= right array's element):",
            "        copy left array's element to final sorted array",
            "    else:",
            "        copy right array's element to final sorted array"
    };
}
