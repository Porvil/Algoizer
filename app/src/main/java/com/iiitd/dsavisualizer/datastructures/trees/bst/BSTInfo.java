package com.iiitd.dsavisualizer.datastructures.trees.bst;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//HARDCODED INFORMATION, Handle with care
public class BSTInfo {

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

    public static final List<Integer> tree1 = Arrays.asList(100, 50, 150, 20, 70, 60, 80, 120, 200, 220, 110, 130);
    public static final List<Integer> tree2 = Arrays.asList(100, 150, 120, 200, 220, 110, 130);
    public static final List<Integer> tree3 = Arrays.asList(50, 40, 100, 30, 45, 10, 35, 65);

    public static String getInsertString(int key, int count){
        return count == 1 ? "Inserting : " + key : "Increasing count of " + key + " -> " + count;
    }

    public static String getDeleteString(int key, int count){
        return count == 1 ? "Deleting : " + key : "Decreasing count of " + key + " -> " + count;
    }

    public static String getNotFoundString(int key){
        return "Node : " + key + " not found in tree";
    }

    public static String getFoundString(int key){
        return "Node : " + key + " found in tree";
    }

    public static String getNoSpaceString(){
        return "No Space in tree :(";
    }

    public static String getRightSubtreeString(){
        return "Move Right Subtree Up";
    }

    public static String getLeftSubtreeString(){
        return "Move Left Subtree Up";
    }

    public static String getFindSuccessorString(int key){
        return "Finding Successor of " + key + " in right subtree";
    }

    public static String getFoundSuccessorString(int key, int foundKey){
        return "Successor of " + key + " is " + foundKey + " in right subtree";
    }

    public static String getSearchString(int key, int curKey){
        if(key < curKey){
            return key + " < " + curKey + ", recurse on left subtree";
        }
        else {
            return key + " > " + curKey + ", recurse on right subtree";
        }
    }
}
