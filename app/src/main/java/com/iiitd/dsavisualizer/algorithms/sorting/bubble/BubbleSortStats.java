package com.iiitd.dsavisualizer.algorithms.sorting.bubble;

import android.text.Html;
import android.text.Spanned;

// Used for BubbleSort time/space complexities
public class BubbleSortStats {
    static final String name = "Bubble Sort";
    static final Spanned avg = Html.fromHtml("n<sup>2</sup>");
    static final Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static final Spanned best = Html.fromHtml("n");
    static final Spanned space = Html.fromHtml("1");
    static final String stable = "yes";
}