package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;
import com.otaliastudios.zoom.ZoomLayout;

// GraphTreePopUp maintains popUp for BFS Tree and DFS Tree
public class GraphTreePopUp{

    Context context;
    LayoutInflater inflater;

    PopupWindow popupwindow;
    View parent;
    View popUpView;

    TextView iv_popupgraphname;
    ImageButton btn_minimize;
    ConstraintLayout cl_bottom;
    ZoomLayout zl_graphtree;
    ImageView iv_graphtree;
    CheckBox cb_tree;
    CheckBox cb_back;
    CheckBox cb_forward;
    CheckBox cb_cross;

    boolean isMinimized;
    String title;
    GraphTree graphTree;
    BoardTree boardTree;

    int width;
    int height;
    final int MIN_WIDTH = 200;
    final int MIN_HEIGHT = 200;

    final int MINIMIZE_ICON = R.drawable.ic_minus;
    final int MAXIMIZE_ICON = R.drawable.ic_maximize;

    public GraphTreePopUp(Context _context, int _width, int _height, View _parent){
        this.context = _context;
        this.width = _width;
        this.height = _height;
        this.parent = _parent;
        this.isMinimized = false;

        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.popUpView = inflater.inflate(R.layout.layout_popup_graph, null);

        this.zl_graphtree = popUpView.findViewById(R.id.zl_graphtree);
        this.btn_minimize = popUpView.findViewById(R.id.btn_minimize);
        this.cl_bottom = popUpView.findViewById(R.id.cl_bottom);
        this.cb_tree = popUpView.findViewById(R.id.cb_tree);
        this.cb_back = popUpView.findViewById(R.id.cb_back);
        this.cb_forward = popUpView.findViewById(R.id.cb_forward);
        this.cb_cross = popUpView.findViewById(R.id.cb_cross);
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
                if(cl_bottom.getVisibility() == View.VISIBLE){
                    isMinimized = true;
                    cl_bottom.setVisibility(View.INVISIBLE);
                    int curWidth = width;
                    int curHeight = height - cl_bottom.getHeight();
                    curWidth = curWidth <= 0 ? MIN_WIDTH : curWidth;
                    curHeight = curHeight <= 0 ? MIN_HEIGHT : curHeight;
                    popupwindow.update(curWidth, curHeight);
                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MAXIMIZE_ICON));
                }
                else{
                    isMinimized = false;
                    cl_bottom.setVisibility(View.VISIBLE);
                    popupwindow.update(width, height);
                    btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MINIMIZE_ICON));
                }
            }
        });

        cb_tree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boardTree.showTreeEdge = isChecked;
                boardTree.drawGraph();
            }
        });

        cb_back.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boardTree.showBackEdge = isChecked;
                boardTree.drawGraph();
            }
        });

        cb_forward.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boardTree.showForwardEdge = isChecked;
                boardTree.drawGraph();
            }
        });

        cb_cross.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boardTree.showCrossEdge = isChecked;
                boardTree.drawGraph();
            }
        });

        popupwindow = new PopupWindow(popUpView, width, height, false);
    }

    public void create(String title, GraphTree graphTree){
        this.title = title;
        this.graphTree = graphTree;
        this.iv_popupgraphname.setText(title);
        this.boardTree = new BoardTree(context, graphTree);

        // Make current state of boardTree booleans as per current state of graphTreePopUp
        this.boardTree.showTreeEdge = cb_tree.isChecked();
        this.boardTree.showBackEdge = cb_back.isChecked();
        this.boardTree.showForwardEdge = cb_forward.isChecked();
        this.boardTree.showCrossEdge = cb_cross.isChecked();

        // Restores current state of icons
        if(isMinimized){
            btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MAXIMIZE_ICON));
        }
        else{
            btn_minimize.setImageDrawable(UtilUI.getDrawable(context, MINIMIZE_ICON));
        }

        // After imageView has been laid out, new layout params are passed and again requests layout
        iv_graphtree.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_graphtree.getLayoutParams();

                layoutParams.height = (int) boardTree.Y;
                layoutParams.width  = (int) boardTree.X;

                iv_graphtree.setLayoutParams(layoutParams);
                iv_graphtree.requestLayout();

                // After imageView has been laid out with new layout params, canvas is setup and zoomLayout zooms to 1
                iv_graphtree.post(new Runnable() {
                    @Override
                    public void run() {
                        boardTree.setImageViewAndCreateCanvas(iv_graphtree);
                        zl_graphtree.zoomTo(GraphSettings.defZoom, false);
                    }
                });
            }
        });

    }

    // Shows the popUpWindow if not already showing
    public void show(){
        if(popupwindow != null && !popupwindow.isShowing()){
            popupwindow.showAtLocation(parent, Gravity.NO_GRAVITY, parent.getWidth() - width, 0);
        }
    }

    // Reset icons and resets the state of popUp checkboxes
    public void dismiss(){
        if(popupwindow != null){
            popupwindow.dismiss();
        }
    }

    // Hides the popUpWindow when drawers opens in drawerLayout
    public void hideWhileDrawerOpen(){
        if(popupwindow != null){
            popupwindow.dismiss();
        }
    }

    // Shows the popUpWindow when drawers closes in drawerLayout
    public void showWhileDrawerOpen(){
        show();
    }

}