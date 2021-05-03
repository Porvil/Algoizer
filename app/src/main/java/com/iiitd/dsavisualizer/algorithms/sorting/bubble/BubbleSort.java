package com.iiitd.dsavisualizer.algorithms.sorting.bubble;

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

// BubbleSort Backend
public class BubbleSort {

    final Context context;
    final int arraySize;
    int[] data;
    BubbleSortData[] bubbleSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;
    BubbleSortSortingSequence sequence;
    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandomize;
    final int[] rawInput;
    int comparisons;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;

    public BubbleSort(Context context, LinearLayout linearLayout, int arraySize) {
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

    public BubbleSort(Context context, LinearLayout linearLayout, int[] rawInput) {
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
        this.bubbleSortData = new BubbleSortData[arraySize];
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

            View myView = vi.inflate(R.layout.element_bubble_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(textSize);
            tv.getLayoutParams().height = (int) (height * h);
            tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
            linearLayout.addView(myView);

            BubbleSortData mergeSortData1 = new BubbleSortData();
            mergeSortData1.data = data[i];
            mergeSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            bubbleSortData[i] = mergeSortData1;
        }

        this.sequence = new BubbleSortSortingSequence();
        this.sequence.setViews(views);
        this.sequence.setPositions(positions);
        this.sequence.setAnimateViews(height, width, context);

        this.bubblesort();
    }

    public void forward(){
        sequence.forward();
    }

    public void backward(){
        sequence.backward();
    }

    private void bubblesort() {
        SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.BS, BubbleSortInfo.getBubbleSortString());
        for(int i=0;i<bubbleSortData.length;i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[i].index, new Pair<>(AnimationDirection.NULL, 1)));
            sortingAnimationState.addHighlightIndexes(bubbleSortData[i].index);
        }
        sequence.addAnimSeq(sortingAnimationState);
        bubble(bubbleSortData);
    }

    private void bubble(BubbleSortData[] arr){
        int length = arr.length;
        boolean flag = false;

        for (int i = 0; i < length; i++) {
            flag = false;

            for (int j = 0; j < length - i - 1; j++) {
                comparisons++;
                if (arr[j].data > arr[j + 1].data) {
                    SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.L_GREATER_R, BubbleSortInfo.getComparedString(arr[j].data, arr[j+1].data, j, j+1));
                    sortingAnimationState.addHighlightIndexes(bubbleSortData[j].index, bubbleSortData[j+1].index);
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[j].index, new Pair<>(AnimationDirection.RIGHT, 1)));
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[j+1].index, new Pair<>(AnimationDirection.LEFT, 1)));
                    sequence.addAnimSeq(sortingAnimationState);

                    Util.swap(arr[j], arr[j + 1]);
                    flag = true;
                }
                else{
                    SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.L_LESSEQUAL_R, BubbleSortInfo.getComparedString(arr[j].data, arr[j+1].data, j, j+1));
                    sortingAnimationState.addHighlightIndexes(bubbleSortData[j].index, bubbleSortData[j+1].index);
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[j].index, new Pair<>(AnimationDirection.NULL, 1)));
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[j+1].index, new Pair<>(AnimationDirection.NULL, 1)));
                    sequence.addAnimSeq(sortingAnimationState);
                }

            }

            if(!flag){
                SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.FLAG, BubbleSortInfo.getFlagString());
                sequence.addAnimSeq(sortingAnimationState);
                for(int k=0;k<length-i-1;k++){
                    sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[k].index));
                }
                return;
            }

            sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[length-i-1].index));
        }
    }

}