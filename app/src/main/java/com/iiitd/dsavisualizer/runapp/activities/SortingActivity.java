package com.iiitd.dsavisualizer.runapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.insertion.InsertionSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.merge.MergeSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortActivity;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.runapp.others.ActivityItemData;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class SortingActivity extends AppCompatActivity {

    Context context;
    LinearLayout linearLayout;

    ActivityItemData[] activityItemData = new ActivityItemData[]{
            new ActivityItemData(BubbleSortActivity.class.getName(), "BubbleSort", R.drawable.ic_bubblesort),
            new ActivityItemData(SelectionSortActivity.class.getName(), "SelectionSort", R.drawable.ic_selectionsort),
            new ActivityItemData(InsertionSortActivity.class.getName(), "InsertionSort", R.drawable.ic_insertionsort),
            new ActivityItemData(MergeSortActivity.class.getName(), "MergeSort", R.drawable.ic_mergesort),
            new ActivityItemData(QuickSortActivity.class.getName(), "QuickSort", R.drawable.ic_quicksort)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_sortings);
        context = this;

        linearLayout = findViewById(R.id.ll_parent);

        int width = (int) UtilUI.dpToPx(context, AppSettings.ACTIVITY_ITEM_WIDTH);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,
                ViewGroup.LayoutParams.MATCH_PARENT);

        for (final ActivityItemData activityItemData : activityItemData) {
            View view = getLayoutInflater().inflate(R.layout.layout_activity_item, null);
            ImageView imageView = view.findViewById(R.id.iv_sorticon);
            TextView textView = view.findViewById(R.id.tv_sortname);

            imageView.setImageDrawable(ContextCompat.getDrawable(context, activityItemData.drawable));
            textView.setText(activityItemData.text);

            layoutParams.setMargins(15, 0, 15, 0);
            view.setLayoutParams(layoutParams);

            linearLayout.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Class<?> aClass = Class.forName(activityItemData.activityClassName);
                        startActivity(new Intent(context, aClass));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

}
