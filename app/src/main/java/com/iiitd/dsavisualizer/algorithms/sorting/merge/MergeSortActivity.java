package com.iiitd.dsavisualizer.algorithms.sorting.merge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.Util;


public class MergeSortActivity extends AppCompatActivity {

    DrawerLayout dl_main;
    View v_main;
    View v_menu;
    ViewStub vs_main;
    ViewStub vs_menu;
    LinearLayout ll_anim;
    LinearLayout ll_code;
    Button btn_backward;
    Button btn_forward;
    TextView tv_seqno;
    TextView tv_info;
    TextView tv_curinst;
    TextView tv_nextinst;
    ScrollView sv_psuedocode;
    LinearLayout ll_psuedocode;
    FloatingActionButton fab_menubutton;

    SeekBar sb_arraysize;
    TextView tv_arraysize;
    Button btn_generate;
    Button btn_clear;

    MergeSort mergeSort;
    TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);


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
        ll_code = v_main.findViewById(R.id.ll_code);
        btn_backward = v_main.findViewById(R.id.btn_backward);
        btn_forward = v_main.findViewById(R.id.btn_forward);
        tv_seqno = v_main.findViewById(R.id.tv_seqno);
        tv_info = v_main.findViewById(R.id.tv_info);
        tv_curinst = v_main.findViewById(R.id.tv_curinst);
        tv_nextinst = v_main.findViewById(R.id.tv_nextinst);
        sv_psuedocode = v_main.findViewById(R.id.sv_psuedocode);
        ll_psuedocode = v_main.findViewById(R.id.ll_pseudocode);
        fab_menubutton = v_main.findViewById(R.id.fab_menubutton);

        sb_arraysize = v_menu.findViewById(R.id.sb_arraysize);
        tv_arraysize = v_menu.findViewById(R.id.tv_arraysize);
        btn_generate = v_menu.findViewById(R.id.btn_generate);
        btn_clear = v_menu.findViewById(R.id.btn_clear);

        tv_arraysize.setText(String.valueOf(sb_arraysize.getProgress() + 1));

        int sizeOfPseudocode = MergeSortInfo.psuedocode.length;
        if (sizeOfPseudocode == 0) throw new AssertionError();
        textViews = new TextView[sizeOfPseudocode];
        for(int i=0;i<sizeOfPseudocode;i++){
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setText(MergeSortInfo.psuedocode[i]);
            textViews[i] = textView;
            ll_psuedocode.addView(textView);

        }


        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("backward");
                if (mergeSort != null) {
                    mergeSort.backward();
                    int curSeqNo = mergeSort.sequence.curSeqNo;
                    tv_seqno.setText(String.valueOf(curSeqNo));
                    if(curSeqNo >= 1)
                        Util.setText(tv_curinst, "Last Inst", mergeSort.sequence.animationStates.get(curSeqNo-1).state);
                    else{
                        Util.setText(tv_curinst, "Last Inst", "Started");
                    }
                    Util.setText(tv_nextinst, "Next Inst", mergeSort.sequence.animationStates.get(curSeqNo).state);
                    Util.setText(tv_info, "info", mergeSort.sequence.animationStates.get(curSeqNo).info);
                    String state = mergeSort.sequence.animationStates.get(curSeqNo).state;
                    if(MergeSortInfo.map.containsKey(state)){
                        Integer[] integers = MergeSortInfo.map.get(state);
                        Util.changeTextViewsColors(textViews, Color.GREEN, Color.YELLOW, integers);
                    }
                }

            }
        });

        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("forward");
                if (mergeSort != null) {
                    mergeSort.forward();
                    int curSeqNo = mergeSort.sequence.curSeqNo;
                    tv_seqno.setText(String.valueOf(curSeqNo));
                    Util.setText(tv_curinst, "Last Inst", mergeSort.sequence.animationStates.get(curSeqNo-1).state);
//                    tv_curinst.setText();
                    if(curSeqNo < mergeSort.sequence.size) {
                        String state = mergeSort.sequence.animationStates.get(curSeqNo).state;
                        Util.setText(tv_nextinst, "Next Inst", mergeSort.sequence.animationStates.get(curSeqNo).state);
                        if(MergeSortInfo.map.containsKey(state)){
                            Integer[] integers = MergeSortInfo.map.get(state);
                            Util.changeTextViewsColors(textViews, Color.GREEN, Color.YELLOW, integers);
                        }
                        Util.setText(tv_info, "info", mergeSort.sequence.animationStates.get(curSeqNo).info);
                    }
                    else{
                        Util.setText(tv_nextinst, "Next Inst", "Finished");
                    }

                }

            }
        });

        fab_menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!dl_main.isOpen()) {
                dl_main.openDrawer(Gravity.RIGHT);
            }
            }
        });

        dl_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset){
                System.out.println(slideOffset);
                if(slideOffset <= 0.40){
                    fab_menubutton.setVisibility(View.VISIBLE);
                }
                else{
                    fab_menubutton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView){}

            @Override
            public void onDrawerClosed(@NonNull View drawerView){}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        sb_arraysize.setOnTouchListener(new SeekBar.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        // Disallow Drawer to intercept touch events.
                        // v.getParent().requestDisallowInterceptTouchEvent(true);
                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Allow Drawer to intercept touch events.
                        // v.getParent().requestDisallowInterceptTouchEvent(false);
                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
                        break;
                }

                // Handle SeekBar touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        sb_arraysize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                tv_arraysize.setText(String.valueOf(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("generate");

                int arraySize = sb_arraysize.getProgress() + 1;
                boolean isRandomize = true;
                if(mergeSort != null){
                    mergeSort.linearLayout.removeAllViews();
                    mergeSort = null;
                    tv_seqno.setText("0");
                }
                mergeSort = new MergeSort(MergeSortActivity.this, arraySize, isRandomize, ll_anim);

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clear");
                mergeSort.linearLayout.removeAllViews();
                mergeSort = null;
                tv_seqno.setText("0");
            }
        });

        int childCount = ll_psuedocode.getChildCount();
        System.out.println("================");
        System.out.println(childCount);
        for(int i=0;i<childCount;i++){
            System.out.println(ll_psuedocode.getChildAt(i));
        }

    }
}