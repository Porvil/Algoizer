package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
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


        System.out.println(totalWidth +"x"+totalHeight);
        System.out.println(width +"x"+height);

        this.data = new int[arraySize];
        this.mergeSortData = new MergeSortData[arraySize];
        this.views = new View[arraySize];
        if(isRandomize){
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(20) + 1;
            }
        }
        else{
            //Get Data, Unimplemented
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<data.length;i++){
            View myView = vi.inflate(R.layout.element_merge_sort, null);
            myView.setLayoutParams(layoutParams);
            myView.setBackgroundColor(Color.GREEN);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            TextView tv2 = myView.findViewById(R.id.tv_elementindex);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.RED);
            tv.setTextSize(16);
            tv2.setText(i+"");
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
        System.out.println("=========DONE==========");
    }

    void merge(final MergeSortData[] arr, final int l, final int m, final int r) {
//        AnimationState animationState = new AnimationState("MERGING LEFT AND RIGHT SIDE");
        AnimationState animationState = new AnimationState(MergeSortInfo.MERGE_STARTED);
        for(int i=l;i<=r;i++){
            animationState.add(new ElementAnimationData(arr[i].index, new Pair<>("B", 1)));
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
                System.out.println("left <= right");

                int start = finalK;
                int end = finalI;
                final int diff = start - end;

                AnimationState animationState1 = new AnimationState(MergeSortInfo.L_LESSEQUAL_R);
//                AnimationState animationState1 = new AnimationState("Left <= Right");
                ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>("U", 1));
                u.add(new Pair<>(diff < 0 ? "L" : "R", Math.abs(diff)));
                animationState1.add(u);
                sequence.addAnimSeq(animationState1);
                arr[k] = L[i];
                i++;
            }
            else {
                System.out.println("right < left");
                int start = finalK;
                int end = finalJ + (n1);
                final int diff = start - end;
                AnimationState animationState2 = new AnimationState(MergeSortInfo.L_GREATER_R);
//                AnimationState animationState2 = new AnimationState("Left > Right");
                ElementAnimationData u1 = new ElementAnimationData(R[finalJ].index, new Pair<>("U", 1));
                u1.add(new Pair<>(diff < 0 ? "L" : "R", Math.abs(diff)));
                animationState2.add(u1);
                sequence.addAnimSeq(animationState2);

                arr[k] = R[j];
                j++;
            }

            k++;
        }

        // -------------------------------------------------------------------------------------

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            final int finalJ = j;
            final int finalI = i;
            final int finalK = k-l;
            System.out.println("left extras");
            int start = finalK;
            int end = finalI;
            final int diff = start - end;
            int anim = diff < 0 ? -1 : diff>0 ? 1 : 0;
            AnimationState animationState1 = new AnimationState(MergeSortInfo.L_EXTRAS);
//            AnimationState animationState1 = new AnimationState("Left Extras");
            ElementAnimationData u = new ElementAnimationData(L[finalI].index, new Pair<>("U", 1));
            u.add(new Pair<>(diff < 0 ? "L" : "R", Math.abs(diff)));
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
            System.out.println("right extras");
            int start = finalK;
            int end = finalJ + (n1);
            final int diff = start - end;
            int anim = diff < 0 ? -1 : diff>0 ? 1 : 0;
            AnimationState animationState1 = new AnimationState(MergeSortInfo.R_EXTRAS);
//            AnimationState animationState1 = new AnimationState("Right Extras");
            ElementAnimationData u = new ElementAnimationData(R[finalJ].index, new Pair<>("U", 1));
            u.add(new Pair<>(diff < 0 ? "L" : "R", Math.abs(diff)));
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
            System.out.println("SORTING LEFT SIDE");
            final AnimationState animationState1 = new AnimationState(MergeSortInfo.LS);
//            final AnimationState animationState1 = new AnimationState("Left Sort");

            //LEFT SORT
            for(int i=l;i<=m;i++){
                animationState1.add(new ElementAnimationData(data[i].index, new Pair<>("B", 1)));
            }

            sequence.addAnimSeq(animationState1);

            sort(data, l, m);

            final AnimationState animationState4 = new AnimationState(MergeSortInfo.LS_U);
//            final AnimationState animationState4 = new AnimationState("Left Sort U");

            //RIGHT SORT
            for(int i=l;i<=m;i++){
                animationState4.add(new ElementAnimationData(data[i].index, new Pair<>("U", 1)));
            }

            sequence.addAnimSeq(animationState4);

            // ----------------------------------------------------------------------------------------------------------

            final AnimationState animationState2 = new AnimationState(MergeSortInfo.RS);
//            final AnimationState animationState2 = new AnimationState("Right Sort");

            System.out.println("SORTING RIGHT SIDE");
            for(int i=m+1;i<=r;i++){
                animationState2.add(new ElementAnimationData(data[i].index, new Pair<>("B", 1)));
            }

            sequence.addAnimSeq(animationState2);

            sort(data, m + 1, r);


            final AnimationState animationState3 = new AnimationState(MergeSortInfo.RS_U);
//            final AnimationState animationState3 = new AnimationState("Right Sort U");
            for(int i=m+1;i<=r;i++){
                animationState3.add(new ElementAnimationData(data[i].index, new Pair<>("U", 1)));
            }

            sequence.addAnimSeq(animationState3);
            // MERGE
            // Merge the sorted halves
            merge(data, l, m, r);
        }
        else{
            System.out.println("Single merge");
            AnimationState animationState = new AnimationState(MergeSortInfo.SINGLE_MERGE);
//            AnimationState animationState = new AnimationState("Single Merge");
            animationState.add(new ElementAnimationData(l, new Pair<>("-", 1)));
            sequence.addAnimSeq(animationState);
        }
    }

}
