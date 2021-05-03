package com.iiitd.dsavisualizer.algorithms.sorting.insertion;

import android.text.Html;
import android.text.Spanned;

// Used for InsertionSort time/space complexities
public class InsertionSortStats {
    static final String name = "Insertion Sort";
    static final Spanned avg = Html.fromHtml("n<sup>2</sup>");
    static final Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static final Spanned best = Html.fromHtml("n");
    static final Spanned space = Html.fromHtml("1");
    static final String stable = "yes";
}