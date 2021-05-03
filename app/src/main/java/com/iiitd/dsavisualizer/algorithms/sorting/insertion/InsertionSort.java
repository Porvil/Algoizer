package com.iiitd.dsavisualizer.algorithms.sorting.insertion;

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
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.Random;

// InsertionSort Backend
public class InsertionSort {

    final Context context;
    final int arraySize;
    int[] data;
    InsertionSortData[] insertionSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;
    InsertionSortSortingSequence sequence;
    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandomize;
    final int[] rawInput;
    int comparisons;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;

    public InsertionSort(Context context, LinearLayout linearLayout, int arraySize) {
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

    public InsertionSort(Context context, LinearLayout linearLayout, int[] rawInput) {
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
        this.height =  (float) totalHeight / 2;

        int MAX = 0;

        this.data = new int[arraySize];
        this.insertionSortData = new InsertionSortData[arraySize];
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

            View myView = vi.inflate(R.layout.element_insertion_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(textSize);
            tv.getLayoutParams().height = (int) (height * h);
            tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
            linearLayout.addView(myView);

            InsertionSortData mergeSortData1 = new InsertionSortData();
            mergeSortData1.data = data[i];
            mergeSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            insertionSortData[i] = mergeSortData1;
        }

        this.sequence = new InsertionSortSortingSequence();
        this.sequence.setViews(views);
        this.sequence.setPositions(positions);
        this.sequence.setAnimateViews(height, width, context);

        this.insertionsort();
    }

    public void forward(){
        sequence.forward();
    }

    public void backward(){
        sequence.backward();
    }

    private void insertionsort() {
        SortingAnimationState sortingAnimationState = new SortingAnimationState(InsertionSortInfo.IS, InsertionSortInfo.getInsertionSortString());
        for(int i = 0; i< insertionSortData.length; i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(insertionSortData[i].index, new Pair<>(AnimationDirection.NULL, 1)));
            sortingAnimationState.addHighlightIndexes(insertionSortData[i].index);
        }
        sequence.addAnimSeq(sortingAnimationState);
        insertion(insertionSortData);
    }

    private void insertion(InsertionSortData[] arr){
        int length = arr.length;
        sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[0].index));
        for (int i = 1; i < length; i++) {
            InsertionSortData insertionSortData = arr[i];
            int j = i - 1;

            SortingAnimationState sortingAnimationState = new SortingAnimationState(InsertionSortInfo.VAL, InsertionSortInfo.getValString(insertionSortData.data, j));
            sortingAnimationState.addHighlightIndexes(insertionSortData.index);
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(insertionSortData.index,
                    new Pair<>(AnimationDirection.DOWN, 1)));
            sequence.addAnimSeq(sortingAnimationState);

            while (j >= 0) {
                comparisons++;
                if(arr[j].data > insertionSortData.data) {

                    SortingAnimationState sortingAnimationState1 = new SortingAnimationState(InsertionSortInfo.L_GREATER_R,
                            InsertionSortInfo.getComparedString(arr[j].data, insertionSortData.data, j, j+1));
                    sortingAnimationState1.addHighlightIndexes(insertionSortData.index);
                    sortingAnimationState1.addHighlightIndexes(arr[j].index);
                    sortingAnimationState1.addElementAnimationData(new ElementAnimationData(arr[j].index,
                            new Pair<>(AnimationDirection.RIGHT, 1)));
                    sortingAnimationState1.addElementAnimationData(new ElementAnimationData(insertionSortData.index,
                            new Pair<>(AnimationDirection.LEFT, 1)));
                    sequence.addAnimSeq(sortingAnimationState1);

                    arr[j + 1] = arr[j];
                    j--;
                }
                else{
                    SortingAnimationState sortingAnimationState1 = new SortingAnimationState(InsertionSortInfo.L_LESSEQUAL_R,
                            InsertionSortInfo.getComparedString(arr[j].data, insertionSortData.data, j, insertionSortData.index));
                    sortingAnimationState1.addHighlightIndexes(insertionSortData.index);
                    sortingAnimationState1.addHighlightIndexes(arr[j].index);
                    sequence.addAnimSeq(sortingAnimationState1);

                    break;
                }
            }

            SortingAnimationState sortingAnimationState2 = new SortingAnimationState(InsertionSortInfo.VAL_U, InsertionSortInfo.getValUString());
            sortingAnimationState2.addElementAnimationData(new ElementAnimationData(insertionSortData.index,
                    new Pair<>(AnimationDirection.UP, 1)));
            sequence.addAnimSeq(sortingAnimationState2);
            sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), insertionSortData.index));

            arr[j + 1] = insertionSortData;

        }
    }

}