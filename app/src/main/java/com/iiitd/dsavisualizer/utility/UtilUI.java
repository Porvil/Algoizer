package com.iiitd.dsavisualizer.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayoutElement;

import java.util.ArrayList;

public class UtilUI {

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
            view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rectangle));
        }

        if(indexes != null) {
            for (int i : indexes) {
                views[i].findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rectangle_highlighted));
            }
        }
    }

    public static Drawable getDrawable(Context context, int id){
        return ContextCompat.getDrawable(context, id);
    }

    public static void changePointers(ArrayList<Pair<Integer, String>> pointers, View[] views){
        for(View view : views){
            TextView viewById = view.findViewById(R.id.tv_pointer);
            TextView viewById2 = view.findViewById(R.id.tv_pointer2);
            viewById.setText("");
            viewById2.setText("");
        }

        for(Pair<Integer, String> pair : pointers){
            TextView viewById = views[pair.first].findViewById(R.id.tv_pointer);
            TextView viewById2 = views[pair.first].findViewById(R.id.tv_pointer2);
            if(pair.second.equals("J")){
                viewById2.setText(pair.second);
            }
            else {
                viewById.setText(pair.second);
            }
        }
    }

    public static void highlightSortedElements(Context context, ArrayList<Pair<Integer, Integer>> sortedIndexes, View[] views, int curSeqNo){

        if(curSeqNo == -1){
            for(View view : views){
                view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rectangle_highlighted));
            }
            return;
        }

        for(View view : views){
            view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rectangle));
        }

        for(Pair<Integer, Integer> pair : sortedIndexes){
            if(curSeqNo >= pair.first){
                views[pair.second].findViewById(R.id.tv_elementvalue)
                        .setBackground(getDrawable(context, R.drawable.rounded_rectangle_highlighted));
            }
        }

    }

    public static View getBSTView(LayoutInflater layoutInflater, int layout, int visibility, int weight){
        View myView = layoutInflater.inflate(layout, null);
        myView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, weight));
        myView.setPadding(5,5,5,5);

        if(layout == R.layout.element_bst_arrow)
            visibility = View.INVISIBLE;

        myView.setVisibility(visibility);

        return myView;
    }

    public static View getBSTView(LayoutInflater layoutInflater, TreeLayoutElement treeLayoutElement){
        int layout = 0;
        int weight = treeLayoutElement.weight;
        int visibility = View.VISIBLE;
        switch (treeLayoutElement.type){
            case EMPTY:
                layout = R.layout.element_bst_empty;
                break;
            case ARROW:
                layout = R.layout.element_bst_arrow;
                break;
            case ELEMENT:
                layout = R.layout.element_bst_element;
                break;
        }
        View myView = layoutInflater.inflate(layout, null);
        myView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, weight));
        myView.setPadding(5,5,5,5);

//        if(layout == R.layout.element_bst_arrow)
//            visibility = View.INVISIBLE;

        myView.setVisibility(visibility);

        return myView;
    }

}
