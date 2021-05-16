package com.iiitd.dsavisualizer.utility;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.transition.Fade;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayoutElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

// Util class for UI Related helper functions
public class UtilUI {

    public static void setText(TextView textView, String label, String data){
        textView.setText(label + " : " + data);
    }

    public static void setText(TextView textView, String data){
        textView.setText(data);
    }

    public static void setText(TextView textView, SpannableString data){
        textView.setText(data);
    }

    public static void setText(TextView textView, SpannableStringBuilder data){
        textView.setText(data);
    }

    public static void setTextInBigONotation(TextView textView, String data){
        textView.setText(String.format("O(%s)", data));
    }

    public static void setTextInBigONotation(TextView textView, Spanned data){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("O(");
        spannableString.append(SpannedString.valueOf(data));
        spannableString.append(")");
        textView.setText(spannableString);
    }

    public static int getRandomColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    // Updates psuedocode in sorting algorithms based on indexes
    public static void changeTextViewsColors(Context context, ScrollView scrollView, TextView[] textViews, Integer[] indexes){
        for(TextView textView : textViews){
            textView.setTypeface(null, Typeface.NORMAL);
        }

        if(indexes != null) {
            scrollView.smoothScrollTo(0, (int) textViews[indexes[0]].getY());

            for (int i : indexes) {
                textViews[i].setTypeface(textViews[i].getTypeface(), Typeface.BOLD);
            }
        }
    }

    // Highlight views in sorting algorithms based on indexes
    public static void highlightViews(Context context, View[] views, ArrayList<Integer> indexes){
        for(View view : views){
            view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_normal));
        }

        if(indexes != null) {
            for (int i : indexes) {
                views[i].findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_highlighted));
            }
        }
    }

    public static Drawable getDrawable(Context context, int id){
        return ContextCompat.getDrawable(context, id);
    }

    // Changes pointers in quicksort algorithm
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

    // Used by Quick Sort
    public static void highlightSortedElements(Context context, ArrayList<Pair<Integer, Integer>> sortedIndexes,
                                               View[] views, int curSeqNo){
        if(curSeqNo == -1){
            for(View view : views){
                view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_done));
            }
            return;
        }

        for(View view : views){
            view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_normal));
        }

        for(Pair<Integer, Integer> pair : sortedIndexes){
            if(curSeqNo >= pair.first){
                views[pair.second].findViewById(R.id.tv_elementvalue)
                        .setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_done));
            }
        }

    }

    // Used by Bubble Sort and Quick Sort
    public static void highlightCombined(Context context, ArrayList<Pair<Integer, Integer>> sortedIndexes,
                                         View[] views, int curSeqNo, ArrayList<Integer> indexes){
        if(curSeqNo == -1){
            for(View view : views){
                view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_done));
            }
            return;
        }

        for(View view : views){
            view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_normal));
        }

        if(indexes != null) {
            for (int i : indexes) {
                views[i].findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_highlighted));
            }
        }

        for(Pair<Integer, Integer> pair : sortedIndexes){
            if(curSeqNo >= pair.first){
                views[pair.second].findViewById(R.id.tv_elementvalue)
                        .setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_done));
            }
        }

    }

    // Used by Insertion Sort
    public static void highlightCombinedForInsertionSort(Context context, ArrayList<Pair<Integer, Integer>> sortedIndexes,
                                                         View[] views, int curSeqNo, ArrayList<Integer> indexes){
        if(curSeqNo == -1){
            for(View view : views){
                view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_done));
            }
            return;
        }

        for(View view : views){
            view.findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_normal));
        }

        ArrayList<Integer> high = new ArrayList<>();
        if(indexes != null) {
            for (int i : indexes) {
                high.add(i);
                views[i].findViewById(R.id.tv_elementvalue).setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_highlighted));
            }
        }

        for(Pair<Integer, Integer> pair : sortedIndexes){
            if(curSeqNo >= pair.first) {
                if (!high.contains(pair.second)) {
                    views[pair.second].findViewById(R.id.tv_elementvalue)
                            .setBackground(getDrawable(context, R.drawable.rounded_rect_sorting_element_done));
                }
            }
        }

    }

    // Used by BST and AVL for tree nodes
    public static View getTreeNodeView(Context context, LayoutInflater layoutInflater,
                                       TreeLayoutElement treeLayoutElement, int height, int row, int col){
        int layout = 0;
        int weight = treeLayoutElement.weight;
        int visibility = View.INVISIBLE;
        switch (treeLayoutElement.type){
            case EMPTY:
                layout = R.layout.element_treenode_empty;
                break;
            case ARROW:
                layout = R.layout.element_treenode_arrow;
                break;
            case ELEMENT:
                layout = R.layout.element_treenode_element;
                break;
        }
        View myView = layoutInflater.inflate(layout, null);
        myView.setLayoutParams(new TableRow.LayoutParams(0, height, weight));

        if(row > 0 && layout == R.layout.element_treenode_arrow){
            int arrowLayout = R.drawable.trees_arrow_height_1;
            if(row == 1){
                arrowLayout = R.drawable.trees_arrow_height_1;
            }
            else if(row == 3){
                arrowLayout = R.drawable.trees_arrow_height_2;
            }
            else if(row == 5){
                arrowLayout = R.drawable.trees_arrow_height_3;
            }

            //if last arrow row, then mod is reversed, hence ++
            if(row == 5)
                col++;
            if(((col-1)/2) % 2 == 1)
                myView.setRotationY(180);

            FrameLayout frameLayout = myView.findViewById(R.id.fl_arrow);
            frameLayout.setBackground(getDrawable(context, arrowLayout));
        }

        /* FOR DEBUGGING PURPOSE's ONLY, HARDCODED STUFF< HANDLE WITH CARE
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
        */

        myView.setVisibility(visibility);

        return myView;
    }

    // Converts pixels to DP
    public static float pxToDp(Context context, int px){
        return px / context.getResources().getDisplayMetrics().density;
    }

    // Converts DP to pixels
    public static float dpToPx(Context context, int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // Converts mm to pixels
    public static float mmToPx(Context context, int mm){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, context.getResources().getDisplayMetrics());
    }

    // Returns current app theme based on SharedPreferences
    public static int getCurrentAppTheme(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppSettings.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(AppSettings.CURRENT_THEME_KEY, 1);
        int currentThemeID;

        switch (currentTheme){
            case 1:
                currentThemeID = R.style.Theme_Blue;
                break;
            case 2:
                currentThemeID = R.style.Theme_Purple;
                break;
            case 3:
                currentThemeID = R.style.Theme_Green;
                break;
            case 4:
                currentThemeID = R.style.Theme_Orange;
                break;
            case 5:
                currentThemeID = R.style.Theme_Brown;
                break;
            case 6:
                currentThemeID = R.style.Theme_Pink;
                break;
            default:
                currentThemeID = R.style.Theme_Blue;
                break;
        }

        return currentThemeID;
    }

    // Changes current app theme based on themeNumber and also saves to SharedPreferences
    public static void changeCurrentAppTheme(Context context, int themeNumber){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppSettings.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AppSettings.CURRENT_THEME_KEY, themeNumber);
        editor.apply();
    }

    // Returns colorInt based on current app theme
    @ColorInt public static int getCurrentThemeColor(Context context, int attr){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        @ColorInt int color = typedValue.data;

        return color;
    }

    // Returns onBoardingState from SharedPreferences
    // false == show onBoarding, true = don't show
    public static boolean getTutorialState(Context context, String id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppSettings.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        boolean state = sharedPreferences.getBoolean(id, false);

        return state;
    }

    // Saves onBoardingState to SharedPreferences
    // false == show onBoarding, true = don't show
    public static void setTutorialState(Context context, String id, boolean state){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppSettings.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(id, state);
        editor.apply();
    }

    // Returns unicode string for infinity symbol
    public static String getInfinity(){
        return "\u221E";
    }

    // Returns unicode string for left arrow symbol
    public static String getLeftArrow(){
        return "\u2190";
    }

    // Returns unicode string for right arrow symbol
    public static String getRightArrow(){
        return "\u2192";
    }

    // Returns SpannableString for infinity symbol
    public static SpannableString getInfinitySpannableString(Context context, TextView textView){
        return getDrawableSpannableString(context, textView, R.drawable.graphs_infinity_symbol, DynamicDrawableSpan.ALIGN_BOTTOM);
    }

    // Returns SpannableString for left arrow symbol
    public static SpannableString getLeftArrowSpannableString(Context context, TextView textView){
        return getDrawableSpannableString(context, textView, R.drawable.graphs_leftarrow_symbol, DynamicDrawableSpan.ALIGN_BASELINE);
    }

    // Returns SpannableString for passed drawable based on passed textView and alignment
    public static SpannableString getDrawableSpannableString(Context context, TextView textView, int drawable, int alignment){
        Drawable infinity = ContextCompat.getDrawable(context, drawable);
        float ascent = textView.getPaint().getFontMetrics().ascent;
        int h = (int) -ascent;
        infinity.setBounds(0,0, h, h);
        SpannableString stringWithImage = new SpannableString("*");
        stringWithImage.setSpan(new ImageSpan(infinity, alignment),
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringWithImage;
    }

    // Returns SpannableStringBuilder for passed string [ used by GraphActivity ]
    public static SpannableStringBuilder stringToSpannableStringBuilder(Context context, TextView textView, String string){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for(char c : string.toCharArray()){
            String cur = String.valueOf(c);
            if(cur.equals(getInfinity())){
                spannableStringBuilder.append(" ");
                spannableStringBuilder.append(getInfinitySpannableString(context, textView));
                spannableStringBuilder.append(" ");
            }
            else if(cur.equals(getLeftArrow())){
                spannableStringBuilder.append(getLeftArrowSpannableString(context, textView));
            }
            else {
                spannableStringBuilder.append(c);
            }
        }

        return spannableStringBuilder;
    }

    public static void startActivity(Activity start, Class<?> end){
        Intent intent = new Intent(start, end);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(start).toBundle();
        start.startActivity(intent, bundle);
    }

    public static void startActivity(Context start, Class<?> end){
        startActivity((Activity) start, end);
    }

    // Reads file from ".graph" file to a string and returns a StringBuilder
    public static String readTextFromUri(Context context, Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static void setWindowSettings(Window window){
        setWindowFullScreen(window);
        setWindowTransitionAnimations(window);
    }

    public static void setWindowFullScreen(Window window){
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void setWindowTransitionAnimations(Window window){
        window.setEnterTransition(new Fade());
        window.setExitTransition(new Fade());
    }

}