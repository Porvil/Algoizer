package com.iiitd.dsavisualizer.runapp.others;

import com.iiitd.dsavisualizer.R;

// Contains OnBoardingInfo for tutorials
public class OnBoardingInfo {

    // if more than MAX_PAGES are passed to viewpager, it will crash
    public static final int MAX_PAGES = 10;

    public static final int[] sortingOnBoarding = {
            R.drawable.ob_sorting_left,
            R.drawable.ob_sorting_right1
    };

    public static final int[] treeOnBoarding = {
            R.drawable.ob_tree_left,
            R.drawable.ob_tree_right1,
            R.drawable.ob_tree_right2
    };

    public static final int[] graphOnBoarding = {
            R.drawable.ob_graph_input,
            R.drawable.ob_graph_right,
            R.drawable.ob_graph_left,
            R.drawable.ob_graph_example
    };

}