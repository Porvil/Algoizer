package com.iiitd.dsavisualizer.datastructures.trees.bst;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//HARDCODED INFORMATION, Handle with care
public class BSTInfo {

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

    public static String getMoveUpString(int key, int oldKey){
        return "Move successor " + key + " of " + oldKey + " up";
    }

    public static String getOrderTraversalString(String type){
        return type + " Order Traversal";
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
