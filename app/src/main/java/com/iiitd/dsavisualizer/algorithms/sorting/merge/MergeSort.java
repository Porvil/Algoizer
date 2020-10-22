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
import com.iiitd.dsavisualizer.runapp.others.AnimationDirection;
import com.iiitd.dsavisualizer.runapp.others.AnimationState;
import com.iiitd.dsavisualizer.runapp.others.ElementAnimationData;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.Random;

public class MergeSort {

    Context context;
    int arraySize;
    int[] data;
    MergeSortData[] mergeSortData;
    View[] views;
    int[] positions;
    LinearLayout linearLayout;
    MergeSortSequence sequence;
    Random random;
    int width;
    int height;
    int textSize;
    boolean isRandomize;
    int[] rawInput;

    public MergeSort(Context context, LinearLayout linearLayout, int arraySize) {
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandomize = true;
        this.linearLayout = linearLayout;
        rawInput = null;

        init();
    }

    public MergeSort(Context context, LinearLayout linearLayout, int[] rawInput) {
        this.context = context;
        this.random = new Random();
        this.arraySize = rawInput.length;
        this.isRandomize = false;
        this.linearLayout = linearLayout;
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
        this.width = totalWidth / arraySize;
        this.height =  totalHeight / log;

        int MAX = 0;

        this.data = new int[arraySize];
        this.mergeSortData = new MergeSortData[arraySize];
        this.views = new View[arraySize];
        this.positions = new int[arraySize];
        if(isRandomize){
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(20) + 1;
                MAX = Math.max(data[i], MAX);
            }
        }
        else{
            for (int i = 0; i < data.length; i++) {
                data[i] = rawInput[i];
                MAX = Math.max(data[i], MAX);
            }
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, height, 1);
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        this.sequence = new MergeSortSequence(0);
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
        final AnimationState animationState = new AnimationState(MergeSortInfo.MS, MergeSortInfo.getMergeSortString(0, mergeSortData.length-1));
        for(int i=0;i<mergeSortData.length;i++){
            animationState.addElementAnimationData(new ElementAnimationData(mergeSortData[i].index, new Pair<>(AnimationDirection.NULL, 1)));
            animationState.addHighlightIndexes(mergeSortData[i].index);
        }
        sequence.addAnimSeq(animationState);
        sort(mergeSortData, 0, mergeSortData.length-1);
    }

    private void merge(final MergeSortData[] arr, final int l, final int m, final int r) {
        AnimationState animationState = new AnimationState(MergeSortInfo.MERGE_STARTED, MergeSortInfo.getMergeString(l, m, r));
        for(int i=l;i<=r;i++){
            animationState.addElementAnimationData(new ElementAnimationData(arr[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
            animationState.addHighlightIndexes(arr[i].index);
        }
        sequence.addAnimSeq(animationState);

        final int n1 = m - l + 1;
        final int n2 = r - m;

        final MergeSortData[] L = new MergeSortData[n1];
        final MergeSortData[] R = new MergeSortData[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0;
        int j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            final int finalI = i;
            final int finalJ = j;
            final int finalK = k-l;

            if (L[i].data <= R[j].data) {
                int start = finalK;
                int end = finalI;
                final int diff = start - end;

                AnimationState animationState1 = new AnimationState(MergeSortInfo.L_LESSEQUAL_R, MergeSortInfo.getComparedString(L[i].data, R[j].data));
                ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>(AnimationDirection.UP, 1));
                if(Math.abs(diff) != 0)
                    u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
                animationState1.addElementAnimationData(u);
                animationState1.addHighlightIndexes(L[finalI].index, R[finalJ].index);
                sequence.addAnimSeq(animationState1);
                arr[k] = L[i];
                i++;
            }
            else {
                int start = finalK;
                int end = finalJ + (n1);
                final int diff = start - end;
                AnimationState animationState2 = new AnimationState(MergeSortInfo.L_GREATER_R, MergeSortInfo.getComparedString(L[i].data, R[j].data));
                ElementAnimationData u1 = new ElementAnimationData(R[finalJ].index, new Pair<>(AnimationDirection.UP, 1));
                if(Math.abs(diff) != 0)
                    u1.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
                animationState2.addElementAnimationData(u1);
                animationState2.addHighlightIndexes(L[finalI].index, R[finalJ].index);
                sequence.addAnimSeq(animationState2);
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
            AnimationState animationState1 = new AnimationState(MergeSortInfo.L_EXTRAS, MergeSortInfo.getRemainingElementString(L[i].data, true));
            ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>(AnimationDirection.UP, 1));
            if(Math.abs(diff) != 0)
                u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
            animationState1.addElementAnimationData(u);
            sequence.addAnimSeq(animationState1);
            animationState1.addHighlightIndexes(L[finalI].index);
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
            AnimationState animationState1 = new AnimationState(MergeSortInfo.R_EXTRAS, MergeSortInfo.getRemainingElementString(R[j].data, false));
            ElementAnimationData u = new ElementAnimationData(R[finalJ].index, new Pair<>(AnimationDirection.UP, 1));
            if(Math.abs(diff) != 0)
                u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
            animationState1.addElementAnimationData(u);
            animationState1.addHighlightIndexes(R[finalJ].index);
            sequence.addAnimSeq(animationState1);
            arr[k] = R[j];
            j++;
            k++;
        }

    }

    private void sort(final MergeSortData[] data, final int l, final int r) {
        if (l < r) {
            final int m = (l + r) / 2;

            final AnimationState animationState1 = new AnimationState(MergeSortInfo.LS,MergeSortInfo.getMergeSortString(l, m));
            for(int i=l;i<=m;i++){
                animationState1.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
                animationState1.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(animationState1);

            sort(data, l, m);

            final AnimationState animationState4 = new AnimationState(MergeSortInfo.LS_U, MergeSortInfo.getMergeSortString(l, m) + " done");
            for(int i=l;i<=m;i++){
                animationState4.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.UP, 1)));
                animationState4.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(animationState4);

            final AnimationState animationState2 = new AnimationState(MergeSortInfo.RS, MergeSortInfo.getMergeSortString(m+1, r));
            for(int i=m+1;i<=r;i++){
                animationState2.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
                animationState2.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(animationState2);

            sort(data, m + 1, r);

            final AnimationState animationState3 = new AnimationState(MergeSortInfo.RS_U, MergeSortInfo.getMergeSortString(m+1, r) + " done");
            for(int i=m+1;i<=r;i++){
                animationState3.addElementAnimationData(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.UP, 1)));
                animationState3.addHighlightIndexes(data[i].index);
            }
            sequence.addAnimSeq(animationState3);

            merge(data, l, m, r);
        }
        else{
            AnimationState animationState = new AnimationState(MergeSortInfo.SINGLE_MERGE, MergeSortInfo.SINGLE_MERGE);
            animationState.addElementAnimationData(new ElementAnimationData(l, new Pair<>(AnimationDirection.NULL, 1)));
            animationState.addHighlightIndexes(data[l].index);
            sequence.addAnimSeq(animationState);
        }
    }

}
