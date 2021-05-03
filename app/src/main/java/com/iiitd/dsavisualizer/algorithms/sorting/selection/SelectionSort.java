package com.iiitd.dsavisualizer.algorithms.sorting.selection;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.SortingAnimationState;
import com.iiitd.dsavisualizer.algorithms.sorting.ElementAnimationData;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.algorithms.sorting.AnimationDirection;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.Random;

// SelectionSort Backend
public class SelectionSort {

    final Context context;
    final int arraySize;
    int[] data;
    SelectionSortData[] selectionSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;
    SelectionSortSortingSequence sequence;
    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandomize;
    final int[] rawInput;
    int comparisons;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;

    public SelectionSort(Context context, LinearLayout linearLayout, int arraySize) {
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandomize = true;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        rawInput = null;

        init();
    }

    public SelectionSort(Context context, LinearLayout linearLayout, int[] rawInput) {
        this.context = context;
        this.random = new Random();
        this.arraySize = rawInput.length;
        this.isRandomize = false;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        this.rawInput = rawInput;

        init();
    }

    private void init() {
        if(arraySize > 8){
            textSize = AppSettings.TEXT_SMALL;
        }
        else{
            textSize = AppSettings.TEXT_MEDIUM;
        }

        int totalWidth = linearLayout.getWidth();
        int totalHeight = linearLayout.getHeight();
        this.width = (float) totalWidth / arraySize;
        this.height =  (float) totalHeight;

        int MAX = 0;

        this.data = new int[arraySize];
        this.selectionSortData = new SelectionSortData[arraySize];
        this.views = new View[arraySize];
        this.positions = new int[arraySize];
        if(isRandomize){
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(AppSettings.SORTING_ELEMENT_BOUND) + 1;
                MAX = Math.max(data[i], MAX);
            }
        }
        else{
            for (int i = 0; i < data.length; i++) {
                data[i] = rawInput[i];
                MAX = Math.max(data[i], MAX);
            }
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, (int) height, 1);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<data.length;i++){
            float x = (float)data[i] / (float)MAX;
            float h = (x * .75f) + .20f;

            View myView = vi.inflate(R.layout.element_selection_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(textSize);
            tv.getLayoutParams().height = (int) (height * h);
            tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
            linearLayout.addView(myView);

            SelectionSortData mergeSortData1 = new SelectionSortData();
            mergeSortData1.data = data[i];
            mergeSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            selectionSortData[i] = mergeSortData1;
        }

        this.sequence = new SelectionSortSortingSequence();
        this.sequence.setViews(views);
        this.sequence.setPositions(positions);
        this.sequence.setAnimateViews(height, width, context);

        this.selectionsort();
    }

    public void forward(){
        sequence.forward();
    }

    public void backward(){
        sequence.backward();
    }

    private void selectionsort() {
        SortingAnimationState sortingAnimationState = new SortingAnimationState(SelectionSortInfo.SS, SelectionSortInfo.getSelectionSortString());
        for(int i = 0; i< selectionSortData.length; i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(selectionSortData[i].index, new Pair<>(AnimationDirection.NULL, 1)));
            sortingAnimationState.addHighlightIndexes(selectionSortData[i].index);
        }
        sequence.addAnimSeq(sortingAnimationState);
        selection(selectionSortData);
    }

    void selection(SelectionSortData[] arr){
        int length = arr.length;

        for (int i = 0; i < length-1; i++) {
            int min_idx = i;

            SortingAnimationState sortingAnimationState = new SortingAnimationState(SelectionSortInfo.VAL, SelectionSortInfo.getValString(min_idx));
            sortingAnimationState.addHighlightIndexes(arr[min_idx].index);
            sequence.addAnimSeq(sortingAnimationState);

            for (int j = i+1; j < length; j++) {
                comparisons++;

                if (arr[j].data < arr[min_idx].data) {
                    SortingAnimationState sortingAnimationState1 = new SortingAnimationState(SelectionSortInfo.L_LESSER_R, SelectionSortInfo.getComparedString(arr[j].data, arr[min_idx].data, j));
                    sortingAnimationState1.addHighlightIndexes(arr[min_idx].index);
                    sortingAnimationState1.addHighlightIndexes(arr[j].index);
                    sequence.addAnimSeq(sortingAnimationState1);

                    min_idx = j;
                }
                else{
                    SortingAnimationState sortingAnimationState1 = new SortingAnimationState(SelectionSortInfo.L_GREATEREQUAL_R, SelectionSortInfo.getComparedString(arr[j].data, arr[min_idx].data, j));
                    sortingAnimationState1.addHighlightIndexes(arr[min_idx].index);
                    sortingAnimationState1.addHighlightIndexes(arr[j].index);
                    sequence.addAnimSeq(sortingAnimationState1);
                }

            }

            if(min_idx != i) {
                int diff = Math.abs(i - min_idx);
                System.out.println(i + " | " + min_idx + " | " + diff);
                SortingAnimationState sortingAnimationState2 = new SortingAnimationState(SelectionSortInfo.SWAP, SelectionSortInfo.getSwapString(i, min_idx));
                sortingAnimationState2.addHighlightIndexes(arr[min_idx].index, arr[i].index);
                sortingAnimationState2.addElementAnimationData(new ElementAnimationData(arr[min_idx].index,
                        new Pair<>(AnimationDirection.LEFT, diff)));
                sortingAnimationState2.addElementAnimationData(new ElementAnimationData(arr[i].index,
                        new Pair<>(AnimationDirection.RIGHT, diff)));

                sequence.addAnimSeq(sortingAnimationState2);
            }

            sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[min_idx].index));
            Util.swap(arr[min_idx], arr[i]);
        }
    }

}