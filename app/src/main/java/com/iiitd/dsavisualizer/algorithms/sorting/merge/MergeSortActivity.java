package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
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
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.florent37.viewanimator.ViewAnimator;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.runapp.others.ElementAnimationData;
import com.iiitd.dsavisualizer.utility.Util;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MergeSortActivity extends AppCompatActivity {

    DrawerLayout dl_main;
    View v_main;
    View v_menu;
    ViewStub vs_main;
    ViewStub vs_menu;
    LinearLayout ll_anim;
    ConstraintLayout cl_info;
    ImageButton btn_play;
    ImageButton btn_back;
    ImageButton btn_menu;
    ImageButton btn_code;
    ImageButton btn_backward;
    ImageButton btn_forward;
    SeekBar sb_animspeed;
    TextView tv_seqno;
    TextView tv_info;
    ScrollView sv_psuedocode;
    LinearLayout ll_psuedocode;

    SeekBar sb_arraysize;
    TextView tv_arraysize;
    Button btn_generate;
    Button btn_clear;
    Switch sw_randomarray;
    EditText et_customarray;

    MergeSort mergeSort;
    TextView[] textViews;

    Timer timer = new Timer();

    boolean isAutoPlay = false;
    boolean isRandomArray = true;
    boolean isPseudocode = true;
    int autoAnimSpeed = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);

        // findViewById
        dl_main = findViewById(R.id.dl_main);
        vs_main = findViewById(R.id.vs_main);
        vs_menu = findViewById(R.id.vs_menu);
        vs_main.setLayoutResource(R.layout.activity_merge_sort);
        vs_menu.setLayoutResource(R.layout.controls_merge_sort);
        vs_main.setBackgroundColor(Color.GREEN);
        vs_menu.setBackgroundColor(Color.YELLOW);
        v_main = vs_main.inflate();
        v_menu = vs_menu.inflate();

        ll_anim = v_main.findViewById(R.id.ll_anim);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        btn_play = v_main.findViewById(R.id.btn_play);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_code = v_main.findViewById(R.id.btn_code);
        btn_back = v_main.findViewById(R.id.btn_back);
        btn_backward = v_main.findViewById(R.id.btn_backward);
        btn_forward = v_main.findViewById(R.id.btn_forward);
        tv_seqno = v_main.findViewById(R.id.tv_seqno);
        tv_info = v_main.findViewById(R.id.tv_value_info);
        sv_psuedocode = v_main.findViewById(R.id.sv_psuedocode);
        ll_psuedocode = v_main.findViewById(R.id.ll_pseudocode);
        cl_info = v_main.findViewById(R.id.cl_info);

        sb_arraysize = v_menu.findViewById(R.id.sb_arraysize);
        tv_arraysize = v_menu.findViewById(R.id.tv_arraysize);
        btn_generate = v_menu.findViewById(R.id.btn_generate);
        btn_clear = v_menu.findViewById(R.id.btn_clear);
        sw_randomarray = v_menu.findViewById(R.id.sw_randomarray);
        et_customarray = v_menu.findViewById(R.id.et_customarray);

        tv_arraysize.setText(String.valueOf(sb_arraysize.getProgress() + 1));

        addPseudocode();
        initViews();

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
                        int[] data = new int[customInput.length];
                        try {
                            for (int i = 0; i < data.length; i++) {
                                data[i] = Integer.parseInt(customInput[i]);
                            }
                            tv_arraysize.setText(String.valueOf(customInput.length));
                        }
                        catch (NumberFormatException e){
                            et_customarray.setError("Bad Input");
                            tv_arraysize.setText("0");
                        }
                    }
                }
            }
        });

        et_customarray.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null || !s.toString().isEmpty()){
                    String[] customInput = s.toString().split(",");
                    int[] data = new int[customInput.length];
                    try {
                        for (int i = 0; i < data.length; i++) {
                            data[i] = Integer.parseInt(customInput[i]);
                        }
                        tv_arraysize.setText(String.valueOf(customInput.length));
                    }
                    catch (NumberFormatException e){
                        et_customarray.setError("Bad Input");
                        tv_arraysize.setText("0");
                    }
                }
            }
        });

        // Auto Animation Speed
        sb_animspeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int speed = (1000 - progress * 10) + 500;
                autoAnimSpeed = speed;
                isAutoPlay = false;
                btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_play_arrow_24));
                timer.cancel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Auto Animation Play/Pause Button
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mergeSort != null){
                    if(isAutoPlay){
                        isAutoPlay = false;
                        btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_play_arrow_24));
                        timer.cancel();
                    }
                    else{
                        isAutoPlay = true;
                        btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_pause_24));
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (mergeSort.sequence.curSeqNo < mergeSort.sequence.size)
                                    onForwardClick();
                                else {
                                    isAutoPlay = false;
                                    btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_play_arrow_24));
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
                btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this,R.drawable.ic_baseline_play_arrow_24));
                timer.cancel();
                onBackwardClick();
            }
        });

        // One Animation Step-Forward
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_play_arrow_24));
                timer.cancel();
                onForwardClick();
            }
        });

        // Floating Menu Button
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dl_main.isOpen()) {
                    isAutoPlay = false;
                    btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_play_arrow_24));
                    timer.cancel();
                    dl_main.openDrawer(Gravity.RIGHT);
                }
            }
        });

        // Floating Meu Button Show/Hide
        dl_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset){
                if(slideOffset <= 0.40){
                    isAutoPlay = false;
                    btn_play.setImageDrawable(ContextCompat.getDrawable(MergeSortActivity.this, R.drawable.ic_baseline_play_arrow_24));
                    timer.cancel();
                    btn_menu.setVisibility(View.VISIBLE);
                }
                else{
                    btn_menu.setVisibility(View.INVISIBLE);
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
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
                        break;
                }

                v.onTouchEvent(event);
                return true;
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
                if(mergeSort != null){
                    mergeSort.linearLayout.removeAllViews();
                    mergeSort = null;
                }

                if(isRandomArray){
                    mergeSort = new MergeSort(MergeSortActivity.this, ll_anim, arraySize);
                }
                else {
                    String customArray = et_customarray.getText().toString();
                    if(customArray != null || !customArray.isEmpty()){
                        String[] customInput = customArray.split(",");
                        int[] data = new int[customInput.length];
                        try {
                            for (int i = 0; i < data.length; i++) {
                                data[i] = Integer.parseInt(customInput[i]);
                            }
                            mergeSort = new MergeSort(MergeSortActivity.this, ll_anim, data);
                        }
                        catch (NumberFormatException e){
                            et_customarray.setError("Bad Input");
                            mergeSort = null;
                        }
                    }
                }
                initViews();
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mergeSort.linearLayout.removeAllViews();
                mergeSort = null;
                initViews();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ADD ALERT DIALOG BOX FOR CONFIRMING
                finish();
            }
        });

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-----------------------------**");
                if(isPseudocode) {
                    sv_psuedocode.setVisibility(View.GONE);
                    sv_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mergeSort != null){
                                int width = ll_anim.getWidth();
                                int div = width / mergeSort.arraySize;

                                for(int i=0;i<mergeSort.arraySize;i++){
                                    int position = mergeSort.positions[i];
                                    int x = position*div;
                                    System.out.println("x="+x);
//                                    mergeSort.views[i].setX(x);
                                    float v1 = x - mergeSort.views[i].getX();
                                    mergeSort.views[i].animate().translationXBy(v1).start();
                                }
                            }
                        }
                    }, 0);
                }
                else {
                    sv_psuedocode.setVisibility(View.VISIBLE);
                    sv_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mergeSort != null){
                                int width = ll_anim.getWidth();
                                int div = width / mergeSort.arraySize;

                                for(int i=0;i<mergeSort.arraySize;i++){
                                    int position = mergeSort.positions[i];
                                    int x = position*div;
                                    System.out.println("x="+x);
//                                    mergeSort.views[i].setX(x);
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
        tv_seqno.setText("0");
        tv_info.setText("-");
        if(mergeSort != null){
            Util.setText(tv_info, mergeSort.sequence.animationStates.get(0).info);
            Util.highlightViews(mergeSort.sequence.views, Color.BLUE, Color.GREEN,
                    mergeSort.sequence.animationStates.get(0).highlightIndexes);
        }
        for(TextView textView : textViews){
            textView.setBackgroundColor(Color.GREEN);
        }
    }

    private void addPseudocode(){
        int sizeOfPseudocode = MergeSortInfo.psuedocode.length;
        if (sizeOfPseudocode == 0) throw new AssertionError();
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
                    tv_seqno.setText(String.valueOf(curSeqNo));
                    if(curSeqNo < mergeSort.sequence.size) {
                        String state = mergeSort.sequence.animationStates.get(curSeqNo).state;
                        if(MergeSortInfo.map.containsKey(state)){
                            Integer[] integers = MergeSortInfo.map.get(state);
                            Util.changeTextViewsColors(sv_psuedocode, textViews, Color.GREEN, Color.YELLOW, integers);
                        }
                        Util.setText(tv_info, mergeSort.sequence.animationStates.get(curSeqNo).info);
                        Util.highlightViews(mergeSort.sequence.views, Color.BLUE, Color.GREEN,
                                mergeSort.sequence.animationStates.get(curSeqNo).highlightIndexes);
                        System.out.println();
                    }
                    else{
                        Util.changeTextViewsColors(sv_psuedocode, textViews, Color.GREEN, Color.YELLOW, null);
                        Util.highlightViews(mergeSort.sequence.views, Color.BLUE, Color.GREEN,
                                null);
                        Util.setText(tv_info, "-");
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
                    tv_seqno.setText(String.valueOf(curSeqNo));
                    if(curSeqNo > 0) {
                        Util.setText(tv_info, mergeSort.sequence.animationStates.get(curSeqNo).info);
                        String state = mergeSort.sequence.animationStates.get(curSeqNo).state;
                        if (MergeSortInfo.map.containsKey(state)) {
                            Integer[] integers = MergeSortInfo.map.get(state);
                            Util.changeTextViewsColors(sv_psuedocode, textViews, Color.GREEN, Color.YELLOW, integers);
                        }
                        Util.highlightViews(mergeSort.sequence.views, Color.BLUE, Color.GREEN,
                                mergeSort.sequence.animationStates.get(curSeqNo).highlightIndexes);
                    }
                    else{
                        Util.setText(tv_info, mergeSort.sequence.animationStates.get(0).info);
                        Util.highlightViews(mergeSort.sequence.views, Color.BLUE, Color.GREEN,
                                mergeSort.sequence.animationStates.get(0).highlightIndexes);
                    }
                }
            });
        }
    }
}