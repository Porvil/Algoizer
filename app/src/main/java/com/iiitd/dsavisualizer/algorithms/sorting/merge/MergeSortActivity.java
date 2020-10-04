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
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iiitd.dsavisualizer.R;


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
    FloatingActionButton fab_menubutton;

    SeekBar sb_arraysize;
    TextView tv_arraysize;
    Button btn_generate;
    Button btn_clear;

    MergeSort mergeSort;

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
        fab_menubutton = v_main.findViewById(R.id.fab_menubutton);

        sb_arraysize = v_menu.findViewById(R.id.sb_arraysize);
        tv_arraysize = v_menu.findViewById(R.id.tv_arraysize);
        btn_generate = v_menu.findViewById(R.id.btn_generate);
        btn_clear = v_menu.findViewById(R.id.btn_clear);

        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("backward");
                if (mergeSort != null) {
                    mergeSort.backward();
                    tv_seqno.setText(String.valueOf(mergeSort.sequence.curSeqNo));
                }
            }
        });

        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("forward");
                if (mergeSort != null) {
                    mergeSort.forward();
                    tv_seqno.setText(String.valueOf(mergeSort.sequence.curSeqNo));
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
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset){}

            @Override
            public void onDrawerOpened(@NonNull View drawerView){}

            @Override
            public void onDrawerClosed(@NonNull View drawerView){}

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (!dl_main.isDrawerOpen(Gravity.RIGHT)) {
                        // Drawer started opening
                        fab_menubutton.setVisibility(View.INVISIBLE);
                    } else {
                        // Drawer started closing
                        fab_menubutton.setVisibility(View.VISIBLE);
                    }
                }
            }
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



    }
}