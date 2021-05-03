package com.iiitd.dsavisualizer.algorithms.sorting.quick;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.algorithms.sorting.AnimationDirection;
import com.iiitd.dsavisualizer.algorithms.sorting.SortingAnimationState;
import com.iiitd.dsavisualizer.algorithms.sorting.ElementAnimationData;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.Random;

// QuickSort Backend
public class QuickSort {

    final Context context;
    final int arraySize;
    int[] data;
    QuickSortData[] quickSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;
    QuickSortSortingSequence sequence;
    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandomize;
    final int[] rawInput;
    int comparisons;
    final PivotType pivotType;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;

    public QuickSort(Context context, LinearLayout linearLayout, int arraySize, PivotType pivotType) {
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandomize = true;
        this.linearLayout = linearLayout;
        this.pivotType = pivotType;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        rawInput = null;

        init();
    }

    public QuickSort(Context context, LinearLayout linearLayout, int[] rawInput, PivotType pivotType) {
        this.context = context;
        this.random = new Random();
        this.arraySize = rawInput.length;
        this.isRandomize = false;
        this.linearLayout = linearLayout;
        this.pivotType = pivotType;
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
        this.quickSortData = new QuickSortData[arraySize];
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

            View myView = vi.inflate(R.layout.element_quick_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(textSize);
            tv.getLayoutParams().height = (int) (height * h * .75f);
            tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
            linearLayout.addView(myView);

            QuickSortData quickSortData1 = new QuickSortData();
            quickSortData1.data = data[i];
            quickSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            quickSortData[i] = quickSortData1;
        }

        this.sequence = new QuickSortSortingSequence();
        this.sequence.setViews(views);
        this.sequence.setPositions(positions);
        this.sequence.setAnimateViews(height, width, context);

        this.quicksort();
    }

    public void forward(){
        sequence.forward();
    }

    public void backward(){
        sequence.backward();
    }

    private void quicksort(){
        SortingAnimationState sortingAnimationState = new SortingAnimationState(QuickSortInfo.QS,
                QuickSortInfo.getQuickSortString(0, quickSortData.length-1));
        sequence.addAnimSeq(sortingAnimationState);
        sort(quickSortData, 0, quickSortData.length-1);
    }

    private int partition(QuickSortData[] arr, int low, int high){
        SortingAnimationState sortingAnimationState = new SortingAnimationState(QuickSortInfo.PA, QuickSortInfo.getPartitionString(low, high));
        for(int z=low;z<=high;z++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(arr[z].index, new Pair<>(AnimationDirection.DOWN, 1)));
        }
        sequence.addAnimSeq(sortingAnimationState);

        if(this.pivotType == PivotType.END){
            if(low != high){
                SortingAnimationState sortingAnimationState1 = new SortingAnimationState(QuickSortInfo.PI, QuickSortInfo.getPivotSwap());
                int val = Math.abs(high - low);
                ElementAnimationData elementAnimationData1 = new ElementAnimationData(arr[low].index, new Pair<>(AnimationDirection.RIGHT, val));
                ElementAnimationData elementAnimationData2 = new ElementAnimationData(arr[high].index, new Pair<>(AnimationDirection.LEFT, val));
                sortingAnimationState1.addElementAnimationData(elementAnimationData1, elementAnimationData2);
                sortingAnimationState1.addPointers(new Pair<>(arr[high].index, "P"));
                sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[high].index));
                sequence.addAnimSeq(sortingAnimationState1);
                Util.swap(arr[low], arr[high]);
            }
        }else if(this.pivotType == PivotType.MIDDLE){
            int mid = (low + high)/2;
            if(mid != low){
                SortingAnimationState sortingAnimationState2 = new SortingAnimationState(QuickSortInfo.PI, QuickSortInfo.getPivotSwap());
                int val = Math.abs(mid - low);
                ElementAnimationData elementAnimationData1 = new ElementAnimationData(arr[low].index, new Pair<>(AnimationDirection.RIGHT, val));
                ElementAnimationData elementAnimationData2 = new ElementAnimationData(arr[mid].index, new Pair<>(AnimationDirection.LEFT, val));
                sortingAnimationState2.addElementAnimationData(elementAnimationData1, elementAnimationData2);
                sortingAnimationState2.addPointers(new Pair<>(arr[mid].index, "P"));
                sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[mid].index));
                sequence.addAnimSeq(sortingAnimationState2);
                Util.swap(arr[low], arr[mid]);
            }
        }

        int i = low+1;
        int j = low+1;
        QuickSortData pivotElement = arr[low];

        SortingAnimationState sortingAnimationState3 = new SortingAnimationState(QuickSortInfo.PI, QuickSortInfo.getPivot(pivotElement.data));
        sortingAnimationState3.addPointers(new Pair<>(pivotElement.index, "P"));
        sequence.addAnimSeq(sortingAnimationState3);

        SortingAnimationState sortingAnimationState4 = new SortingAnimationState(QuickSortInfo.PA_START, QuickSortInfo.getPartitionStart());
        sortingAnimationState4.addPointers(new Pair<>(pivotElement.index, "P"),
                new Pair<>(arr[i].index, "I"), new Pair<>(arr[j].index, "J"));
        sequence.addAnimSeq(sortingAnimationState4);

        for (; j<=high; j++){
            comparisons++;
            Pair<Integer, String> pairP = new Pair<>(pivotElement.index, "P");
            Pair<Integer, String> pairI = new Pair<>(arr[i].index, "I");
            Pair<Integer, String> pairJ = new Pair<>(arr[j].index, "J");
            SortingAnimationState sortingAnimationState5;
            if (arr[j].data < pivotElement.data){
                int val = j-i;
                sortingAnimationState5 = new SortingAnimationState(QuickSortInfo.E_LESSER_P,
                        QuickSortInfo.getComparedString(arr[j].data, pivotElement.data, j, i));
                sortingAnimationState5.addElementAnimationData(new ElementAnimationData(arr[i].index, new Pair<>(AnimationDirection.RIGHT, val)));
                sortingAnimationState5.addElementAnimationData(new ElementAnimationData(arr[j].index, new Pair<>(AnimationDirection.LEFT, val)));
                sortingAnimationState5.addHighlightIndexes(arr[i].index, arr[j].index);
                Util.swap(arr[i], arr[j]);
                i++;
            }
            else{
                sortingAnimationState5 = new SortingAnimationState(QuickSortInfo.E_GREATEREQUAL_P,
                        QuickSortInfo.getComparedString(arr[j].data, pivotElement.data, j, i));
                sortingAnimationState5.addHighlightIndexes(arr[i].index, arr[j].index);
            }
            sortingAnimationState5.addPointers(pairP, pairI, pairJ);
            sequence.addAnimSeq(sortingAnimationState5);
        }

        int val2 = i-1-low;
        SortingAnimationState sortingAnimationState6 = new SortingAnimationState(QuickSortInfo.SWAP_END, QuickSortInfo.getEndSwap(low, i-1));
        sortingAnimationState6.addElementAnimationData(new ElementAnimationData(arr[low].index, new Pair<>(AnimationDirection.RIGHT, val2)));
        sortingAnimationState6.addElementAnimationData(new ElementAnimationData(arr[i-1].index, new Pair<>(AnimationDirection.LEFT, val2)));
        sortingAnimationState6.addPointers(new Pair<>(pivotElement.index, "P"), new Pair<>(arr[i-1].index, "I-1"));
        sortingAnimationState6.addHighlightIndexes(arr[i-1].index, pivotElement.index);
        sequence.addAnimSeq(sortingAnimationState6);
        Util.swap(arr[low], arr[i-1]);

        sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[i-1].index));
        SortingAnimationState sortingAnimationState7 = new SortingAnimationState(QuickSortInfo.PA_U, QuickSortInfo.PA_U);
        for(int z=low;z<=high;z++){
            sortingAnimationState7.addElementAnimationData(new ElementAnimationData(quickSortData[z].index, new Pair<>(AnimationDirection.UP, 1)));
        }
        sequence.addAnimSeq(sortingAnimationState7);


        return i-1;
    }

    private void sort(QuickSortData[] arr, int low, int high){
        if (low < high) {
            int pi = partition(arr, low, high);

            if(low <= pi-1) {
                SortingAnimationState sortingAnimationState = new SortingAnimationState(QuickSortInfo.LS, QuickSortInfo.getQuickSortString(low, pi - 1));
                sequence.addAnimSeq(sortingAnimationState);
            }
            sort(arr, low, pi-1);

            if(pi+1 <= high) {
                SortingAnimationState sortingAnimationState1 = new SortingAnimationState(QuickSortInfo.RS, QuickSortInfo.getQuickSortString(pi + 1, high));
                sequence.addAnimSeq(sortingAnimationState1);
            }
            sort(arr, pi + 1, high);

        }
        else if (low >=0 && low < arr.length && high >=0 && high <arr.length){
            SortingAnimationState sortingAnimationState2 = new SortingAnimationState(QuickSortInfo.SINGLE_PARTITION, QuickSortInfo.SINGLE_PARTITION);
            sequence.addAnimSeq(sortingAnimationState2);
            sortedIndexes.add(new Pair<>(sequence.sortingAnimationStates.size(), arr[low].index));
        }
    }

}