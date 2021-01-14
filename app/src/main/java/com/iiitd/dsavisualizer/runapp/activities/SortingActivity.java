package com.iiitd.dsavisualizer.runapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.insertion.InsertionSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.merge.MergeSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortActivity;

public class SortingActivity extends AppCompatActivity {

    LinearLayout ll_sorting_row1;
    LinearLayout ll_sorting_row2;

    SortingData[] sortingData = new SortingData[]{
            new SortingData(BubbleSortActivity.class.getName(), "BubbleSort", R.drawable.bubblesorticon),
            new SortingData(SelectionSortActivity.class.getName(), "SelectionSort", R.drawable.selectionsorticon),
            new SortingData(InsertionSortActivity.class.getName(), "InsertionSort", R.drawable.insertionsorticon),
            new SortingData(MergeSortActivity.class.getName(), "MergeSort", R.drawable.mergesorticon),
            new SortingData(QuickSortActivity.class.getName(), "QuickSort", R.drawable.quicksorticon),
            new SortingData("", "", R.drawable.empty)
    };

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        context = this;

        ll_sorting_row1 = findViewById(R.id.ll_sorting_row1);
        ll_sorting_row2 = findViewById(R.id.ll_sorting_row2);

        for(int i=0;i<sortingData.length;i++){
            final SortingData sortingData = this.sortingData[i];
            View view = getLayoutInflater().inflate(R.layout.layout_sortingitem, null);
            ImageView imageView = view.findViewById(R.id.iv_sorticon);
            TextView textView = view.findViewById(R.id.tv_sortname);

            imageView.setImageDrawable(getResources().getDrawable(sortingData.drawable));
            textView.setText(sortingData.text);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(5,5,5,5);
            view.setLayoutParams(layoutParams);

            if(i < 5){
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


            if(i < 3){
                ll_sorting_row1.addView(view);
            }
            else{
                ll_sorting_row2.addView(view);
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