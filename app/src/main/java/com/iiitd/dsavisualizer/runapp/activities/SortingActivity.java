package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.insertion.InsertionSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.merge.MergeSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSort;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortActivity;

public class SortingActivity extends AppCompatActivity {

    Button btn_bubblesort;
    Button btn_selectionsort;
    Button btn_insertionsort;
    Button btn_mergesort;
    Button btn_quicksort;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        context = this;

        btn_bubblesort = findViewById(R.id.btn_bubblesort);
        btn_selectionsort = findViewById(R.id.btn_selectionsort);
        btn_insertionsort = findViewById(R.id.btn_insertionsort);
        btn_mergesort = findViewById(R.id.btn_mergesort);
        btn_quicksort = findViewById(R.id.btn_quicksort);

        btn_bubblesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BubbleSortActivity.class));
            }
        });

        btn_selectionsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SelectionSortActivity.class));
            }
        });

        btn_insertionsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, InsertionSortActivity.class));
            }
        });

        btn_mergesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MergeSortActivity.class));
            }
        });

        btn_quicksort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, QuickSortActivity.class));
            }
        });

    }
}