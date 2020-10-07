package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.runapp.others.AnimationDirection;
import com.iiitd.dsavisualizer.runapp.others.AnimationState;
import com.iiitd.dsavisualizer.runapp.others.ElementAnimationData;

import java.util.Random;

public class MergeSort {

    Context context;
    int arraySize;
    int[] data;
    MergeSortData[] mergeSortData;
    View[] views;
    LinearLayout linearLayout;
    MergeSortSequence sequence;
    Random random;
    int width;
    int height;
    boolean isRandomize;

    public MergeSort(Context context, int arraySize, boolean isRandomize, LinearLayout linearLayout) {
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandomize = isRandomize;
        this.linearLayout = linearLayout;

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
        if(isRandomize){
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(20) + 1;
                MAX = Math.max(data[i], MAX);
            }
        }
        else{
            //Get Data, Unimplemented
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayout.setBackgroundColor(Color.RED);
        for(int i=0;i<data.length;i++){
            float h = (float)data[i] / (float)MAX;

            View myView = vi.inflate(R.layout.element_merge_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.RED);
            tv.setTextSize(16);
            tv.setBackgroundColor(Color.GREEN);
            tv.getLayoutParams().height = (int) (height * h);
            linearLayout.addView(myView);

            MergeSortData mergeSortData1 = new MergeSortData();
            mergeSortData1.data = data[i];
            mergeSortData1.index = i;
            views[i] = myView;
            mergeSortData[i] = mergeSortData1;
        }

        this.sequence = new MergeSortSequence(0);
        this.sequence.setViews(views);
        this.sequence.setAnimateViews(height, width, context);

        mergesort();
    }

    public void forward(){
        sequence.forward();
    }

    public void backward(){
        sequence.backward();
    }

    public void mergesort(){
        sort(mergeSortData, 0, mergeSortData.length-1);
    }

    void merge(final MergeSortData[] arr, final int l, final int m, final int r) {
        AnimationState animationState = new AnimationState(MergeSortInfo.MERGE_STARTED, "-");
        for(int i=l;i<=r;i++){
            animationState.add(new ElementAnimationData(arr[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
        }

        sequence.addAnimSeq(animationState);

        // Find sizes of two subarrays to be merged
        final int n1 = m - l + 1;
        final int n2 = r - m;

        /* Create temp arrays */
        final MergeSortData L[] = new MergeSortData[n1];
        final MergeSortData R[] = new MergeSortData[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            final int finalJ = j;
            final int finalI = i;
            final int finalK = k-l;

            if (L[i].data <= R[j].data) {
                int start = finalK;
                int end = finalI;
                final int diff = start - end;

                AnimationState animationState1 = new AnimationState(MergeSortInfo.L_LESSEQUAL_R,L[i].data + " <= " + R[j].data);
                ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>(AnimationDirection.UP, 1));
                u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
                animationState1.add(u);
                sequence.addAnimSeq(animationState1);
                arr[k] = L[i];
                i++;
            }
            else {
                int start = finalK;
                int end = finalJ + (n1);
                final int diff = start - end;
                AnimationState animationState2 = new AnimationState(MergeSortInfo.L_GREATER_R, L[i].data + " > " + R[j].data);
                ElementAnimationData u1 = new ElementAnimationData(R[finalJ].index, new Pair<>(AnimationDirection.UP, 1));
                u1.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
                animationState2.add(u1);
                sequence.addAnimSeq(animationState2);
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            final int finalJ = j;
            final int finalI = i;
            final int finalK = k-l;
            int start = finalK;
            int end = finalI;
            final int diff = start - end;
            AnimationState animationState1 = new AnimationState(MergeSortInfo.L_EXTRAS, L[i].data + " copied to final array");
            ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>(AnimationDirection.UP, 1));
            u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
            animationState1.add(u);
            sequence.addAnimSeq(animationState1);
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            final int finalJ = j;
            final int finalI = i;
            final int finalK = k-l;
            int start = finalK;
            int end = finalJ + (n1);
            final int diff = start - end;
            AnimationState animationState1 = new AnimationState(MergeSortInfo.R_EXTRAS, R[j].data + " copied to final array");
            ElementAnimationData u = new ElementAnimationData(R[finalJ].index, new Pair<>(AnimationDirection.UP, 1));
            u.add(new Pair<>(diff < 0 ? AnimationDirection.LEFT : AnimationDirection.RIGHT, Math.abs(diff)));
            animationState1.add(u);
            sequence.addAnimSeq(animationState1);
            arr[k] = R[j];
            j++;
            k++;
        }

    }

    // Main function that sorts arr[l..r] using
    // merge()
    void sort(final MergeSortData[] data, final int l, final int r) {
        if (l < r) {
            // Find the middle point
            final int m = (l + r) / 2;

            // Sort first and second halves
            final AnimationState animationState1 = new AnimationState(MergeSortInfo.LS,"mergeSort(data, " + l + ", "+ m + ")");
            //LEFT SORT
            for(int i=l;i<=m;i++){
                animationState1.add(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
            }

            sequence.addAnimSeq(animationState1);
            sort(data, l, m);

            final AnimationState animationState4 = new AnimationState(MergeSortInfo.LS_U, "LS Done");
            //RIGHT SORT
            for(int i=l;i<=m;i++){
                animationState4.add(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.UP, 1)));
            }

            sequence.addAnimSeq(animationState4);

            final AnimationState animationState2 = new AnimationState(MergeSortInfo.RS, "mergeSort(data, " + m + ", "+ r + ")");
            for(int i=m+1;i<=r;i++){
                animationState2.add(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.DOWN, 1)));
            }

            sequence.addAnimSeq(animationState2);
            sort(data, m + 1, r);

            final AnimationState animationState3 = new AnimationState(MergeSortInfo.RS_U, "RS Done");
            for(int i=m+1;i<=r;i++){
                animationState3.add(new ElementAnimationData(data[i].index, new Pair<>(AnimationDirection.UP, 1)));
            }

            sequence.addAnimSeq(animationState3);
            merge(data, l, m, r);
        }
        else{
            AnimationState animationState = new AnimationState(MergeSortInfo.SINGLE_MERGE, data[l].data + " is always sorted");
            animationState.add(new ElementAnimationData(l, new Pair<>(AnimationDirection.NULL, 1)));
            sequence.addAnimSeq(animationState);
        }
    }

}
