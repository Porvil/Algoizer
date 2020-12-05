package com.iiitd.dsavisualizer.datastructures.trees.avl;

public class AVLNode {

    int key;
    int count;
    AVLNode left;
    AVLNode right;

    public AVLNode(int key) {
        this.key = key;
        this.count = 1;
        this.left = null;
        this.right = null;
    }

}
