package com.iiitd.dsavisualizer.datastructures.trees.bst;

import java.util.Arrays;
import java.util.List;

//HARDCODED INFORMATION, Handle with care
public class BSTInfo {

    public static final List<Integer> tree1 = Arrays.asList(100, 50, 150, 20, 70, 60, 80, 120, 200, 220, 110, 130);
    public static final List<Integer> tree2 = Arrays.asList(100, 150, 120, 200, 220, 110, 130);
    public static final List<Integer> tree3 = Arrays.asList(50, 40, 100, 30, 45, 10, 35, 65);

    public static String getInsertString(int key, int count){
        return count == 1 ? "inserting : " + key : "increasing count of " + key + " -> " + count;
    }

    public static String getDeleteString(int key, int count){
        return count == 1 ? "deleting : " + key : "decreasing count of " + key + " -> " + count;
    }

    public static String getNotFoundString(int key){
        return "node : " + key + " not found in tree";
    }

    public static String getFoundString(int key){
        return "node : " + key + " found in tree";
    }

    public static String getNoSpaceString(){
        return "no space in tree :(";
    }

    public static String getMoveUpString(int key, int oldKey){
        return "move successor " + key + " of " + oldKey + " up";
    }

    public static String getOrderTraversalString(String type){
        return type + " order traversal";
    }

    public static String getRightSubtreeString(){
        return "move right subtree Up";
    }

    public static String getLeftSubtreeString(){
        return "move left subtree Up";
    }

    public static String getFindSuccessorString(int key){
        return "finding successor of " + key + " in right subtree";
    }

    public static String getFoundSuccessorString(int key, int foundKey){
        return "successor of " + key + " is " + foundKey + " in right subtree";
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