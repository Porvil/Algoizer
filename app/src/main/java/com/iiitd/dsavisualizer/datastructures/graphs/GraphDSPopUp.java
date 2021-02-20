package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;
import com.otaliastudios.zoom.ZoomLayout;

import java.util.ArrayList;

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
    LinearLayout.LayoutParams layoutParams;

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
        this.layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

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

    void create(String title){
        this.title = title;
        this.iv_popupgraphname.setText(title);

        ll_graphds.post(new Runnable() {
            @Override
            public void run() {

                ll_graphds.removeAllViews();

                Button button = new Button(context);
                button.setText("FRONT(POP)");
                button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                ll_graphds.addView(button);

                Button button2 = new Button(context);
                button2.setText("END(PUSH)");
                button2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                ll_graphds.addView(button2);
            }
        });

    }

    void update(final ArrayList<Integer> queue){

        ll_graphds.post(new Runnable() {
            @Override
            public void run() {

                ll_graphds.removeAllViews();

                Button button = new Button(context);
                button.setText("FRONT(POP)");
                button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                ll_graphds.addView(button);

                for(Integer i : queue) {
                    Button button1 = new Button(context);
                    button1.setText(String.valueOf(i));
                    button1.setLayoutParams(layoutParams);
                    ll_graphds.addView(button1);
                }

                Button button2 = new Button(context);
                button2.setText("END(PUSH)");
                button2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                ll_graphds.addView(button2);
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
