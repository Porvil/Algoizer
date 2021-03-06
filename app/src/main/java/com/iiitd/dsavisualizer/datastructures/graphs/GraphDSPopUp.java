package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.Collections;

public class GraphDSPopUp {

    Context context;
    LayoutInflater inflater;

    PopupWindow popupwindow;
    View parent;
    View popUpView;

    TextView iv_popupgraphname;
    ImageButton btn_minimize;
    ImageButton btn_close;
    LinearLayout ll_graphds;
    LinearLayout.LayoutParams elementLayoutParams;
    LinearLayout.LayoutParams otherLayoutParams;

    GraphAlgorithmType graphAlgorithmType;
    String title;

    int width;
    int height;
    final int MIN_WIDTH = 200;
    final int MIN_HEIGHT = 200;

    final int MINIMIZE_ICON = R.drawable.ic_baseline_remove_24;
    final int MAXIMIZE_ICON = R.drawable.ic_baseline_open_in_full_24;

    GraphDSPopUp(Context _context, int _width, int _height, View _parent){
        this.context = _context;
        this.width = _width;
        this.height = _height;
        this.parent = _parent;

        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.elementLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        this.elementLayoutParams.setMargins(30,10,30,10);

        this.otherLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) UtilUI.dpToPx(context, 40));
        this.otherLayoutParams.setMargins(30,10,30,10);

        this.popUpView = inflater.inflate(R.layout.layout_popup_dsgraph, null);

        this.ll_graphds = popUpView.findViewById(R.id.ll_graphds);
        this.btn_minimize = popUpView.findViewById(R.id.btn_minimize);
        this.btn_close = popUpView.findViewById(R.id.btn_close);
        this.iv_popupgraphname = popUpView.findViewById(R.id.tv_popupgraphname);

        popUpView.setOnTouchListener(new View.OnTouchListener() {
            int orgX;
            int orgY;
            int offsetX;
            int offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        orgY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetX = (int) event.getRawX() - orgX;
                        offsetY = (int) event.getRawY() - orgY;
                        popupwindow.update(offsetX, offsetY, -1, -1, true);
                        break;
                }
                return true;
            }
        });

        btn_minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_graphds.getVisibility() == View.VISIBLE){
                    ll_graphds.setVisibility(View.GONE);
                    int curWidth = width;
                    int curHeight = height - ll_graphds.getHeight();
                    curWidth = curWidth <= 0 ? MIN_WIDTH : curWidth;
                    curHeight = curHeight <= 0 ? MIN_HEIGHT : curHeight;
                    popupwindow.update(curWidth, curHeight);

                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MAXIMIZE_ICON));
                }
                else{
                    ll_graphds.setVisibility(View.VISIBLE);
                    popupwindow.update(width, height);
                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MINIMIZE_ICON));
                }
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        popupwindow = new PopupWindow(popUpView, width, height, false);
    }

    void create(String title, final GraphAlgorithmType graphAlgorithmType){
        this.title = title;
        this.graphAlgorithmType = graphAlgorithmType;
        this.iv_popupgraphname.setText(title);

        ll_graphds.post(new Runnable() {
            @Override
            public void run() {
                // Remove all Old Views
                ll_graphds.removeAllViews();

                // BFS (Queue)
                if(graphAlgorithmType == GraphAlgorithmType.BFS) {
                    View emptyQueueView = inflater.inflate(R.layout.element_graph_ds, null);
                    emptyQueueView.setLayoutParams(otherLayoutParams);
                    emptyQueueView.setPadding(5, 5, 5, 5);
                    TextView tv_emptyQueueView = emptyQueueView.findViewById(R.id.tv_elementvalue);
                    tv_emptyQueueView.setText("Empty");
                    tv_emptyQueueView.setTextColor(Color.WHITE);
                    tv_emptyQueueView.setTextSize(16);
                    tv_emptyQueueView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                    ll_graphds.addView(emptyQueueView);
                }
                // DFS (Stack)
                else if(graphAlgorithmType == GraphAlgorithmType.DFS) {
                    View emptyStackView = inflater.inflate(R.layout.element_graph_ds, null);
                    emptyStackView.setLayoutParams(otherLayoutParams);
                    emptyStackView.setPadding(5, 5, 5, 5);
                    TextView tv_emptyStackView = emptyStackView.findViewById(R.id.tv_elementvalue);
                    tv_emptyStackView.setText("Empty");
                    tv_emptyStackView.setTextColor(Color.WHITE);
                    tv_emptyStackView.setTextSize(16);
                    tv_emptyStackView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                    ll_graphds.addView(emptyStackView);
                }

            }
        });

    }

    // arrayList could represent both BFS(queue) and DFS(stack) as per current DS selected
    void update(final ArrayList<Integer> arrayList){

        ll_graphds.post(new Runnable() {
            @Override
            public void run() {
                // Remove all Old Views
                ll_graphds.removeAllViews();

                // BFS (Queue)
                if(graphAlgorithmType == GraphAlgorithmType.BFS) {
                    if(arrayList.size() == 0){
                        View emptyQueueView = inflater.inflate(R.layout.element_graph_ds, null);
                        emptyQueueView.setLayoutParams(otherLayoutParams);
                        emptyQueueView.setPadding(5, 5, 5, 5);
                        TextView tv_emptyQueueView = emptyQueueView.findViewById(R.id.tv_elementvalue);
                        tv_emptyQueueView.setText("Empty");
                        tv_emptyQueueView.setTextColor(Color.WHITE);
                        tv_emptyQueueView.setTextSize(16);
                        tv_emptyQueueView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                        ll_graphds.addView(emptyQueueView);
                    }
                    else {
                        // Start (Pop)
                        View queueFrontView = inflater.inflate(R.layout.element_graph_ds, null);
                        queueFrontView.setLayoutParams(otherLayoutParams);
                        queueFrontView.setPadding(5, 5, 5, 5);
                        TextView tv_queueFrontView = queueFrontView.findViewById(R.id.tv_elementvalue);
                        tv_queueFrontView.setText("Front");
                        tv_queueFrontView.setTextColor(Color.WHITE);
                        tv_queueFrontView.setTextSize(16);
                        tv_queueFrontView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                        ll_graphds.addView(queueFrontView);

                        // Queue elements
                        for (Integer i : arrayList) {
                            View myView = inflater.inflate(R.layout.element_graph_ds, null);
                            myView.setLayoutParams(elementLayoutParams);
                            myView.setPadding(5, 5, 5, 5);
                            TextView tv = myView.findViewById(R.id.tv_elementvalue);
                            tv.setText(String.valueOf(i));
                            tv.setTextColor(Color.WHITE);
                            tv.setTextSize(16);
                            tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                            ll_graphds.addView(myView);
                        }

                        // End (Push)
                        View queueEndView = inflater.inflate(R.layout.element_graph_ds, null);
                        queueEndView.setLayoutParams(otherLayoutParams);
                        queueEndView.setPadding(5, 5, 5, 5);
                        TextView tv_queueEndView = queueEndView.findViewById(R.id.tv_elementvalue);
                        tv_queueEndView.setText("End");
                        tv_queueEndView.setTextColor(Color.WHITE);
                        tv_queueEndView.setTextSize(16);
                        tv_queueEndView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                        ll_graphds.addView(queueEndView);
                    }
                }
                // DFS (Stack)
                else if(graphAlgorithmType == GraphAlgorithmType.DFS) {
                    if(arrayList.size() == 0){
                        View emptyStackView = inflater.inflate(R.layout.element_graph_ds, null);
                        emptyStackView.setLayoutParams(otherLayoutParams);
                        emptyStackView.setPadding(5, 5, 5, 5);
                        TextView tv_emptyStackView = emptyStackView.findViewById(R.id.tv_elementvalue);
                        tv_emptyStackView.setText("Empty");
                        tv_emptyStackView.setTextColor(Color.WHITE);
                        tv_emptyStackView.setTextSize(16);
                        tv_emptyStackView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                        ll_graphds.addView(emptyStackView);
                    }
                    else {
                        // Top (Push/Pop)
                        View stackTopView = inflater.inflate(R.layout.element_graph_ds, null);
                        stackTopView.setLayoutParams(otherLayoutParams);
                        stackTopView.setPadding(5, 5, 5, 5);
                        TextView tv_stackTopView = stackTopView.findViewById(R.id.tv_elementvalue);
                        tv_stackTopView.setText("TOP");
                        tv_stackTopView.setTextColor(Color.WHITE);
                        tv_stackTopView.setTextSize(12);
                        tv_stackTopView.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                        ll_graphds.addView(stackTopView);

                        // Reverse Stack element to represent top down stack
                        Collections.reverse(arrayList);

                        // Stack elements
                        for (Integer i : arrayList) {
                            View myView = inflater.inflate(R.layout.element_graph_ds, null);
                            myView.setLayoutParams(elementLayoutParams);
                            myView.setPadding(5, 5, 5, 5);
                            TextView tv_queueViewElement = myView.findViewById(R.id.tv_elementvalue);
                            tv_queueViewElement.setText(String.valueOf(i));
                            tv_queueViewElement.setTextColor(Color.WHITE);
                            tv_queueViewElement.setTextSize(16);
                            tv_queueViewElement.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
                            ll_graphds.addView(myView);
                        }
                    }
                }
            }
        });

    }

    void show(){
        if(popupwindow != null && !popupwindow.isShowing()){
            popupwindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    void dismiss(){
        if(popupwindow != null){
            ll_graphds.setVisibility(View.VISIBLE);
            btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MINIMIZE_ICON));
            popupwindow.dismiss();
        }
    }

}
