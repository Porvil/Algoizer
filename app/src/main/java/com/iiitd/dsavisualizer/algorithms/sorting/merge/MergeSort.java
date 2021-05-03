package com.iiitd.dsavisualizer.algorithms.sorting.merge;

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
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.Random;

// MergeSort Backend
public class MergeSort {

    final Context context;
    final int arraySize;
    int[] data;
    MergeSortData[] mergeSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;
    MergeSortSortingSequence sequence;
    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandomize;
    final int[] rawInput;
    int comparisons;

    public MergeSort(Context context, LinearLayout linearLayout, int arraySize) {
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandomize = true;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
        rawInput = null;

        init();
    }

    public MergeSort(Context context, LinearLayout linearLayout, int[] rawInput) {
        this.context = context;
        this.random = new Random();
        this.arraySize = rawInput.length;
        this.isRandomize = false;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
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
        int log = (int)(Math.log(arraySize) / Math.log(2));
        if((arraySize & (arraySize - 1)) == 0){
            log++;
        }
        else{
            log += 2;
        }
        int totalWidth = linearLayout.getWidth();
        int totalHeight = linearLayout.getHeight();
        this.width = (float) totalWidth / arraySize;
        this.height =  (float) totalHeight / log;

        int MAX = 0;

        this.data = new int[arraySize];
        this.mergeSortData = new MergeSortData[arraySize];
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

            View myView = vi.inflate(R.layout.element_merge_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(textSize);
            tv.getLayoutParams().height = (int) (height * h);
            tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
            linearLayout.addView(myView);

            MergeSortData mergeSortData1 = new MergeSortData();
            mergeSortData1.data = data[i];
            mergeSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            mergeSortData[i] = mergeSortData1;
        }

        this.sequence = new MergeSortSortingSequence();
        this.sequence.setViews(views);
        this.sequence.setPositions(positions);
        this.sequence.setAnimateViews(height, width, context);

        this.mergesort();
    }

    public void forward(){
        sequence.forward();
    }

    public void backward(){
        sequence.backward();
    }

    private void mergesort(){
        SortingAnimationState sortingAnimationState = new SortingAnimationState(MergeSortInfo.MS, MergeSortInfo.getMergeSortString(0, mergeSortData.length-1));
        for(int i=0;i<mergeSortData.length;i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(mergeSortData[i].index, new Pair<>(AnimationDirection.NULL, 1)));
            sortingAnimationState.addHighlightIndexes(mergeSortData[i].index);
        }
        sequence.addAnimSeq(sortingAnimationState);
        sort(mergeSortData, 0, mergeSortData.length-1);
    }

    private void merge(MergeSortData[] arr, int l, int m, int r) {
        SortingAnimationState sortingAnimationState = new SortingAnimationState(MergeSortInfo.MERGE_STARTED, MergeSortInfo.getMergeString(l, m, r));
        for(int i=l;i<=r;i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(arr[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
            sortingAnimationState.addHighlightIndexes(arr[i].index);
        }
        sequence.addAnimSeq(sortingAnimationState);

        final int n1 = m - l + 1;
        final int n2 = r - m;

        MergeSortData[] L = new MergeSortData[n1];
        MergeSortData[] R = new MergeSortData[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0;
        int j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            comparisons++;
            final int finalI = i;
            final int finalJ = j;
            final int finalK = k-l;

            if (L[i].data <= R[j].data) {
                int start = finalK;
                int end = finalI;
                final int diff = start - end;

                SortingAnimationState sortingAnimationState1 = new SortingAnimationState(MergeSortInfo.L_LESSEQUAL_R, MergeSortInfo.getComparedString(L[i].data, R[j].data));
                ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>(AnimationDirection.UP, 1));
                if(Math.abs(diff) != 0)
                    u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
                sortingAnimationState1.addElementAnimationData(u);
                sortingAnimationState1.addHighlightIndexes(L[finalI].index, R[finalJ].index);
                sequence.addAnimSeq(sortingAnimationState1);
                arr[k] = L[i];
                i++;
            }
            else {
                int start = finalK;
                int end = finalJ + (n1);
                final int diff = start - end;
                SortingAnimationState sortingAnimationState2 = new SortingAnimationState(MergeSortInfo.L_GREATER_R, MergeSortInfo.getComparedString(L[i].data, R[j].data));
                ElementAnimationData u1 = new ElementAnimationData(R[finalJ].index, new Pair<>(AnimationDirection.UP, 1));
                if(Math.abs(diff) != 0)
                    u1.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
                sortingAnimationState2.addElementAnimationData(u1);
                sortingAnimationState2.addHighlightIndexes(L[finalI].index, R[finalJ].index);
                sequence.addAnimSeq(sortingAnimationState2);
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            final int finalI = i;
            final int finalJ = j;
            final int finalK = k-l;
            int start = finalK;
            int end = finalI;
            final int diff = start - end;
            SortingAnimationState sortingAnimationState1 = new SortingAnimationState(MergeSortInfo.L_EXTRAS, MergeSortInfo.getRemainingElementString(L[i].data, true));
            ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>(AnimationDirection.UP, 1));
            if(Math.abs(diff) != 0)
                u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
            sortingAnimationState1.addElementAnimationData(u);
            sequence.addAnimSeq(sortingAnimationState1);
            sortingAnimationState1.addHighlightIndexes(L[finalI].index);
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            final int finalI = i;
            final int finalJ = j;
            final int finalK = k-l;
            int start = finalK;
            int end = finalJ + (n1);
            final int diff = start - end;
            SortingAnimationState sortingAnimationState1 = new SortingAnimationState(MergeSortInfo.R_EXTRAS, MergeSortInfo.getRemainingElementString(R[j].data, false));
            ElementAnimationData u = new ElementAnimationData(R[finalJ].index, new Pair<>(AnimationDirection.UP, 1));
            if(Math.abs(diff) != 0)
                u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
            sortingAnimationState1.addElementAnimationData(u);
            sortingAnimationState1.addHighlightIndexes(R[finalJ].index);
            sequence.addAnimSeq(sortingAnimationState1);
            arr[k] = R[j];
            j++;
            k++;
        }

    }

    private void sort(final MergeSortData[] data, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            SortingAnimationState sortingAnimationState1 = new SortingAnimationState(MergeSortInfo.LS,MergeSortInfo.getMergeSortString(l, m));
            for(int i=l;i<=m;i++){
                sortingAnimationState1.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
                sortingAnimationState1.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(sortingAnimationState1);

            sort(data, l, m);

            SortingAnimationState sortingAnimationState2 = new SortingAnimationState(MergeSortInfo.LS_U, MergeSortInfo.getMergeSortString(l, m) + " done");
            for(int i=l;i<=m;i++){
                sortingAnimationState2.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.UP, 1)));
                sortingAnimationState2.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(sortingAnimationState2);

            SortingAnimationState sortingAnimationState3 = new SortingAnimationState(MergeSortInfo.RS, MergeSortInfo.getMergeSortString(m+1, r));
            for(int i=m+1;i<=r;i++){
                sortingAnimationState3.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
                sortingAnimationState3.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(sortingAnimationState3);

            sort(data, m + 1, r);

            SortingAnimationState sortingAnimationState4 = new SortingAnimationState(MergeSortInfo.RS_U, MergeSortInfo.getMergeSortString(m+1, r) + " done");
            for(int i=m+1;i<=r;i++){
                sortingAnimationState4.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.UP, 1)));
                sortingAnimationState4.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(sortingAnimationState4);

            merge(data, l, m, r);
        }
        else{
            SortingAnimationState sortingAnimationState = new SortingAnimationState(MergeSortInfo.SINGLE_MERGE, MergeSortInfo.SINGLE_MERGE);
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(l, new Pair<>(AnimationDirection.NULL, 1)));
            sortingAnimationState.addHighlightIndexes(data[l].index);
            sequence.addAnimSeq(sortingAnimationState);
        }
    }

}