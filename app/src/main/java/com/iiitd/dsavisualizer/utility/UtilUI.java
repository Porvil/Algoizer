package com.iiitd.dsavisualizer.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.datastructures.trees.NodeType;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayoutElement;

import java.util.ArrayList;
import java.util.Random;

public class UtilUI {

    public static void setText(TextView textView, String label, String data){
        textView.setText(label + " : " + data);
    }

    public static void setText(TextView textView, String data){
        textView.setText(data);
    }

    public int getRandomColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
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
//        myView.setPadding(5,5,5,5);

        if(layout == R.layout.element_bst_arrow)
            visibility = View.INVISIBLE;

        myView.setVisibility(visibility);

        return myView;
    }

    public static View getBSTView(Context context, LayoutInflater layoutInflater, TreeLayoutElement treeLayoutElement, int height, int row, int col){
        int layout = 0;
        int weight = treeLayoutElement.weight;
//        int visibility = View.VISIBLE;
        int visibility = View.INVISIBLE;
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
        myView.setLayoutParams(new TableRow.LayoutParams(0, height, weight));
//        myView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, weight));
//        myView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight));
//        myView.setPadding(5,5,5,5);

        if(row > 0 && layout == R.layout.element_bst_arrow){
            int arrowLayout = R.drawable.arrow_4;
            if(row == 1){
                arrowLayout = R.drawable.arrow_4;
            }
            else if(row == 3){
                arrowLayout = R.drawable.arrow_2;
            }
            else if(row == 5){
                arrowLayout = R.drawable.arrow_1;
            }

            //if last arrow row, then mod is reversed, hence ++
            if(row == 5)
                col++;
            if(((col-1)/2) % 2 == 1)
                myView.setRotationY(180);

            FrameLayout frameLayout = myView.findViewById(R.id.fl_arrow);
            frameLayout.setBackground(getDrawable(context, arrowLayout));
        }
//        if(layout == R.layout.element_bst_arrow)
//            visibility = View.INVISIBLE;

        int in = 0;
        if(row == 0)
            in = 8;
        else if(row == 2){
            if(col == 1)
                in = 4;
            if(col == 3)
                in = 12;
        }
        else if(row == 4){
            if(col == 1)
                in = 2;
            if(col == 3)
                in = 6;
            if(col == 5)
                in = 10;
            if(col == 7)
                in = 14;
        }
        else if(row == 6){
            if(col == 0)
                in = 1;
            if(col == 2)
                in = 3;
            if(col == 4)
                in = 5;
            if(col == 6)
                in = 7;
            if(col == 8)
                in = 9;
            if(col == 10)
                in = 11;
            if(col == 12)
                in = 13;
            if(col == 14)
                in = 15;
        }

        if(treeLayoutElement.type == NodeType.ELEMENT) {
            TextView viewById = myView.findViewById(R.id.tv_index);
            viewById.setText(in+"");
        }
        myView.setVisibility(visibility);

        return myView;
    }

}
