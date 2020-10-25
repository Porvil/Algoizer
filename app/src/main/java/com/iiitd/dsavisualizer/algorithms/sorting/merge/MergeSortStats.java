package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.text.Html;
import android.text.Spanned;

public class MergeSortStats {
    static String name = "MergeSort";
    static Spanned avg = Html.fromHtml("nlog(n)");
    static Spanned worst = Html.fromHtml("nlog(n)");
    static Spanned best = Html.fromHtml("nlog(n)");
    static Spanned space = Html.fromHtml("n");
    static String stable = "yes";
}
