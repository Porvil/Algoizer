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

    View popupView;
    PopupWindow popupWindow;
    ZoomLayout zl_graphtree;
    ImageButton btn_minimize;
    ImageButton btn_close;
    TextView iv_popupgraphname;
    ImageView iv_graphtree;
    GraphTree graphTree;
    BoardTree boardTree;
    String title;
    LayoutInflater inflater;
    View parent;

    int width;
    int height;
    int minWidth = 200;
    int minHeight = 200;

    GraphTreePopUp(final Context context, final int width, final int height, View parent){
        this.context = context;
        this.width = width;
        this.height = height;
        this.parent = parent;

        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void create(String title, GraphTree graphTree){
        dismiss();

        this.title = title;
        this.graphTree = graphTree;

        this.boardTree = new BoardTree(context, graphTree);

        this.popupView = inflater.inflate(R.layout.layout_popup_graph, null);

        this.zl_graphtree = popupView.findViewById(R.id.zl_graphtree);
        this.btn_minimize = popupView.findViewById(R.id.btn_minimize);
        this.btn_close = popupView.findViewById(R.id.btn_close);
        this.iv_popupgraphname = popupView.findViewById(R.id.tv_popupgraphname);
        this.iv_graphtree = zl_graphtree.findViewById(R.id.iv_graphtree);

        this.iv_popupgraphname.setText(title);

        iv_graphtree.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_graphtree.getLayoutParams();
                System.out.println(layoutParams.width + "x" + layoutParams.height);

                layoutParams.height = (int) boardTree.Y;
                layoutParams.width  = (int) boardTree.X;

                System.out.println(layoutParams.width + "x" + layoutParams.height);

                iv_graphtree.setLayoutParams(layoutParams);
                iv_graphtree.requestLayout();
                System.out.println(iv_graphtree.getWidth() + "x" + iv_graphtree.getHeight());

                iv_graphtree.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("||||");
                        System.out.println(iv_graphtree.getWidth() + "x" + iv_graphtree.getHeight());
                        boardTree.setImageViewAndCreateCanvas(iv_graphtree);
                    }
                });
            }
        });

        width = 800;
        height = parent.getHeight();

        popupWindow = new PopupWindow(popupView, width, height, false);

        popupView.setOnTouchListener(new View.OnTouchListener() {
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
                        popupWindow.update(offsetX, offsetY, -1, -1, true);
                        break;
                }
                return true;
            }});

        btn_minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zl_graphtree.getVisibility() == View.VISIBLE){
                    zl_graphtree.setVisibility(View.GONE);
                    int curWidth = width;
                    int curHeight = height - zl_graphtree.getHeight();
                    curWidth = curWidth <= 0 ? minWidth : curWidth;
                    curHeight = curHeight <= 0 ? minHeight : curHeight;
                    popupWindow.update(curWidth, curHeight);

                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_open_in_full_24));

                }
                else{
                    zl_graphtree.setVisibility(View.VISIBLE);
                    popupWindow.update(width, height);
                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_remove_24));
                }
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    void show(){
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
    }

    void dismiss(){
        if(popupWindow !=  null){
            popupWindow.dismiss();
        }
    }

}
