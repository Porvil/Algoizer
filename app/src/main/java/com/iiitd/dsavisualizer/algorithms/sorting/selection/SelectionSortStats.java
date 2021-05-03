package com.iiitd.dsavisualizer.algorithms.sorting.selection;

import android.text.Html;
import android.text.Spanned;

// Used for SelectionSort time/space complexities
public class SelectionSortStats {
    static final String name = "Selection Sort";
    static final Spanned avg = Html.fromHtml("n<sup>2</sup>");
    static final Spanned worst = Html.fromHtml("n<sup>2</sup>");
    static final Spanned best = Html.fromHtml("n<sup>2</sup>");
    static final Spanned space = Html.fromHtml("1");
    static final String stable = "no";
}