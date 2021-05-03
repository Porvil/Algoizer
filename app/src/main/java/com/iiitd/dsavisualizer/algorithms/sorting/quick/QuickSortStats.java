package com.iiitd.dsavisualizer.algorithms.sorting.quick;

import android.text.Html;
import android.text.Spanned;

// Used for QuickSort time/space complexities
public class QuickSortStats {
    static final String name = "Quick Sort";
    static final Spanned avg = Html.fromHtml("nlog(n)");
    static final Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static final Spanned best = Html.fromHtml("nlog(n)");
    static final Spanned space = Html.fromHtml("1");
    static final String stable = "no";
}