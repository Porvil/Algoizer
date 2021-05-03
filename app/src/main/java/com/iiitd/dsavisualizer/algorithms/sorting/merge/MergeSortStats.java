package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.text.Html;
import android.text.Spanned;

// Used for MergeSort time/space complexities
public class MergeSortStats {
    static final String name = "Merge Sort";
    static final Spanned avg = Html.fromHtml("nlog(n)");
    static final Spanned worst = Html.fromHtml("nlog(n)");
    static final Spanned best = Html.fromHtml("nlog(n)");
    static final Spanned space = Html.fromHtml("n");
    static final String stable = "yes";
}