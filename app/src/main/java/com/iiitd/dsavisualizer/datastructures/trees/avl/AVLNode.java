package com.iiitd.dsavisualizer.datastructures.trees.avl;

public class AVLNode {

    int key;
    int count;
    int height;
    AVLNode left;
    AVLNode right;

    AVLNode(int key) {
        this.key = key;
        this.count = 1;
        this.height = 1;
        this.left = null;
        this.right = null;
    }

}
