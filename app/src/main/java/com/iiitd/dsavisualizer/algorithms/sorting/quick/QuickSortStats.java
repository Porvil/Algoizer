package com.iiitd.dsavisualizer.algorithms.sorting.quick;

import android.text.Html;
import android.text.Spanned;

public class QuickSortStats {
    static String name = "MergeSort";
    static Spanned avg = Html.fromHtml("nlog(n)");
    static Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static Spanned best = Html.fromHtml("nlog(n)");
    static Spanned space = Html.fromHtml("1");
    static String stable = "no";
}
