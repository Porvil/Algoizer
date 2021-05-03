package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import java.util.HashMap;

//HARDCODED INFORMATION, Handle with care
public class MergeSortInfo {

    /*0*/  public static final String MS = "Merge Sort";
    /*4*/  public static final String LS = "Sorting Left Half";
    /*14*/ public static final String LS_U = "Sorting Left Half Done";
    /*5*/  public static final String RS = "Sorting Right Half";
    /*14*/ public static final String RS_U = "Sorting Right Half Done";
    /*11*/ public static final String L_LESSEQUAL_R = "Left <= Right";
    /*13*/ public static final String L_GREATER_R = "Left > Right";
    /*11*/ public static final String L_EXTRAS = "Left Extras";
    /*13*/ public static final String R_EXTRAS = "Right Extras";
    /*9*/  public static final String MERGE_STARTED = "Merge arrays, Array copy for merging";
    /*1*/  public static final String SINGLE_MERGE = "Single element is always sorted";

    public static final HashMap<String, Integer[]> map = new HashMap<>();

    static {
        map.put(MS, new Integer[]{0});
        map.put(LS, new Integer[]{4});
        map.put(LS_U, new Integer[]{19});
        map.put(RS, new Integer[]{5});
        map.put(RS_U, new Integer[]{19});
        map.put(L_LESSEQUAL_R, new Integer[]{11, 12});
        map.put(L_GREATER_R, new Integer[]{13, 14});
        map.put(L_EXTRAS, new Integer[]{15, 16});
        map.put(R_EXTRAS, new Integer[]{17, 18});
        map.put(MERGE_STARTED, new Integer[]{8, 9});
        map.put(SINGLE_MERGE, new Integer[]{1, 2});
    }

    public static final int[] boldIndexes = new int[]{0, 8};

    public static final String[] psuedocode = new String[]{
    /*0*/         "mergeSort(data, start, end):",
    /*1*/         "    if start > end",
    /*2*/         "        return",
    /*3*/         "    mid = (start+end)/2",
    /*4*/         "    mergeSort(data, start, mid)",
    /*5*/         "    mergeSort(data, mid+1, end)",
    /*6*/         "    merge(data, start, mid, end)",
    /*7*/         "",
    /*8*/         "merge(data, start, mid, end)",
    /*9*/         "Copy both arrays into 2 temporary arrays",
    /*10*/        "while(both arrays are not empty):",
    /*11*/        "    if(left array's element <= right array's element):",
    /*12*/        "        copy left array's element to final sorted array",
    /*13*/        "    else:",
    /*14*/        "        copy right array's element to final sorted array",
    /*15*/        "while(left array is not empty):",
    /*16*/        "    copy left array's element to final sorted array",
    /*17*/        "while(right array is not empty):",
    /*18*/        "    copy right array's element to final sorted array",
    /*19*/        "return",
    /*20*/        ""
    };


       public static String getComparedString(int a, int b){
           if(a <= b){
               return a + " <= " + b + ", copy left array's element(" + a + ") to final array";
           }

           return a + " > " + b + ", copy right array's element(" + b + ") to final array";
       }

       public static String getRemainingElementString(int element, boolean isLeftSide){
           if(isLeftSide){
               return "copy remaining left array's elements(" + element + ") to final array";
           }

           return "copy remaining right array's elements(" + element + ") to final array";
       }

       public static String getMergeSortString(int left, int right){
           return "mergeSort(data, " + left + ", " + right + ")";
       }

       public static String getMergeString(int left, int mid, int right){
           return "merge(data, " + left + ", " + mid + ", " + right + ")";
       }

}