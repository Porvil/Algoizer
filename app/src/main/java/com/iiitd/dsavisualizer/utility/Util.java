package com.iiitd.dsavisualizer.utility;

import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

public class Util {

    public static void setText(TextView textView, String label, String data){
        textView.setText(label + " : " + data);
    }

    public static void changeTextViewsColors(TextView[] textViews, int normal, int highlight, int... indexes){
        for(TextView textView : textViews){
            textView.setBackgroundColor(normal);
        }

        for(int i:indexes){
            textViews[i].setBackgroundColor(highlight);
        }
    }

    public static void changeTextViewsColors(TextView[] textViews, int normal, int highlight, Integer[] indexes){
        for(TextView textView : textViews){
            textView.setBackgroundColor(normal);
        }

        for(int i:indexes){
            textViews[i].setBackgroundColor(highlight);
        }
    }

}
