package com.iiitd.dsavisualizer.datastructures.trees.bst;

public class BSTNode {

    int key;
    int count;
    BSTNode left;
    BSTNode right;

    public BSTNode(int key) {
        this.key = key;
        this.count = 1;
        this.left = null;
        this.right = null;
    }

}
