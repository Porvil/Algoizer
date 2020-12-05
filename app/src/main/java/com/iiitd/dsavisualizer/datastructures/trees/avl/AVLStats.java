package com.iiitd.dsavisualizer.datastructures.trees.avl;

import android.text.Html;
import android.text.Spanned;

public class AVLStats {
    static final String name = "AVL Tree";
    static final Spanned avg = Html.fromHtml("nlog(n)");
    static final Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static final Spanned best = Html.fromHtml("nlog(n)");
    static final Spanned space = Html.fromHtml("1");
    static final String stable = "no";
}
