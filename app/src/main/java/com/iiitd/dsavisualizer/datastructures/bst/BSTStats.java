package com.iiitd.dsavisualizer.datastructures.bst;

import android.text.Html;
import android.text.Spanned;

public class BSTStats {
    static final String name = "Binary Search Tree";
    static final Spanned avg = Html.fromHtml("nlog(n)");
    static final Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static final Spanned best = Html.fromHtml("nlog(n)");
    static final Spanned space = Html.fromHtml("1");
    static final String stable = "no";
}
