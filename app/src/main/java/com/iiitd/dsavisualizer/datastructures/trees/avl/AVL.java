package com.iiitd.dsavisualizer.datastructures.trees.avl;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;

import java.util.ArrayList;

public class AVL {

    private AVLNode root;
    private final int baseIndex = 8;
    private final int baseLevel = 4;
    public TreeSequence treeSequence;
    private ArrayList<TreeAnimationState> treeAnimationStates;

    public AVL() {
        root = null;
        treeSequence = new TreeSequence();
    }

}
