package com.iiitd.dsavisualizer.datastructures.trees;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.iiitd.dsavisualizer.datastructures.trees.NodeType.ARROW;
import static com.iiitd.dsavisualizer.datastructures.trees.NodeType.ELEMENT;
import static com.iiitd.dsavisualizer.datastructures.trees.NodeType.EMPTY;

// Used by Trees
//HARDCODED INFORMATION, Handle with care
public class TreeLayout {

    public static final int baseIndex = 8;
    public static final int baseLevel = 4;
    public static ArrayList<List<TreeLayoutElement>> treeLayout = new ArrayList<>();
    public static HashMap<Integer, Pair<Integer, Integer>> map = new HashMap<>();

    static {
        //Row 0
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(7, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(7, EMPTY)));

        //Row 1
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(3, EMPTY),
                new TreeLayoutElement(4, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(4, ARROW),
                new TreeLayoutElement(3, EMPTY)));

        //Row 2
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(3, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(7, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(3, EMPTY)));

        //Row 3
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(2, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(2, ARROW),
                new TreeLayoutElement(3, EMPTY),
                new TreeLayoutElement(2, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(2, ARROW),
                new TreeLayoutElement(1, EMPTY)));

        //Row 4
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(3, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(3, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(3, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY)));

        //Row 5
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ARROW)));

        //Row 6
        treeLayout.add(Arrays.asList(
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT),
                new TreeLayoutElement(1, EMPTY),
                new TreeLayoutElement(1, ELEMENT)));

        // Element Index(1-Indexed), Pair<Row, Column>
        map.put(8, new Pair<>(0, 1));
        map.put(4, new Pair<>(2, 1));
        map.put(12, new Pair<>(2, 3));
        map.put(2, new Pair<>(4, 1));
        map.put(6, new Pair<>(4, 3));
        map.put(10, new Pair<>(4, 5));
        map.put(14, new Pair<>(4, 7));
        map.put(1, new Pair<>(6, 0));
        map.put(3, new Pair<>(6, 2));
        map.put(5, new Pair<>(6, 4));
        map.put(7, new Pair<>(6, 6));
        map.put(9, new Pair<>(6, 8));
        map.put(11, new Pair<>(6, 10));
        map.put(13, new Pair<>(6, 12));
        map.put(15, new Pair<>(6, 14));
    }

    // 0 is redundant and -1 == no child
    public static Pair<Integer, Integer>[] childs = new Pair[]{
        new Pair(0,0),
        new Pair(-1,-1),
        new Pair(1,3),
        new Pair(-1,-1),
        new Pair(2,6),
        new Pair(-1,-1),
        new Pair(5,7),
        new Pair(-1,-1),
        new Pair(4,12),
        new Pair(-1,-1),
        new Pair(9,11),
        new Pair(-1,-1),
        new Pair(10,14),
        new Pair(-1,-1),
        new Pair(13,15),
        new Pair(-1,-1)
    };

    // 0 is redundant, -1 = no parent(root)
    public static int[] parents = new int[]{
            0,
            2,
            4,
            2,
            8,
            6,
            4,
            6,
            -1,
            10,
            12,
            10,
            8,
            14,
            12,
            14
    };

}