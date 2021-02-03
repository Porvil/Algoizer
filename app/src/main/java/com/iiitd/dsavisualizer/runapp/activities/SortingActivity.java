package com.iiitd.dsavisualizer.runapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sorting);
        context = this;

        linearLayout = findViewById(R.id.ll_parent);

        int width = (int) UtilUI.dpToPx(context, 250);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,
                ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0; i< activityItemData.length; i++){
            final ActivityItemData activityItemData = this.activityItemData[i];
            View view = getLayoutInflater().inflate(R.layout.layout_sortingitem, null);
            ImageView imageView = view.findViewById(R.id.iv_sorticon);
            TextView textView = view.findViewById(R.id.tv_sortname);

            imageView.setImageDrawable(ContextCompat.getDrawable(context, activityItemData.drawable));
            textView.setText(activityItemData.text);

            layoutParams.setMargins(5,5,5,5);
            view.setLayoutParams(layoutParams);

            linearLayout.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Class<?> aClass = Class.forName(activityItemData.activityClassName);
                        startActivity(new Intent(context, aClass));
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

}
