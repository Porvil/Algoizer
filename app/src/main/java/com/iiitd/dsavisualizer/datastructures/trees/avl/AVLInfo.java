package com.iiitd.dsavisualizer.datastructures.trees.avl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//HARDCODED INFORMATION, Handle with care
public class AVLInfo {

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

//    public static final List<Integer> tree1 = Arrays.asList(8, 4, 12, 2);
//    public static final List<Integer> tree1 = Arrays.asList(30,20,40,10);
//    public static final List<Integer> tree1 = Arrays.asList(30,10,40,20);
//    public static final List<Integer> tree1 = Arrays.asList(30,10,40,5,20);
//    public static final List<Integer> tree1 = Arrays.asList(20,10,30,40);
//    public static final List<Integer> tree1 = Arrays.asList(20,10,40,30);
    public static final List<Integer> tree1 = Arrays.asList(20,10,30,25,40);
//    public static final List<Integer> tree1 = Arrays.asList(8,4,14,6,12,42,72);
//    public static final List<Integer> tree1 = Arrays.asList(8, 4, 12, 14);
//    public static final List<Integer> tree1 = Arrays.asList(100, 50, 150, 20, 70, 60, 80, 120, 200, 220, 110, 130);
    public static final List<Integer> tree2 = Arrays.asList(8,2,12,1,6);
//    public static final List<Integer> tree2 = Arrays.asList(100, 150, 120, 200, 220, 110, 130);
    public static final List<Integer> tree3 = Arrays.asList(50, 40, 100, 30, 45, 10, 35, 65);

}
