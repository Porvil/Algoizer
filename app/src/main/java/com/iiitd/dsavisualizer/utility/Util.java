package com.iiitd.dsavisualizer.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.iiitd.dsavisualizer.R;

import java.util.ArrayList;

public class Util {

    public static void setText(TextView textView, String label, String data){
        textView.setText(label + " : " + data);
    }

    public static void setText(TextView textView, String data){
        textView.setText(data);
    }

    public static void changeTextViewsColors(Context context, ScrollView scrollView, TextView[] textViews, Integer[] indexes){
        for(TextView textView : textViews){
            textView.setTextColor(Color.BLACK);
        }

        if(indexes != null) {
            scrollView.scrollTo(0, (int) textViews[indexes[0]].getY());

            for (int i : indexes) {
                textViews[i].setTextColor(ContextCompat.getColor(context, R.color.mainColorDarkerShade));
            }
        }
    }

    public static void highlightViews(Context context, View[] views, ArrayList<Integer> indexes){
        for(View view : views){
            view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle));
        }

        if(indexes != null) {
            for (int i : indexes) {
                views[i].findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_highlighted));
            }
        }
    }

    public static Drawable getDrawable(Context context, int id){
        return ContextCompat.getDrawable(context, id);
    }

}
