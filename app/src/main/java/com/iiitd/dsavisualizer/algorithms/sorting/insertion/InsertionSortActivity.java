package com.iiitd.dsavisualizer.algorithms.sorting.insertion;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TooltipCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.merge.MergeSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortActivity;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.runapp.activities.BaseActivity;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.Timer;
import java.util.TimerTask;

// InsertionSort FrontEnd
public class InsertionSortActivity extends BaseActivity {

    LinearLayout ll_anim;
    ImageButton btn_play;
    ImageButton btn_nav;
    ImageButton btn_menu;
    ImageButton btn_code;
    ImageButton btn_info;
    ImageButton btn_backward;
    ImageButton btn_forward;
    SeekBar sb_animspeed;
    TextView tv_seqno;
    TextView tv_info;
    ConstraintLayout cl_psuedocode;
    ScrollView sv_psuedocode;
    LinearLayout ll_psuedocode;

    SeekBar sb_arraysize;
    TextView tv_arraysize;
    ImageButton btn_closemenu;
    ImageButton btn_helpmenu;
    Button btn_generate;
    Button btn_clear;
    Switch sw_randomarray;
    EditText et_customarray;

    InsertionSort insertionSort;
    TextView[] textViews;

    ImageButton btn_closenav;
    ConstraintLayout cl_home;
    ConstraintLayout cl_bubblesort;
    ConstraintLayout cl_selectionsort;
    ConstraintLayout cl_insertionsort;
    ConstraintLayout cl_mergesort;
    ConstraintLayout cl_quicksort;

    Timer timer = new Timer();
    boolean isAutoPlay = false;
    boolean isRandomArray = true;
    int autoAnimSpeed = AppSettings.DEFAULT_ANIM_SPEED;
    final int LAYOUT_MAIN = R.layout.activity_sorting;
    final int LAYOUT_LEFT = R.layout.navigation_sorting;
    final int LAYOUT_RIGHT = R.layout.controls_insertion_sort;
    final String ONBOARDING_KEY =  AppSettings.SORTING_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(LAYOUT_MAIN, LAYOUT_LEFT, LAYOUT_RIGHT, ONBOARDING_KEY);
        super.onCreate(savedInstanceState);

        v_main = vs_main.inflate();
        v_menu_right = vs_menu_right.inflate();
        v_menu_left = vs_menu_left.inflate();

        ll_anim = v_main.findViewById(R.id.ll_anim);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        btn_play = v_main.findViewById(R.id.btn_play);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_code = v_main.findViewById(R.id.btn_code);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_nav = v_main.findViewById(R.id.btn_nav);
        btn_backward = v_main.findViewById(R.id.btn_backward);
        btn_forward = v_main.findViewById(R.id.btn_forward);
        tv_seqno = v_main.findViewById(R.id.tv_seqno);
        tv_info = v_main.findViewById(R.id.tv_info);
        cl_psuedocode = v_main.findViewById(R.id.cl_psuedocode);
        sv_psuedocode = v_main.findViewById(R.id.sv_psuedocode);
        ll_psuedocode = v_main.findViewById(R.id.ll_pseudocode);

        btn_closemenu = v_menu_right.findViewById(R.id.btn_closemenu);
        btn_helpmenu = v_menu_right.findViewById(R.id.btn_helpmenu);
        sb_arraysize = v_menu_right.findViewById(R.id.sb_arraysize);
        tv_arraysize = v_menu_right.findViewById(R.id.tv_arraysize);
        btn_generate = v_menu_right.findViewById(R.id.btn_generate);
        btn_clear = v_menu_right.findViewById(R.id.btn_clear);
        sw_randomarray = v_menu_right.findViewById(R.id.sw_randomarray);
        et_customarray = v_menu_right.findViewById(R.id.et_customarray);

        btn_closenav = v_menu_left.findViewById(R.id.btn_closenav);
        cl_home = v_menu_left.findViewById(R.id.cl_home);
        cl_bubblesort = v_menu_left.findViewById(R.id.cl_bubblesort);
        cl_selectionsort = v_menu_left.findViewById(R.id.cl_selectionsort);
        cl_insertionsort = v_menu_left.findViewById(R.id.cl_insertionsort);
        cl_mergesort = v_menu_left.findViewById(R.id.cl_mergesort);
        cl_quicksort = v_menu_left.findViewById(R.id.cl_quicksort);

        tv_arraysize.setText(String.valueOf(sb_arraysize.getProgress() + 1));

        initOnBoarding();
        initPseudoCode();
        initViews();
        initNavigation();
        initToolTipTexts();

        sw_randomarray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isRandomArray = true;
                    et_customarray.setError(null);
                    sw_randomarray.setText(sw_randomarray.getTextOn());
                    tv_arraysize.setText(String.valueOf(sb_arraysize.getProgress()+1));
                }
                else {
                    isRandomArray = false;
                    sw_randomarray.setText(sw_randomarray.getTextOff());
                    String customArray = et_customarray.getText().toString();
                    if(customArray != null || !customArray.isEmpty()){
                        String[] customInput = customArray.split(",");
                        int length = customInput.length;
                        if(length > 16){
                            et_customarray.setError("Decrease elements");
                            tv_arraysize.setText("0");
                        }
                        else {
                            int[] data = new int[length];
                            try {
                                for (int i = 0; i < data.length; i++) {
                                    data[i] = Integer.parseInt(customInput[i]);
                                }
                                tv_arraysize.setText(String.valueOf(customInput.length));
                            } catch (NumberFormatException e) {
                                et_customarray.setError("Bad Input");
                                tv_arraysize.setText("0");
                            }
                        }
                    }
                }
            }
        });

        et_customarray.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!isRandomArray) {
                    if (s != null || !s.toString().isEmpty()) {
                        String[] customInput = s.toString().split(",");
                        int length = customInput.length;
                        if(length > 16){
                            et_customarray.setError("Decrease elements");
                            tv_arraysize.setText("0");
                        }
                        else {
                            int[] data = new int[length];
                            try {
                                for (int i = 0; i < data.length; i++) {
                                    data[i] = Integer.parseInt(customInput[i]);
                                }
                                tv_arraysize.setText(String.valueOf(customInput.length));
                            } catch (NumberFormatException e) {
                                et_customarray.setError("Bad Input");
                                tv_arraysize.setText("0");
                            }
                        }
                    }
                }
            }
        });

        // Auto Animation Speed
        sb_animspeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 2500ms to 500ms
                autoAnimSpeed = (2000 - seekBar.getProgress() * 20) + 500;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               if (insertionSort != null && isAutoPlay) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (insertionSort.sequence.curSeqNo < insertionSort.sequence.size)
                                onForwardClick();
                            else {
                                isAutoPlay = false;
                                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                                timer.cancel();
                            }
                        }
                    }, 0, autoAnimSpeed);
                }
            }
        });

        // Auto Animation Play/Pause Button
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(insertionSort != null){
                    if(isAutoPlay){
                        isAutoPlay = false;
                        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        timer.cancel();
                    }
                    else{
                        isAutoPlay = true;
                        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PAUSE_BUTTON));
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (insertionSort.sequence.curSeqNo < insertionSort.sequence.size)
                                    onForwardClick();
                                else {
                                    isAutoPlay = false;
                                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                                    timer.cancel();
                                }
                            }
                        }, 0, autoAnimSpeed);
                    }
                }
            }
        });

        // One Animation Step-Back
        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();
                onBackwardClick();
            }
        });

        // One Animation Step-Forward
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();
                onForwardClick();
            }
        });

        // Info Button
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.layout_sorting_info, null);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView tv_avg = view.findViewById(R.id.tv_avg);
                TextView tv_worst = view.findViewById(R.id.tv_worst);
                TextView tv_best = view.findViewById(R.id.tv_best);
                TextView tv_space = view.findViewById(R.id.tv_space);
                TextView tv_stable = view.findViewById(R.id.tv_stable);
                TextView tv_comparisons = view.findViewById(R.id.tv_comparisons);
                ImageButton btn_close = view.findViewById(R.id.btn_close);

                String comparisons = "-";
                if(insertionSort != null) {
                    isAutoPlay = false;
                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                    timer.cancel();
                    comparisons = String.valueOf(insertionSort.comparisons);
                }

                tv_name.setText(InsertionSortStats.name);
                UtilUI.setTextInBigONotation(tv_avg, InsertionSortStats.avg);
                UtilUI.setTextInBigONotation(tv_worst, InsertionSortStats.worst);
                UtilUI.setTextInBigONotation(tv_best, InsertionSortStats.best);
                UtilUI.setTextInBigONotation(tv_space, InsertionSortStats.space);
                tv_stable.setText(InsertionSortStats.stable);
                tv_comparisons.setText(comparisons);

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(view);
                dialog.show();

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();

                openDrawer(1);
            }
        });

        btn_closenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(1);
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();

                openDrawer(2);
            }
        });

        btn_closemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(2);
            }
        });

        btn_helpmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnBoarding();
            }
        });

        // Stop auto animation on drawer open
        dl_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset){
                if(slideOffset >= 0.35){
                    isAutoPlay = false;
                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                    timer.cancel();
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView){}

            @Override
            public void onDrawerClosed(@NonNull View drawerView){}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        // Array Size Slider for generation of random numbers
        sb_arraysize.setOnTouchListener(new SeekBar.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sb_arraysize.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // Array Size Slider for changing size of array
        sb_arraysize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(isRandomArray)
                    tv_arraysize.setText(String.valueOf(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Generates Random Numbers
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int arraySize = sb_arraysize.getProgress() + 1;
                if(insertionSort != null){
                    insertionSort.linearLayout.removeAllViews();
                    insertionSort = null;
                }

                if(isRandomArray){
                    insertionSort = new InsertionSort(context, ll_anim, arraySize);
                }
                else {
                    String customArray = et_customarray.getText().toString();
                    if(customArray != null || !customArray.isEmpty()){
                        String[] customInput = customArray.split(",");
                        int length = customInput.length;
                        if(length > 16){
                            et_customarray.setError("Decrease elements");
                            tv_arraysize.setText("0");
                        }
                        else {
                            int[] data = new int[customInput.length];
                            try {
                                for (int i = 0; i < data.length; i++) {
                                    data[i] = Integer.parseInt(customInput[i]);
                                }
                                insertionSort = new InsertionSort(context, ll_anim, data);
                            }
                            catch (NumberFormatException e){
                                et_customarray.setError("Bad Input");
                                insertionSort = null;
                            }
                        }
                    }
                }

                closeDrawer(0);
                initViews();
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(insertionSort != null) {
                insertionSort.linearLayout.removeAllViews();
                insertionSort = null;
            }
            initViews();
            }
        });

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cl_psuedocode.getVisibility() == View.VISIBLE){
                    ViewAnimator.animate(cl_psuedocode).alpha(1, 0).duration(500).start().onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {
                            cl_psuedocode.setVisibility(View.GONE);
                            cl_psuedocode.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(insertionSort != null){
                                        int width = ll_anim.getWidth();
                                        int div = width / insertionSort.arraySize;

                                        for(int i = 0; i< insertionSort.arraySize; i++){
                                            int position = insertionSort.positions[i];
                                            int x = position*div;
                                            float v1 = x - insertionSort.views[i].getX();
                                            insertionSort.views[i].animate().translationXBy(v1).start();
                                        }
                                    }
                                }
                            }, 0);
                        }
                    });
                }
                else{
                    cl_psuedocode.setVisibility(View.VISIBLE);
                    ViewAnimator.animate(cl_psuedocode).alpha(0, 1).duration(500).start();
                    cl_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(insertionSort != null){
                                int width = ll_anim.getWidth();
                                int div = width / insertionSort.arraySize;

                                for(int i = 0; i< insertionSort.arraySize; i++){
                                    int position = insertionSort.positions[i];
                                    int x = position*div;
                                    float v1 = x - insertionSort.views[i].getX();
                                    insertionSort.views[i].animate().translationXBy(v1).start();
                                }
                            }
                        }
                    }, 0);
                }
            }
        });

        // Initialize with a Randomized Example
        ll_anim.post(new Runnable() {
            @Override
            public void run() {
                btn_generate.performClick();
            }
        });

    }

    private void onForwardClick(){
        if (insertionSort != null) {
            insertionSort.forward();
            final int curSeqNo = insertionSort.sequence.curSeqNo;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_seqno.setText(curSeqNo + " / " + insertionSort.sequence.sortingAnimationStates.size());
                    if(curSeqNo < insertionSort.sequence.size) {
                        String state = insertionSort.sequence.sortingAnimationStates.get(curSeqNo).state;
                        if(InsertionSortInfo.map.containsKey(state)){
                            Integer[] integers = InsertionSortInfo.map.get(state);
                            UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
                        }
                        UtilUI.setText(tv_info, insertionSort.sequence.sortingAnimationStates.get(curSeqNo).info);
                        UtilUI.highlightCombinedForInsertionSort(context, insertionSort.sortedIndexes,
                                insertionSort.views, curSeqNo,
                                insertionSort.sequence.sortingAnimationStates.get(curSeqNo).highlightIndexes);
                    }
                    else{
                        UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, null);
                        UtilUI.setText(tv_info, "Array is sorted");
                        UtilUI.highlightCombinedForInsertionSort(context, insertionSort.sortedIndexes,
                                insertionSort.views, -1,
                                null);
                    }
                }
            });
        }
    }

    private void onBackwardClick(){
        if (insertionSort != null) {
            insertionSort.backward();
            final int curSeqNo = insertionSort.sequence.curSeqNo;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_seqno.setText(curSeqNo + " / " + insertionSort.sequence.sortingAnimationStates.size());
                    UtilUI.setText(tv_info, insertionSort.sequence.sortingAnimationStates.get(curSeqNo).info);
                    String state = insertionSort.sequence.sortingAnimationStates.get(curSeqNo).state;
                    if (InsertionSortInfo.map.containsKey(state)) {
                        Integer[] integers = InsertionSortInfo.map.get(state);
                        UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
                    }

                    UtilUI.highlightCombinedForInsertionSort(context, insertionSort.sortedIndexes,
                            insertionSort.views, curSeqNo,
                            insertionSort.sequence.sortingAnimationStates.get(curSeqNo).highlightIndexes);
                }
            });
        }
    }

    @Override
    protected void initPseudoCode() {
        int sizeOfPseudocode = InsertionSortInfo.psuedocode.length;
        textViews = new TextView[sizeOfPseudocode];
        for(int i=0;i<sizeOfPseudocode;i++){
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setTextAppearance(context, R.style.S_TextView_Normal_Opp);
            textView.setText(InsertionSortInfo.psuedocode[i]);
            textView.setPadding(5, 0,0,0);
            textViews[i] = textView;
            ll_psuedocode.addView(textView);
        }

        for(int i : InsertionSortInfo.boldIndexes){
            textViews[i].setTypeface(textViews[i].getTypeface(), Typeface.BOLD);
        }
    }

    @Override
    protected void initViews() {
        if(insertionSort != null){
            tv_seqno.setText("0 / " + insertionSort.sequence.sortingAnimationStates.size());
            UtilUI.setText(tv_info, insertionSort.sequence.sortingAnimationStates.get(0).info);
            UtilUI.highlightViews(context, insertionSort.sequence.views,
                    insertionSort.sequence.sortingAnimationStates.get(0).highlightIndexes);
            String state = insertionSort.sequence.sortingAnimationStates.get(0).state;
            if(InsertionSortInfo.map.containsKey(state)){
                Integer[] integers = InsertionSortInfo.map.get(state);
                UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
            }
        }
        else{
            tv_seqno.setText("0 / 0");
            UtilUI.setText(tv_info, "-");
            UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, null);
        }
    }

    @Override
    protected void initNavigation() {
        int color = UtilUI.getCurrentThemeColor(context, R.attr.shade);

        cl_insertionsort.setBackgroundColor(color);

        cl_bubblesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
                UtilUI.startActivity(context, BubbleSortActivity.class);
            }
        });

        cl_selectionsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
                UtilUI.startActivity(context, SelectionSortActivity.class);
            }
        });

        cl_insertionsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(1);
            }
        });

        cl_mergesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
                UtilUI.startActivity(context, MergeSortActivity.class);
            }
        });

        cl_quicksort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
                UtilUI.startActivity(context, QuickSortActivity.class);
            }
        });

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

    }

    @Override
    protected void back(){isAutoPlay = false;
        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
        timer.cancel();

        View view = getLayoutInflater().inflate(R.layout.layout_back_confirmation, null);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_yes = view.findViewById(R.id.btn_yes);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAfterTransition();
            }
        });
    }

    @Override
    protected void disableUI() {}

    @Override
    protected void enableUI() {}

    @Override
    protected void initToolTipTexts(){
        TooltipCompat.setTooltipText(btn_info, "Info");
        TooltipCompat.setTooltipText(btn_code, "Show/Hide Code");
        TooltipCompat.setTooltipText(btn_menu, "Controls Menu");
        TooltipCompat.setTooltipText(btn_nav, "Navigation Menu");
        TooltipCompat.setTooltipText(btn_play, "Play/Pause");
        TooltipCompat.setTooltipText(btn_forward, "Forward");
        TooltipCompat.setTooltipText(btn_backward, "Backward");
        TooltipCompat.setTooltipText(tv_info, "Current Animation Info");
        TooltipCompat.setTooltipText(tv_seqno, "Animation Step Counter");

        // Right Menu
        TooltipCompat.setTooltipText(btn_closemenu, "Close Controls");
        TooltipCompat.setTooltipText(btn_helpmenu, "Show Help");

        // Left Menu
        TooltipCompat.setTooltipText(btn_closenav, "Close Navigation");
    }

}