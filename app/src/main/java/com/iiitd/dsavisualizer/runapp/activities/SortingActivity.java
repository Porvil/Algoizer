package com.iiitd.dsavisualizer.runapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.insertion.InsertionSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.merge.MergeSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortActivity;

public class SortingActivity extends AppCompatActivity {

    Context context;
    View[] views;
    LinearLayout ll_sorting_row1;
    LinearLayout ll_sorting_row2;

    SortingData[] sortingData = new SortingData[]{
            new SortingData(BubbleSortActivity.class.getName(), "BubbleSort", R.drawable.ic_bubblesort),
            new SortingData(SelectionSortActivity.class.getName(), "SelectionSort", R.drawable.ic_selectionsort),
            new SortingData(InsertionSortActivity.class.getName(), "InsertionSort", R.drawable.ic_insertionsort),
            new SortingData(MergeSortActivity.class.getName(), "MergeSort", R.drawable.ic_mergesort),
            new SortingData(QuickSortActivity.class.getName(), "QuickSort", R.drawable.ic_quicksort),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        context = this;

        views = new View[6];

        ll_sorting_row1 = findViewById(R.id.ll_sorting_row1);
        ll_sorting_row2 = findViewById(R.id.ll_sorting_row2);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);

        for(int i=0;i<sortingData.length;i++){
            final SortingData sortingData = this.sortingData[i];
            View view = getLayoutInflater().inflate(R.layout.layout_sortingitem, null);
            ImageView imageView = view.findViewById(R.id.iv_sorticon);
            TextView textView = view.findViewById(R.id.tv_sortname);

            imageView.setImageDrawable(getResources().getDrawable(sortingData.drawable));
            textView.setText(sortingData.text);

            layoutParams.setMargins(5,5,5,5);
            view.setLayoutParams(layoutParams);
            views[i] = view;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Class<?> aClass = Class.forName(sortingData.activity);
                        startActivity(new Intent(context, aClass));
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        Space space = new Space(this);
        space.setLayoutParams(layoutParams);
        views[5] = space;

        for(int i=0;i<views.length;i++){
            if(i < 3){
                ll_sorting_row1.addView(views[i]);
            }
            else{
                ll_sorting_row2.addView(views[i]);
            }
        }

    }

}

class SortingData {
    String activity;
    String text;
    int drawable;

    public SortingData(String activity, String text, int drawable) {
        this.activity = activity;
        this.text = text;
        this.drawable = drawable;
    }

}