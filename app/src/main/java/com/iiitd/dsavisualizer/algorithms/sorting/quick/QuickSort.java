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
import com.iiitd.dsavisualizer.runapp.others.AnimationDirection;
import com.iiitd.dsavisualizer.runapp.others.AnimationState;
import com.iiitd.dsavisualizer.runapp.others.ElementAnimationData;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.Random;

public class QuickSort {

    Context context;
    int arraySize;
    int[] data;
    QuickSortData[] quickSortData;
    View[] views;
    int[] positions;
    LinearLayout linearLayout;
    QuickSortSequence sequence;
    Random random;
    int width;
    int height;
    int textSize;
    boolean isRandomize;
    int[] rawInput;

    public QuickSort(Context context, LinearLayout linearLayout, int arraySize) {
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandomize = true;
        this.linearLayout = linearLayout;
        rawInput = null;

        init();
    }

    public QuickSort(Context context, LinearLayout linearLayout, int[] rawInput) {
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
        this.quickSortData = new QuickSortData[arraySize];
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

            QuickSortData quickSortData1 = new QuickSortData();
            quickSortData1.data = data[i];
            quickSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            quickSortData[i] = quickSortData1;
        }

        this.sequence = new QuickSortSequence(0);
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
        AnimationState animationState = new AnimationState("start", "start");
        sequence.addAnimSeq(animationState);
        sort(quickSortData, 0, quickSortData.length-1);
    }

    private int partition(QuickSortData arr[], int low, int high){
        int i = low+1;
        int pivot = arr[low].data;
        AnimationState animationState = new AnimationState("pivot", "pivot = " + pivot);
        sequence.addAnimSeq(animationState);
        System.out.println("pivot = " + pivot + " | " + low);

        for (int j=low+1; j<=high; j++){
            AnimationState animationState1 = new AnimationState("compare", "compare = " + pivot + " | " + arr[j].data);

            System.out.println("compare " + arr[j].data + "|" + pivot);
            if (arr[j].data < pivot){
                int val = j-i;
                animationState1.addElementAnimationData(new ElementAnimationData(arr[i].index, new Pair<>(AnimationDirection.RIGHT, val)));
                animationState1.addElementAnimationData(new ElementAnimationData(arr[j].index, new Pair<>(AnimationDirection.LEFT, val)));
                Util.swap(arr[i], arr[j]);
                i++;
            }
            sequence.addAnimSeq(animationState1);
        }

        int val2 = i-1-low;
        AnimationState animationState2 = new AnimationState("end swap", "swap = " + arr[low].data + " | " + arr[i-1].data);
        animationState2.addElementAnimationData(new ElementAnimationData(arr[low].index, new Pair<>(AnimationDirection.RIGHT, val2)));
        animationState2.addElementAnimationData(new ElementAnimationData(arr[i-1].index, new Pair<>(AnimationDirection.LEFT, val2)));
        sequence.addAnimSeq(animationState2);
        System.out.println("swap end|" + arr[low].data + "|" + arr[i-1].data);
        Util.swap(arr[low], arr[i-1]);
        return i-1;
    }

    private void sort(QuickSortData arr[], int low, int high){
        if (low < high) {
            int pi = partition(arr, low, high);
            System.out.println("sorting left");
            sort(arr, low, pi-1);
            System.out.println("sorting right");
            sort(arr, pi+1, high);
        }
        else{
            System.out.println("else");
        }
    }

}
