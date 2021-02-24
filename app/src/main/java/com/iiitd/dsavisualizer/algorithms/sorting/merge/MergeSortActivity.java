package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.insertion.InsertionSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortActivity;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortActivity;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.Timer;
import java.util.TimerTask;

public class MergeSortActivity extends AppCompatActivity {

    Context context;

    DrawerLayout dl_main;
    View v_main;
    View v_menu_left;
    View v_menu_right;
    ViewStub vs_main;
    ViewStub vs_menu_left;
    ViewStub vs_menu_right;
    LinearLayout ll_anim;
    ConstraintLayout cl_info;
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
    Button btn_generate;
    Button btn_clear;
    Switch sw_randomarray;
    EditText et_customarray;

    MergeSort mergeSort;
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
    boolean isPseudocode = true;
    int autoAnimSpeed = AppSettings.DEFAULT_ANIM_SPEED;
    final int LAYOUT_MAIN = R.layout.activity_sorting;
    final int LAYOUT_LEFT = R.layout.navigation_sorting;
    final int LAYOUT_RIGHT = R.layout.controls_merge_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_base);
        context = this;

        // findViewById
        dl_main = findViewById(R.id.dl_main);
        vs_main = findViewById(R.id.vs_main);
        vs_menu_left = findViewById(R.id.vs_menu_left);
        vs_menu_right = findViewById(R.id.vs_menu_right);
        vs_main.setLayoutResource(LAYOUT_MAIN);
        vs_menu_left.setLayoutResource(LAYOUT_LEFT);
        vs_menu_right.setLayoutResource(LAYOUT_RIGHT);
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
        cl_info = v_main.findViewById(R.id.cl_info);

        btn_closemenu = v_menu_right.findViewById(R.id.btn_closemenu);
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

        addPseudocode();
        initViews();
        initNavigation();

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
               if (mergeSort != null && isAutoPlay) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (mergeSort.sequence.curSeqNo < mergeSort.sequence.size)
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
                if(mergeSort != null){
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
                                if (mergeSort.sequence.curSeqNo < mergeSort.sequence.size)
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
                if(mergeSort != null) {
                    isAutoPlay = false;
                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                    timer.cancel();
                    comparisons = String.valueOf(mergeSort.comparisons);
                }

                tv_name.setText(MergeSortStats.name);
                tv_avg.setText(MergeSortStats.avg);
                tv_worst.setText(MergeSortStats.worst);
                tv_best.setText(MergeSortStats.best);
                tv_space.setText(MergeSortStats.space);
                tv_stable.setText(MergeSortStats.stable);
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

        // Menu Button
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dl_main.isOpen()) {
                    isAutoPlay = false;
                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                    timer.cancel();
                    dl_main.openDrawer(GravityCompat.END);
                }
            }
        });

        // Stop auto animation on drawer open
        dl_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset){
                if(slideOffset <= 0.40){
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

        // Close Menu Control
        btn_closemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        // Generates Random Numbers
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int arraySize = sb_arraysize.getProgress() + 1;
                if(mergeSort != null){
                    mergeSort.linearLayout.removeAllViews();
                    mergeSort = null;
                }

                if(isRandomArray){
                    mergeSort = new MergeSort(context, ll_anim, arraySize);
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
                                mergeSort = new MergeSort(context, ll_anim, data);
                            }
                            catch (NumberFormatException e){
                                et_customarray.setError("Bad Input");
                                mergeSort = null;
                            }
                        }
                    }
                }
                initViews();
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mergeSort != null) {
                    mergeSort.linearLayout.removeAllViews();
                    mergeSort = null;
                }
                initViews();
            }
        });

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();

                dl_main.openDrawer(GravityCompat.START);
            }
        });

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPseudocode) {
                    cl_psuedocode.setVisibility(View.GONE);
                    cl_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mergeSort != null){
                                int width = ll_anim.getWidth();
                                int div = width / mergeSort.arraySize;

                                for(int i=0;i<mergeSort.arraySize;i++){
                                    int position = mergeSort.positions[i];
                                    int x = position*div;
                                    float v1 = x - mergeSort.views[i].getX();
                                    mergeSort.views[i].animate().translationXBy(v1).start();
                                }
                            }
                        }
                    }, 0);
                }
                else {
                    cl_psuedocode.setVisibility(View.VISIBLE);
                    cl_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mergeSort != null){
                                int width = ll_anim.getWidth();
                                int div = width / mergeSort.arraySize;

                                for(int i=0;i<mergeSort.arraySize;i++){
                                    int position = mergeSort.positions[i];
                                    int x = position*div;
                                    float v1 = x - mergeSort.views[i].getX();
                                    mergeSort.views[i].animate().translationXBy(v1).start();
                                }
                            }
                        }
                    }, 0);

                }
                isPseudocode = !isPseudocode;
            }
        });

    }

    private void initViews() {
        if(mergeSort != null){
            tv_seqno.setText("0 / " + mergeSort.sequence.sortingAnimationStates.size());
            UtilUI.setText(tv_info, mergeSort.sequence.sortingAnimationStates.get(0).info);
            UtilUI.highlightViews(context, mergeSort.sequence.views,
                    mergeSort.sequence.sortingAnimationStates.get(0).highlightIndexes);
            String state = mergeSort.sequence.sortingAnimationStates.get(0).state;
            if(MergeSortInfo.map.containsKey(state)){
                Integer[] integers = MergeSortInfo.map.get(state);
                UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
            }
        }
        else{
            tv_seqno.setText("0 / 0");
            UtilUI.setText(tv_info, "-");
            UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, null);
        }

    }

    private void addPseudocode(){
        int sizeOfPseudocode = MergeSortInfo.psuedocode.length;
        textViews = new TextView[sizeOfPseudocode];
        for(int i=0;i<sizeOfPseudocode;i++){
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setText(MergeSortInfo.psuedocode[i]);
            textView.setPadding(5, 0,0,0);
            textViews[i] = textView;
            ll_psuedocode.addView(textView);
        }

        for(int i : MergeSortInfo.boldIndexes){
            textViews[i].setTypeface(textViews[i].getTypeface(), Typeface.BOLD);
        }
    }

    private void onForwardClick(){
        if (mergeSort != null) {
            mergeSort.forward();
            final int curSeqNo = mergeSort.sequence.curSeqNo;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_seqno.setText(curSeqNo + " / " + mergeSort.sequence.sortingAnimationStates.size());
                    if(curSeqNo < mergeSort.sequence.size) {
                        String state = mergeSort.sequence.sortingAnimationStates.get(curSeqNo).state;
                        if(MergeSortInfo.map.containsKey(state)){
                            Integer[] integers = MergeSortInfo.map.get(state);
                            UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
                        }
                        UtilUI.setText(tv_info, mergeSort.sequence.sortingAnimationStates.get(curSeqNo).info);
                        UtilUI.highlightViews(context, mergeSort.sequence.views,
                                mergeSort.sequence.sortingAnimationStates.get(curSeqNo).highlightIndexes);
                    }
                    else{
                        UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, null);
                        UtilUI.highlightViews(context, mergeSort.sequence.views,null);
                        UtilUI.setText(tv_info, "Array is sorted");
                    }
                }
            });
        }
    }

    private void onBackwardClick(){
        if (mergeSort != null) {
            mergeSort.backward();
            final int curSeqNo = mergeSort.sequence.curSeqNo;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_seqno.setText(curSeqNo + " / " + mergeSort.sequence.sortingAnimationStates.size());
                    UtilUI.setText(tv_info, mergeSort.sequence.sortingAnimationStates.get(curSeqNo).info);
                    String state = mergeSort.sequence.sortingAnimationStates.get(curSeqNo).state;
                    if (MergeSortInfo.map.containsKey(state)) {
                        Integer[] integers = MergeSortInfo.map.get(state);
                        UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
                    }
                    UtilUI.highlightViews(context, mergeSort.sequence.views,
                            mergeSort.sequence.sortingAnimationStates.get(curSeqNo).highlightIndexes);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (dl_main.isDrawerOpen(GravityCompat.START) || dl_main.isDrawerOpen(GravityCompat.END)){
            dl_main.closeDrawer(GravityCompat.START);
            dl_main.closeDrawer(GravityCompat.END);
        }
        else {
            back();
        }
    }

    private void back(){
        isAutoPlay = false;
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
                finish();
            }
        });
    }

    private void initNavigation() {
        int color = UtilUI.getCurrentThemeColor(context, R.attr.shade);

        cl_mergesort.setBackgroundColor(color);

        cl_bubblesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(context, BubbleSortActivity.class));
            }
        });

        cl_selectionsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(context, SelectionSortActivity.class));
            }
        });

        cl_insertionsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(context, InsertionSortActivity.class));
            }
        });

        cl_mergesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.closeDrawer(GravityCompat.START);
            }
        });

        cl_quicksort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(context, QuickSortActivity.class));
            }
        });

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_closenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.closeDrawer(GravityCompat.START);
            }
        });

    }

}