package com.iiitd.dsavisualizer.datastructures.trees.bst;

import android.text.Html;
import android.text.Spanned;

// Used for BST time/space complexities
public class BSTStats {
    static final String name = "Binary Search Tree";
    static final Spanned worst_insert = Html.fromHtml("n");
    static final Spanned best_insert = Html.fromHtml("log(n)");
    static final Spanned worst_search = Html.fromHtml("n");
    static final Spanned best_search = Html.fromHtml("log(n)");
    static final Spanned worst_delete = Html.fromHtml("n");
    static final Spanned best_delete = Html.fromHtml("log(n)");
    static final Spanned space = Html.fromHtml("N, no of nodes in tree");
    static final Spanned traversals = Html.fromHtml("N, no. of nodes in tree");
}