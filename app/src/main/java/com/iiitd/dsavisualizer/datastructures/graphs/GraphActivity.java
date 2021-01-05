package com.iiitd.dsavisualizer.datastructures.graphs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class GraphActivity extends AppCompatActivity {

    Context context;

    DrawerLayout dl_main;
    View v_main;
    View v_menu;
    ViewStub vs_main;
    ViewStub vs_menu;
    LinearLayout ll_anim;
    ImageView iv_graph;
    ConstraintLayout cl_info;
    ImageButton btn_back;
    ImageButton btn_menu;
    ImageButton btn_info;
    SeekBar sb_animspeed;
    TextView tv_info;

    ImageButton btn_closemenu;
    ImageButton btn_insertrandom;
    Button btn_insert;
    Button btn_search;
    Button btn_delete;
    Button btn_inorder;
    Button btn_preorder;
    Button btn_postorder;
    Button btn_cleartree;
    Button btn_tree1;
    Button btn_tree2;
    Button btn_tree3;
    Button btn_edge;
    EditText et1;
    EditText et2;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    CustomCanvas customCanvas;
    Graph graph;
    Board board;

    TableLayout tableLayout;
    ArrayList<TableRow> tableRows = new ArrayList<>();

    Random random = new Random();
    Timer timer = null;
    int animStepDuration = AppSettings.DEFAULT_ANIM_SPEED;
    int animDuration = AppSettings.DEFAULT_ANIM_DURATION;
    final int LAYOUT = R.layout.activity_graph;
    final int CONTROL = R.layout.controls_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);
        context = this;

        // findViewById
        dl_main = findViewById(R.id.dl_main);
        vs_main = findViewById(R.id.vs_main);
        vs_menu = findViewById(R.id.vs_menu);
        vs_main.setLayoutResource(LAYOUT);
        vs_menu.setLayoutResource(CONTROL);
        v_main = vs_main.inflate();
        v_menu = vs_menu.inflate();

        ll_anim = v_main.findViewById(R.id.ll_anim);
        iv_graph = v_main.findViewById(R.id.iv_graph);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_back = v_main.findViewById(R.id.btn_back);
        tv_info = v_main.findViewById(R.id.tv_info);
        cl_info = v_main.findViewById(R.id.cl_info);

        btn_closemenu = v_menu.findViewById(R.id.btn_closemenu);
        btn_insert = v_menu.findViewById(R.id.btn_insert);
        btn_insertrandom = v_menu.findViewById(R.id.btn_insertrandom);
        btn_search = v_menu.findViewById(R.id.btn_search);
        btn_delete = v_menu.findViewById(R.id.btn_delete);
        btn_inorder = v_menu.findViewById(R.id.btn_inorder);
        btn_preorder = v_menu.findViewById(R.id.btn_preorder);
        btn_postorder = v_menu.findViewById(R.id.btn_postorder);
        btn_cleartree = v_menu.findViewById(R.id.btn_cleartree);
        btn_tree1 = v_menu.findViewById(R.id.btn_tree1);
        btn_tree2 = v_menu.findViewById(R.id.btn_tree2);
        btn_tree3 = v_menu.findViewById(R.id.btn_tree3);
        btn_edge = v_menu.findViewById(R.id.btn_edge);
        et1 = v_menu.findViewById(R.id.et1);
        et2 = v_menu.findViewById(R.id.et2);
        et_insert = v_menu.findViewById(R.id.et_insert);
        et_search = v_menu.findViewById(R.id.et_search);
        et_delete = v_menu.findViewById(R.id.et_delete);

        initViews();

        // Auto Animation Speed
        sb_animspeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 2500ms to 500ms
                animStepDuration = (2000 - seekBar.getProgress() * 20) + 500;
                animDuration = animStepDuration/2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        // Info Button
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = getLayoutInflater().inflate(R.layout.layout_trees_info, null);
//                TextView tv_name = view.findViewById(R.id.tv_name);
//                TextView tv_worst_insert = view.findViewById(R.id.tv_worst_insert);
//                TextView tv_best_insert = view.findViewById(R.id.tv_best_insert);
//                TextView tv_worst_search = view.findViewById(R.id.tv_worst_search);
//                TextView tv_best_search  = view.findViewById(R.id.tv_best_search);
//                TextView tv_worst_delete = view.findViewById(R.id.tv_worst_delete);
//                TextView tv_best_delete = view.findViewById(R.id.tv_best_delete);
//                TextView tv_traversals = view.findViewById(R.id.tv_traversals);
//                TextView tv_space = view.findViewById(R.id.tv_space);
//                ImageButton btn_close = view.findViewById(R.id.btn_close);
//
//                if(bst != null) {
//                    if(timer != null){
//                        timer.cancel();
//                        timer = null;
//                    }
//                }
//
//                tv_name.setText(BSTStats.name);
//                tv_worst_insert.setText(BSTStats.worst_insert);
//                tv_best_insert.setText(BSTStats.best_insert);
//                tv_worst_search.setText(BSTStats.worst_search);
//                tv_best_search.setText(BSTStats.best_search);
//                tv_worst_delete.setText(BSTStats.worst_delete);
//                tv_best_delete.setText(BSTStats.best_delete);
//                tv_traversals.setText(BSTStats.traversals);
//                tv_space.setText(BSTStats.space);
//
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(view);
//                dialog.show();
//
//                btn_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
            }
        });

        // Menu Button
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dl_main.isOpen()) {
                    dl_main.openDrawer(Gravity.RIGHT);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }

        });

        btn_closemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.closeDrawer(Gravity.RIGHT);
            }
        });

//        btn_insertrandom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int data = random.nextInt(100);
//                et_insert.setText(String.valueOf(data));
//                et_insert.setError(null);
//            }
//        });
//
//        btn_insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = et_insert.getText().toString();
//                int data;
//                if(!s.isEmpty()){
//                    data = Integer.parseInt(s.trim());
//                }
//                else{
//                    et_insert.setError("Cant be empty");
//                    return;
//                }
//
//                startTimer("INSERT", data);
//            }
//        });
//
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = et_search.getText().toString();
//                int data;
//                if(!s.isEmpty()){
//                    data = Integer.parseInt(s.trim());
//                }
//                else{
//                    et_search.setError("Cant be empty");
//                    return;
//                }
//
//                startTimer("SEARCH", data);
//            }
//        });
//
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = et_delete.getText().toString();
//                int data;
//                if(!s.isEmpty()){
//                    data = Integer.parseInt(s.trim());
//                }
//                else{
//                    et_delete.setError("Cant be empty");
//                    return;
//                }
//
//                startTimer("DELETE", data);
//            }
//        });

//        btn_inorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTimer("INORDER", -1);
//            }
//        });
//
//        btn_preorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTimer("PREORDER", -1);
//            }
//        });
//
//        btn_postorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTimer("POSTORDER", -1);
//            }
//        });

//        btn_cleartree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearTree();
//            }
//        });
//
//        btn_tree1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createExampleTree(BSTInfo.tree1);
//            }
//        });
//
//        btn_tree2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createExampleTree(BSTInfo.tree2);
//            }
//        });
//
//        btn_tree3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createExampleTree(BSTInfo.tree3);
//            }
//        });


        btn_edge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = et1.getText().toString();
                String s2 = et2.getText().toString();

                String[] split = s1.split("-");

                int i1 = Integer.parseInt(split[0]);
                int i2 = Integer.parseInt(split[1]);

                int edge = graph.createEdge(i1, i2, 1);
                board.update(graph);

            }
        });

    }

    private void initViews() {
//        if(bst != null){
//            tv_seqno.setText("0 / " + bst.sequence.animationStates.size());
//            UtilUI.setText(tv_info, bst.sequence.animationStates.get(0).info);
//            UtilUI.highlightViews(context, bst.sequence.views,
//                    bst.sequence.animationStates.get(0).highlightIndexes);
//            String state = bst.sequence.animationStates.get(0).state;
//            if(BSTInfo.map.containsKey(state)){
//                Integer[] integers = BSTInfo.map.get(state);
//                UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
//            }
//        }
//        else{
//            tv_seqno.setText("0 / 0");
//            UtilUI.setText(tv_info, "-");
//            UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, null);
//        }

        iv_graph.post(new Runnable() {
            @Override
            public void run() {
                customCanvas = new CustomCanvas(context, iv_graph);
                graph = new Graph();
                board = new Board(context, customCanvas);

                Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
                mPaintText.setTextSize(30);
                Paint paint = new Paint(Color.BLACK);
                Rect rect = new Rect();
//
//                for(int i = 0; i<board.xCount +1; i++){
//                    int left = (int) (i*board.xSize);
//                    int right = left + 1;
//                    int top = 0;
//                    int bottom = (int) board.Y;
//                    rect.set(left, top, right, bottom);
//                    board.customCanvas.canvas.drawRect(rect, paint);
//                }
//
//
//                for(int i = 0; i<board.yCount +1; i++){
//                    int top = (int) (i*board.ySize);
//                    int bottom = top + 1;
//                    int left = 0;
//                    int right = (int) board.X;
//                    rect.set(left, top, right, bottom);
//                    board.customCanvas.canvas.drawRect(rect, paint);
//                }
//                for(int r = 0; r<board.yCount +1; r++){
//                    for(int c = 0; c<board.xCount +1; c++){
//                        String text = r + " " + c;
//                        int yy = (int) (r*board.xSize + board.xSize /2);
//                        int xx = (int) (c*board.ySize + board.ySize /2);
//                        board.customCanvas.canvas.drawText(text, xx, yy, mPaintText);
//                    }
//                }


//                iv_graph.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if(event.getAction() == MotionEvent.ACTION_UP) {
//                            float x1 = event.getX();
//                            float y1 = event.getY();
//
//                            float x = (x1 / board.colWidth);
//                            float y =  (y1 / board.rowHeight);
//
//                            Rect box = board.getBox(x, y);
//
//                            System.out.println("BOX = " + box.centerX() + " | " + box.centerY());
//
//                            iv_graph.invalidate();
//                        }
//                        return false;
//                    }
//                });


            }
        });

        final GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDown(MotionEvent event) {
                System.out.println("On down");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                System.out.println("long");

                float x1 = event.getX();
                float y1 = event.getY();

                float x = (x1 / board.xSize);
                float y =  (y1 / board.ySize);


            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                System.out.println("Single touch up");

                float x1 = event.getX();
                float y1 = event.getY();

                float x = (x1 / board.xSize);
                float y =  (y1 / board.ySize);

                int row = (int) y;
                int col = (int) x;
//                System.out.println("touch = " + x + "|" + y);

                boolean state = board.getState(x, y);
                if(state){
//                    System.out.p/rintln("ON");
                }
                else{
                    System.out.println("OFF");
                    Vertex vertex = graph.createVertex((int) graph.noOfVertices, row, col);
                    board.addVertex(x, y, vertex);
//                        board.switchState(x, y);
                }


                board.update(graph);
                iv_graph.invalidate();

                return  true;
            }
        });

        iv_graph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                if(event.getAction() == MotionEvent.ACTION_DOWN) {
//
//
//                }
//                return true;

                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }

                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (dl_main.isDrawerOpen(Gravity.RIGHT)){
            dl_main.closeDrawer(Gravity.RIGHT);
        }
        else {
            back();
        }
    }

    private void back(){
        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        View view = getLayoutInflater().inflate(R.layout.layout_back_confirmation, null);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_yes = view.findViewById(R.id.btn_yes);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_menu.setEnabled(true);
                dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                System.out.println("Dismissed");
                btn_menu.setEnabled(true);
                btn_back.setEnabled(true);
                btn_info.setEnabled(true);
                dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
    }

}