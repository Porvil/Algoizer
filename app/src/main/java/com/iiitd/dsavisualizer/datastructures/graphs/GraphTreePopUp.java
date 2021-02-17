package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;
import com.otaliastudios.zoom.ZoomLayout;

public class GraphTreePopUp{

    Context context;
    LayoutInflater inflater;

    PopupWindow popupwindow;
    View parent;
    View popUpView;

    TextView iv_popupgraphname;
    ImageButton btn_minimize;
    ImageButton btn_close;
    ZoomLayout zl_graphtree;
    ImageView iv_graphtree;

    String title;
    GraphTree graphTree;
    BoardTree boardTree;

    int width;
    int height;
    final int MIN_WIDTH = 200;
    final int MIN_HEIGHT = 200;

    final int MINIMIZE_ICON = R.drawable.ic_baseline_remove_24;
    final int MAXIMIZE_ICON = R.drawable.ic_baseline_open_in_full_24;

    GraphTreePopUp(Context _context, int _width, int _height, View _parent){
        this.context = _context;
        this.width = _width;
        this.height = _height;
        this.parent = _parent;

        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.popUpView = inflater.inflate(R.layout.layout_popup_graph, null);

        this.zl_graphtree = popUpView.findViewById(R.id.zl_graphtree);
        this.btn_minimize = popUpView.findViewById(R.id.btn_minimize);
        this.btn_close = popUpView.findViewById(R.id.btn_close);
        this.iv_popupgraphname = popUpView.findViewById(R.id.tv_popupgraphname);
        this.iv_graphtree = zl_graphtree.findViewById(R.id.iv_graphtree);

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
                if(zl_graphtree.getVisibility() == View.VISIBLE){
                    zl_graphtree.setVisibility(View.GONE);
                    int curWidth = width;
                    int curHeight = height - zl_graphtree.getHeight();
                    curWidth = curWidth <= 0 ? MIN_WIDTH : curWidth;
                    curHeight = curHeight <= 0 ? MIN_HEIGHT : curHeight;
                    popupwindow.update(curWidth, curHeight);

                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MAXIMIZE_ICON));

                }
                else{
                    zl_graphtree.setVisibility(View.VISIBLE);
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

    void create(String title, GraphTree graphTree){
        this.title = title;
        this.graphTree = graphTree;
        this.boardTree = new BoardTree(context, graphTree);
        this.iv_popupgraphname.setText(title);

        iv_graphtree.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_graphtree.getLayoutParams();

                layoutParams.height = (int) boardTree.Y;
                layoutParams.width  = (int) boardTree.X;

                iv_graphtree.setLayoutParams(layoutParams);
                iv_graphtree.requestLayout();

                iv_graphtree.post(new Runnable() {
                    @Override
                    public void run() {
                        boardTree.setImageViewAndCreateCanvas(iv_graphtree);
                    }
                });
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
            zl_graphtree.setVisibility(View.VISIBLE);
            btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MINIMIZE_ICON));
            popupwindow.dismiss();
        }
    }

}
