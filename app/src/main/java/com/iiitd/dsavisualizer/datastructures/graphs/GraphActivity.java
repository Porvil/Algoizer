package com.iiitd.dsavisualizer.datastructures.graphs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.BFS;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.DFS;
import com.iiitd.dsavisualizer.runapp.others.BiDirectionScrollView;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GraphActivity extends AppCompatActivity {

    Context context;

    DrawerLayout dl_main;
    View v_main;
    View v_menu;
    ViewStub vs_main;
    ViewStub vs_menu;
    LinearLayout ll_anim;
    ImageView iv_grid;
    ImageView iv_coordinates;
    ImageView iv_graph;
    ImageView iv_anim;
    ConstraintLayout cl_info;
    RadioGroup rg_graphcontrols;
    RadioButton rb_graphcontrol_view;
    RadioButton rb_graphcontrol_vertex;
    RadioButton rb_graphcontrol_edge;
    ImageButton btn_play;
    ImageButton btn_back;
    ImageButton btn_menu;
    ImageButton btn_code;
    ImageButton btn_info;
    ImageButton btn_backward;
    ImageButton btn_forward;
    SeekBar sb_animspeed;
    TextView tv_seqno;
    TextView tv_info;
    ImageButton btn_grid;

    ImageButton btn_closemenu;
    RadioGroup rg_weighted;
    RadioGroup rg_directed;
    Button btn_bfs;
    Button btn_dfs;
    ImageButton btn_clearcustominput;
    ImageButton btn_copygraph;
    ImageButton btn_pastecustominput;
    ImageButton btn_custominput;
    ImageButton btn_savecustominput;
    Button btn_cleargraph;
    Button btn_cleargraphanim;
    Button btn_tree1;
    Button btn_tree2;
    Button btn_tree3;
    EditText et_customgraphinput;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    GraphWrapper graphWrapper;
    CustomCanvas customCanvas;
    GraphControls graphControls;
    GraphSequence graphSequence;
    GraphTree graphTree;

    Timer timer = null;
    int animStepDuration = AppSettings.DEFAULT_ANIM_SPEED;
    int animDuration = AppSettings.DEFAULT_ANIM_DURATION;
    final int LAYOUT = R.layout.activity_graph;
    final int CONTROL = R.layout.controls_graph;
    boolean isAutoPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        iv_grid = v_main.findViewById(R.id.iv_grid);
        iv_coordinates = v_main.findViewById(R.id.iv_coordinates);
        iv_graph = v_main.findViewById(R.id.iv_graph);
        iv_anim = v_main.findViewById(R.id.iv_anim);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        rg_graphcontrols = v_main.findViewById(R.id.rg_graphcontrols);
        rb_graphcontrol_view = v_main.findViewById(R.id.rb_graphcontrol_view);
        rb_graphcontrol_vertex = v_main.findViewById(R.id.rb_graphcontrol_vertex);
        rb_graphcontrol_edge = v_main.findViewById(R.id.rb_graphcontrol_edge);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        btn_play = v_main.findViewById(R.id.btn_play);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_code = v_main.findViewById(R.id.btn_code);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_back = v_main.findViewById(R.id.btn_back);
        btn_backward = v_main.findViewById(R.id.btn_backward);
        btn_forward = v_main.findViewById(R.id.btn_forward);
        tv_seqno = v_main.findViewById(R.id.tv_seqno);
        tv_info = v_main.findViewById(R.id.tv_info);
        btn_grid = v_main.findViewById(R.id.btn_grid);
        cl_info = v_main.findViewById(R.id.cl_info);

        btn_closemenu = v_menu.findViewById(R.id.btn_closemenu);
        rg_weighted = v_menu.findViewById(R.id.rg_weighted);
        rg_directed = v_menu.findViewById(R.id.rg_directed);
        btn_bfs = v_menu.findViewById(R.id.btn_bfs);
        btn_dfs = v_menu.findViewById(R.id.btn_dfs);
        btn_clearcustominput = v_menu.findViewById(R.id.btn_clearcustominput);
        btn_copygraph = v_menu.findViewById(R.id.btn_copygraph);
        btn_pastecustominput = v_menu.findViewById(R.id.btn_pastecustominput);
        btn_custominput = v_menu.findViewById(R.id.btn_custominput);
        btn_savecustominput = v_menu.findViewById(R.id.btn_savecustominput );
        btn_custominput = v_menu.findViewById(R.id.btn_custominput);
        btn_cleargraph = v_menu.findViewById(R.id.btn_cleargraph);
        btn_cleargraphanim = v_menu.findViewById(R.id.btn_cleargraphanim);
        btn_tree1 = v_menu.findViewById(R.id.btn_tree1);
        btn_tree2 = v_menu.findViewById(R.id.btn_tree2);
        btn_tree3 = v_menu.findViewById(R.id.btn_tree3);
        et_customgraphinput = v_menu.findViewById(R.id.et_customgraphinput);
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

                // TEMP CODE
//                int scale = (seekBar.getProgress() / 10) - 5;
//                System.out.println(seekBar.getProgress() + " " + scale);
//                customCanvas.canvasGraph.scale(scale, scale);
//                iv_graph.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        btn_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphControls.changeGraphViewState();
                updateGraphViewState();
            }
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

        btn_bfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetGraphSequence();
                graphWrapper.board.clearCanvasAnim();

                BFS bfs = new BFS(graphWrapper.graph);
                GraphSequence run = bfs.run(0);
                graphSequence = run;

                GraphTree rungt = bfs.graphTree;

                graphTree = rungt;
                graphTree.noOfCols = 3;
                graphTree.noOfRows = 7;

                graphTree.printEdges();
                graphTree.printVertices();

                System.out.println(graphSequence);
                UtilUI.setText(tv_seqno, "0/" + graphSequence.size);
//                startTimer("BFS", bfs);
            }
        });

        btn_dfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGraphSequence();
                graphWrapper.board.clearCanvasAnim();



                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.layout_popup_graph, null);

                final ImageButton btn_minimize = popupView.findViewById(R.id.btn_minimize);
                ImageButton btn_close = popupView.findViewById(R.id.btn_close);
                TextView iv_popupgraphname = popupView.findViewById(R.id.tv_popupgraphname);

                final BiDirectionScrollView bdsv_popupgraph = popupView.findViewById(R.id.bdsv_popupgraph);



                DFS dfs = new DFS(graphWrapper.graph);
                GraphSequence run = dfs.dfs();
                graphSequence = run;

                GraphTree rungt = dfs.graphTree;

                graphTree = rungt;
                graphTree.noOfCols = 3;
                graphTree.noOfRows = 7;


                final BoardTree boardTree = new BoardTree(context, graphTree, bdsv_popupgraph);
                graphTree.printEdges();
                graphTree.printVertices();


                bdsv_popupgraph.post(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("########333 bdsv pop up graph tree = " + bdsv_popupgraph.getWidth()
                                + " | " + bdsv_popupgraph.getHeight());
                        boardTree.startInit();

                        System.out.println("########6666 bdsv pop up graph tree = " + bdsv_popupgraph.getWidth()
                                + " | " + bdsv_popupgraph.getHeight());
                    }
                });




//
//                iv_popupgraph.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int width = iv_popupgraph.getWidth();
//                        int height = iv_popupgraph.getHeight();
//
//                        System.out.println("Canvas Pop Up = " + width + "x" + height);
//
//                        Canvas canvas;
//                        Bitmap bitmap;
//
//                        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//                        iv_popupgraph.setImageBitmap(bitmap);
//                        canvas = new Canvas(bitmap);
//
//                        canvas.drawCircle(50,50,20, new Paint(Color.RED));
//
//                    }
//                });


                // create the popup window
//                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                final int width = 528;
                final int width = 800;
                final int height = ll_anim.getHeight();
                boolean focusable = false; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                final PopupWindow popupWindow = new PopupWindow(popupView, 600, 800, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
//                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.showAtLocation(ll_anim, Gravity.NO_GRAVITY, 0, 0);
//popupWindow.showAsDropDown(view);
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                popupView.setOnTouchListener(new View.OnTouchListener() {
                    int orgX, orgY;
                    int offsetX, offsetY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                orgX = (int) event.getX();
                                orgY = (int) event.getY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                offsetX = (int)event.getRawX() - orgX;
                                offsetY = (int)event.getRawY() - orgY;
                                System.out.println(offsetX + "x" + offsetY);
                                popupWindow.update(offsetX, offsetY, -1, -1, true);
                                break;
                        }
                        return true;
                    }});

                btn_minimize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bdsv_popupgraph.getVisibility() == View.VISIBLE){
                            bdsv_popupgraph.setVisibility(View.GONE);
                            int curWidth = width;
                            int curHeight = height - bdsv_popupgraph.getHeight();
                            curHeight = curHeight <= 0 ? 200 : curHeight;
                            popupWindow.update(curWidth, curHeight);

                            btn_minimize.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_open_in_full_24));

                        }
                        else{
                            bdsv_popupgraph.setVisibility(View.VISIBLE);
                            popupWindow.update(width, height);
                            btn_minimize.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_remove_24));
                        }
                    }
                });

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


//                System.out.println(graphSequence);
//                UtilUI.setText(tv_seqno, "0/" + graphSequence.size);
            }
        });

        btn_copygraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphWrapper != null) {
                    String graphString = graphWrapper.getSerializableGraphString();
                    if (graphString != null) {
                        System.out.println(graphString);

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copied Graph to Clipboard", graphString);
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(context, "Copied Graph to Clipboard", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_custominput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = et_customgraphinput.getText().toString();
                parseAndShowCustomInput(customGraphString);
            }
        });

        btn_cleargraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                graphWrapper.graph.clearGraph();
//                graphWrapper.board.reset(graphWrapper.graph);
                graphWrapper.reset();
                graphWrapper.board.clearCanvasAnim();
                graphSequence = null;
                resetGraphSequence();
//                graphWrapper.board.clearCanvasGraph();

            }
        });

        btn_clearcustominput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_customgraphinput.setText("");
            }
        });

        btn_cleargraphanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphWrapper.board.clearCanvasAnim();
                resetGraphSequence();
            }
        });

        btn_tree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString =
                        "D 1\n" +
                                "W 0\n" +
                                "VC 5\n" +
                                "VA 0 0 0\n" +
                                "VA 1 0 3\n" +
                                "VA 2 2 0\n" +
                                "VA 3 2 4\n" +
                                "VA 4 4 3\n" +
                                "E 0 1 1\n" +
                                "E 0 2 1\n" +
                                "E 1 2 1\n" +
                                "E 2 3 1\n" +
                                "E 3 4 1\n" +
                                "E 3 0 1\n";

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);
            }
        });

        btn_tree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getLayoutInflater().inflate(R.layout.layout_test, null);
//                View view = getLayoutInflater().inflate(R.layout.layout_test2, null);
//                View view = getLayoutInflater().inflate(R.layout.layout_test3, null);


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(view);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();



                String customGraphString =
                        "D 1\n" +
                        "W 0\n" +
                        "VC 5\n" +
                        "VA 0 0 0\n" +
                        "VA 1 0 3\n" +
                        "VA 2 2 0\n" +
                        "VA 3 2 4\n" +
                        "VA 4 4 3\n" +
                        "E 0 1 1\n" +
                        "E 0 2 1\n" +
                        "E 1 2 1\n" +
                        "E 2 3 1\n" +
                        "E 3 4 1\n";

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

//                graphWrapper.graph.addVertex(0,0,0);graphWrapper.board.addVertex(0,0,0);
//                graphWrapper.graph.addVertex(1,0,3);graphWrapper.board.addVertex(0,3,1);
//                graphWrapper.graph.addVertex(2,2,0);graphWrapper.board.addVertex(2,0,2);
//                graphWrapper.graph.addVertex(3,2,4);graphWrapper.board.addVertex(2,4,3);
//                graphWrapper.graph.addVertex(4,4,3);graphWrapper.board.addVertex(4,3,4);
//
//
//                graphWrapper.graph.addEdge(0, 1, 1);
//                graphWrapper.graph.addEdge(0, 2, 1);
//                graphWrapper.graph.addEdge(1, 2, 1);
//                graphWrapper.graph.addEdge(2, 3, 1);
//                graphWrapper.graph.addEdge(3, 4, 1);


//                graphWrapper.board.update(graphWrapper.graph)
            }
        });

        btn_tree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String customGraphString = "D 1\n" +
                        "W 0\n" +
                        "VC 7\n" +
                        "VA 0 3 7\n" +
                        "VA 1 3 5\n" +
                        "VA 2 5 5\n" +
                        "VA 3 1 7\n" +
                        "VA 4 1 10\n" +
                        "VA 5 3 10\n" +
                        "VA 6 5 10\n" +
                        "E 0 1 1\n" +
                        "E 0 5 1\n" +
                        "E 0 2 1\n" +
                        "E 1 2 1\n" +
                        "E 3 0 1\n" +
                        "E 3 5 1\n" +
                        "E 3 4 1\n" +
                        "E 4 5 1\n" +
                        "E 5 6 1\n" +
                        "E 6 2 1\n" +
                        "E 6 0 1\n";

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);


//                LayoutInflater inflater = (LayoutInflater)
//                        getSystemService(LAYOUT_INFLATER_SERVICE);
//                final View popupView = inflater.inflate(R.layout.layout_popup_graph, null);
//
//                ImageButton btn_minimize = popupView.findViewById(R.id.btn_minimize);
//                ImageButton btn_close = popupView.findViewById(R.id.btn_close);
//                TextView iv_popupgraphname = popupView.findViewById(R.id.tv_popupgraphname);
////                final ImageView iv_popupgraph = popupView.findViewById(R.id.iv_popupgraph);
//
//
//
//
////                iv_popupgraph.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        int width = iv_popupgraph.getWidth();
////                        int height = iv_popupgraph.getHeight();
////
////                        System.out.println("Canvas Pop Up = " + width + "x" + height);
////
////                        Canvas canvas;
////                        Bitmap bitmap;
////
////                        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
////                        iv_popupgraph.setImageBitmap(bitmap);
////                        canvas = new Canvas(bitmap);
////
////                        canvas.drawCircle(50,50,20, new Paint(Color.RED));
////
////                    }
////                });
//
//
//                // create the popup window
////                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
////                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                final int width = 800;
//                final int height = ll_anim.getHeight();
//                boolean focusable = false; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
////                final PopupWindow popupWindow = new PopupWindow(popupView, 600, 800, focusable);
//
//                // show the popup window
//                // which view you pass in doesn't matter, it is only used for the window tolken
////                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//                popupWindow.showAtLocation(ll_anim, Gravity.NO_GRAVITY, 0, 0);
////popupWindow.showAsDropDown(view);
//                // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                });
//
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    int orgX, orgY;
//                    int offsetX, offsetY;
//
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        switch (event.getAction()) {
//                            case MotionEvent.ACTION_DOWN:
//                                orgX = (int) event.getX();
//                                orgY = (int) event.getY();
//                                break;
//                            case MotionEvent.ACTION_MOVE:
//                                offsetX = (int)event.getRawX() - orgX;
//                                offsetY = (int)event.getRawY() - orgY;
//                                System.out.println(offsetX + "x" + offsetY);
//                                popupWindow.update(offsetX, offsetY, -1, -1, true);
//                                break;
//                        }
//                        return true;
//                    }});
//
//                btn_minimize.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        if(iv_popupgraph.getVisibility() == View.VISIBLE){
////                            iv_popupgraph.setVisibility(View.GONE);
////                            int curWidth = width;
////                            int curHeight = height - iv_popupgraph.getHeight();
////                            curHeight = curHeight <= 0 ? 200 : curHeight;
////                            popupWindow.update(curWidth, curHeight);
////                        }
////                        else{
////                            iv_popupgraph.setVisibility(View.VISIBLE);
////                            popupWindow.update(width, height);
////                        }
//                    }
//                });
//
//                btn_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });

            }
        });

        btn_savecustominput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportCurrentGraph();
            }
        });

        btn_pastecustominput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String pasteData = "";

                if (!(clipboard.hasPrimaryClip())) {}
                else if (!(clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {}
                else {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                    pasteData = item.getText().toString();
                    et_customgraphinput.setText(pasteData);
                }
            }
        });

        rg_directed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                boolean directed = false;

                if (checkedId == R.id.rb_directed) {
                    directed = true;
                } else if (checkedId == R.id.rb_undirected) {
                    directed = false;
                }

                // Clear everything
                Toast.makeText(context, "Graphs will be cleared", Toast.LENGTH_SHORT).show();
//                graphWrapper = new GraphWrapper(context, customCanvas, directed, false);

                graphWrapper.changeDirected(directed);
                graphSequence = null;
                resetGraphSequence();

            }
        });

        rg_weighted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                boolean weighted = false;

                if (checkedId == R.id.rb_weighted) {
                    weighted = true;
                } else if (checkedId == R.id.rb_unweighted) {
                    weighted = false;
                }

                // Clear everything
                Toast.makeText(context, "Graphs will be cleared", Toast.LENGTH_SHORT).show();
                graphWrapper.changeWeighted(weighted);
                graphSequence = null;
                resetGraphSequence();

            }
        });

        // Auto Animation Play/Pause Button
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(graphSequence != null){
                    if(isAutoPlay){
                        isAutoPlay = false;
                        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        if(timer != null) {
                            timer.cancel();
                        }
                    }
                    else{
                        isAutoPlay = true;
                        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PAUSE_BUTTON));
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (graphSequence.curSeqNo < graphSequence.getSize()-1){
                                    graphSequence.forward();
                                    taskStep(graphSequence.curSeqNo);
                                }
                                else {
                                    isAutoPlay = false;
                                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                                    if(timer != null) {
                                        timer.cancel();
                                    }
                                }
                            }
                        }, 0, animStepDuration);
                    }

                }
            }
        });

        // One Animation Step-Back
        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isAutoPlay = false;
//                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
//                timer.cancel();
//                onBackwardClick();
                if(graphSequence != null){
                    graphSequence.backward();
                    taskStep(graphSequence.curSeqNo);
                }

            }
        });

        // One Animation Step-Forward
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isAutoPlay = false;
//                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
//                timer.cancel();
//                onForwardClick();
                if(graphSequence != null){
                    graphSequence.forward();
                    taskStep(graphSequence.curSeqNo);
                }
            }
        });

    }

    private void startTimer(String operation, final BFS bfs){
        if(!dl_main.isOpen()) {
            System.out.println("OPEN");
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            btn_menu.setEnabled(false);
            btn_back.setEnabled(false);
            btn_info.setEnabled(false);
        }

        if(timer == null) {

            final int animDurationTemp = this.animDuration;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    task(animDurationTemp);
                }
            }, animStepDuration, animStepDuration);

        }
    }

    private void task(final int animDurationTemp) {
        if (graphSequence != null) {
            final int curSeqNo = graphSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);
            graphSequence.forward();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    graphWrapper.board.clearCanvasAnim();
                    if(curSeqNo < graphSequence.graphAnimationStates.size()) {
                        GraphAnimationState graphAnimationState = graphSequence.graphAnimationStates.get(curSeqNo);
                        System.out.println(graphAnimationState);

                        for(GraphAnimationStateShadow graphAnimationStateShadow : graphAnimationState.graphAnimationStateShadow){
                            for(Vertex vertex : graphAnimationStateShadow.vertices){
                                Rect rect = graphWrapper.board.getRect(vertex.data);
                                graphWrapper.board.drawNodeAnim(rect, vertex.data);
                            }

                            for(Edge edge : graphAnimationStateShadow.edges){
                                Rect rect1 = graphWrapper.board.getRect(edge.src);
                                Rect rect2 = graphWrapper.board.getRect(edge.des);
                                graphWrapper.board.drawEdgeAnim(rect1, rect2, edge, graphWrapper.directed);
                            }
                        }

                        graphWrapper.board.refreshAnim();

                    }
                    else{
                        UtilUI.setText(tv_info, "Done");
                        System.out.println("Canceled");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                btn_back.setEnabled(true);
                                btn_info.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            });

        }

    }

    private void taskStep(final int curSeqNo) {
        if (graphSequence != null) {
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(curSeqNo < graphSequence.graphAnimationStates.size() && curSeqNo >= 0) {
                        UtilUI.setText(tv_seqno, curSeqNo + "/" + graphSequence.size);

                        graphWrapper.board.clearCanvasAnim();
                        GraphAnimationState graphAnimationState = graphSequence.graphAnimationStates.get(curSeqNo);
//                        System.out.println(graphAnimationState);


                        for(GraphAnimationStateShadow graphAnimationStateShadow : graphAnimationState.graphAnimationStateShadow){
                            System.out.println(graphAnimationStateShadow.queues);
                            for(Vertex vertex : graphAnimationStateShadow.vertices){
                                Rect rect = graphWrapper.board.getRect(vertex.data);
                                graphWrapper.board.drawNodeAnim(rect, vertex.data);
                            }

                            for(Edge edge : graphAnimationStateShadow.edges){
                                Rect rect1 = graphWrapper.board.getRect(edge.src);
                                Rect rect2 = graphWrapper.board.getRect(edge.des);
                                graphWrapper.board.drawEdgeAnim(rect1, rect2, edge, graphWrapper.weighted);
                            }
                        }

                        graphWrapper.board.refreshAnim();

                    }
                    else{
                        UtilUI.setText(tv_info, "Done");
                        System.out.println("Canceled");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                btn_back.setEnabled(true);
                                btn_info.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                        timer.cancel();
//                        timer = null;
                    }
                }
            });

        }

    }


    private void initViews() {

        final boolean startDirected = false;
        final boolean startWeighted = false;

        graphControls = new GraphControls(context, rb_graphcontrol_view, rb_graphcontrol_vertex, rb_graphcontrol_edge);
        graphControls.updateDrawables();

        // Draws Grid and Graph View After Layouts have been laid out
        iv_graph.post(new Runnable() {
            @Override
            public void run() {
                iv_grid.post(new Runnable() {
                    @Override
                    public void run() {
                        iv_anim.post(new Runnable() {
                            @Override
                            public void run() {
                                customCanvas = new CustomCanvas(context, iv_graph, iv_grid, iv_anim, iv_coordinates);
                                graphWrapper = new GraphWrapper(context, customCanvas, startDirected, startWeighted);
                                updateGraphViewState();
                            }
                        });
                    }
                });
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

                float x = (x1 / graphWrapper.board.xSize);
                float y =  (y1 / graphWrapper.board.ySize);

            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                System.out.println("Single touch up");

                float x1 = event.getX();
                float y1 = event.getY();

                float x = (x1 / graphWrapper.board.xSize);
                float y =  (y1 / graphWrapper.board.ySize);

                int row = (int) y;
                int col = (int) x;
//                System.out.println("touch = " + x + "|" + y);
                System.out.println("touch = " + row + "|" + col);
                System.out.println(graphWrapper.board.getState(row, col));

                switch(graphControls.getCurrentState()){
                    case VIEW:
                        break;
                    case VERTEX_ADD:
                        boolean addVertex = graphWrapper.addVertex(event);
                        if(!addVertex){
                            Toast.makeText(context, "Vertex already present or too close to another vertex", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case VERTEX_REMOVE:
                        graphWrapper.removeVertex(event);
                        break;
                    case EDGE_ADD:
                        if(graphWrapper.board.getState(row, col)){
                            if(graphControls.startEdge != -1){//some node already selected
                                int des = graphWrapper.board.boardElements[row][col].value;
                                int src = graphControls.startEdge;
                                if(src != des) {
                                    graphWrapper.addEdge(src, des);
                                }
                                graphControls.startEdge = -1;
                                Toast.makeText(context, src + " -> " + des, Toast.LENGTH_SHORT).show();
                            }
                            else{// first node getting selected now
                                int data = graphWrapper.board.boardElements[row][col].value;
                                graphControls.startEdge = data;
                                Toast.makeText(context, data + " selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case EDGE_REMOVE:
                        if(graphWrapper.board.getState(row, col)){
                            if(graphControls.startEdge != -1){//some node already selected
                                int des = graphWrapper.board.boardElements[row][col].value;
                                int src = graphControls.startEdge;
                                graphWrapper.removeEdge(src, des);
                                graphControls.startEdge = -1;
                            }
                            else{// first node getting selected now
                                int data = graphWrapper.board.boardElements[row][col].value;
                                graphControls.startEdge = data;
                            }
                        }
                        break;

                }

//                graphWrapper.update();
                System.out.println(graphWrapper.board.getState(row, col));

                //Careful about this below line, MUST BE CALLED
                graphWrapper.board.refreshGraph();

                return  true;
            }
        });

        iv_graph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    public void showSaveGraphDialog(final String graphString){
        View view = getLayoutInflater().inflate(R.layout.layout_save_confirmation, null);

        final EditText et_graphsavename = view.findViewById(R.id.et_graphsavename);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_yes = view.findViewById(R.id.btn_yes);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        final String fileName = "graph-" + currentTimeStamp + ".txt";

        et_graphsavename.setText(fileName);

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
                String fileName = et_graphsavename.getText().toString();
                if(fileName.equals("") || fileName.length() == 0){
                    et_graphsavename.setError("Cant be empty");
                }
                else {
                    boolean success = Util.writeGraphToStorage(graphString, fileName);
                    if(success){
                        Toast.makeText(context, "Graph saved", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

            }
        });

    }

    public void onGraphControlsClick(View view) {
        graphControls.updateState(view);
        graphControls.updateDrawables();

        System.out.println(graphControls);

    }

    public void updateGraphViewState(){
        switch (graphControls.graphViewState){
            case GRAPH_ONLY:
                btn_grid.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_grid_off_24));
                iv_grid.setVisibility(View.GONE);
                iv_coordinates.setVisibility(View.GONE);
                break;
            case GRAPH_GRID:
                btn_grid.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_grid_on_24));
                iv_grid.setVisibility(View.VISIBLE);
                iv_coordinates.setVisibility(View.GONE);
                break;
            case GRAPH_GRID_COORDINATES:
                btn_grid.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_grid_on2_24));
                iv_grid.setVisibility(View.VISIBLE);
                iv_coordinates.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void parseAndShowCustomInput(String customGraphString){
        String[] ss = customGraphString.split("\\n");

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        boolean directed = false;
        boolean weighted = false;
        int noOfVertices = 0;

        boolean gotDirection = false;
        boolean gotWeight = false;
        boolean gotVertexCount = false;
        boolean error = false;
        String response = "";

        try {
            for (String line : ss) {
                String[] chars = line.split("\\s+");

                switch (chars[0]) {
                    case "D":
                        if (!gotDirection) {
                            directed = chars[1].equals("1");
                            gotDirection = true;
                        }
                        else{
                            error = true;
                            response = "Multiple times \"direction\" variable provided";
                        }
                        break;
                    case "W": {
                        if (!gotWeight) {
                            weighted = chars[1].equals("1");
                            gotWeight = true;
                        }
                        else {
                            error = true;
                            response = "Multiple times \"weight\" variable provided";
                        }
                        break;
                    }
                    case "VC": {
                        // USELESS, vertexCount is taken in the end using "vertices.size()"
                        if (!gotVertexCount) {
                            noOfVertices = Integer.parseInt(chars[1]);
                            gotVertexCount = true;
                        }
                        else {
                            error = true;
                            response = "Multiple times \"vertex count\" variable provided";
                        }
                        break;
                    }
                    case "VA": {
                        int data = Integer.parseInt(chars[1]);
                        int row = Integer.parseInt(chars[2]);
                        int col = Integer.parseInt(chars[3]);

                        if(row < 0)
                            row = -1;
                        if(col < 0)
                            col = -1;

                        if(data < 0 || data > 999){
                            error = true;
                            response = "Node Value should be [0, 999]";
                            break;
                        }

                        Vertex vertex = new Vertex(data, row, col);

                        if(!vertices.contains(vertex)){
                            vertices.add(vertex);
                        }
                        else{
                            int index = vertices.indexOf(vertex);
                            vertices.get(index).row = row;
                            vertices.get(index).col = col;
                        }
                        break;
                    }
                    case "V": {
                        for (int c = 1; c < chars.length; c++) {
                            int data = Integer.parseInt(chars[c]);

                            if(data < 0 || data > 999){
                                error = true;
                                response = "Node Value should be [0, 999]";
                                break;
                            }

                            Vertex vertex = new Vertex(data, -1, -1);
                            if(!vertices.contains(vertex)){
                                vertices.add(vertex);
                            }
                        }
                        break;
                    }
                    case "E": {
                        int src = Integer.parseInt(chars[1]);
                        int des = Integer.parseInt(chars[2]);
                        int weight = weighted ? Integer.parseInt(chars[3]) : 1;

                        edges.add(new Edge(src, des, weight));
                        break;
                    }
                    default:
                        error = true;
                        response = "Bad Input";
                        break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception while parsing graph data");
            error = true;
            response = "Bad Input, Exception while parsing graph data";
        }

        if(error){
            System.out.println("BAD INPUT or error");
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        }
        else{
            if(noOfVertices == 0){
                System.out.println("No vertices in custom input");
                response = "No vertices found in custom input";
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }

            System.out.println("Directed = " + directed);
            System.out.println("Weighted = " + weighted);
            System.out.println("No of Vertices = " + noOfVertices);

            noOfVertices = vertices.size();

            System.out.println("No of Vertices(updated) = " + noOfVertices);

            //Sort vertices here such that, random nodes are in the end of arraylist [ IMPORTANT ]
            Collections.sort(vertices);


            if(noOfVertices > graphWrapper.board.maxVertices){
                System.out.println("Not enough space in graph");
                response = "Not enough space in graph";
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
            else if(graphWrapper.checkPerformanceBounds(vertices)){
                System.out.println("Custom input has large coordinates for vertices");
                response = "Custom input has large coordinates for vertices";
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
            else{
                if (!graphWrapper.checkBounds(vertices)) {
                    if(graphWrapper.checkIfMinimizable(vertices)){
                        graphWrapper.minimizeGraph(vertices);
                    }
                }

                if(weighted){
                    rg_weighted.check(R.id.rb_weighted);
                }
                else{
                    rg_weighted.check(R.id.rb_unweighted);
                }

                if(directed){
                    rg_directed.check(R.id.rb_directed);
                }
                else{
                    rg_directed.check(R.id.rb_undirected);
                }

                graphSequence = null;
                resetGraphSequence();
                graphWrapper.changeDirectedWeighted(directed, weighted);
                graphWrapper.customInput(vertices, edges);

                response = "Custom Graph input successful";
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void exportCurrentGraph(){

        if (graphWrapper != null) {
            String graphString = graphWrapper.getSerializableGraphString();
            if (graphString != null) {
                System.out.println(graphString);

                boolean hasPermissions = false;
                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    hasPermissions = hasPermissions(AppSettings.PERMISSIONS);
                    if (!hasPermissions) {
                        ActivityCompat.requestPermissions(this, AppSettings.PERMISSIONS, AppSettings.PERMISSION_ALL);
                    }
                } else {
                    hasPermissions = true;
                }

                // True only if android version API <= 22 OR permissions granted automatically
                if (hasPermissions) {
                    //write
                    showSaveGraphDialog(graphString);
                }
            }
        }
    }

    public void resetGraphSequence(){
        graphWrapper.board.clearCanvasAnim();

        if(graphSequence != null){
            graphSequence.curSeqNo = 0;
            UtilUI.setText(tv_seqno, "0/" + graphSequence.size);
        }
        else{
            UtilUI.setText(tv_info, "-");
            UtilUI.setText(tv_seqno, "0");
        }
    }

    private void checkPermissions(){
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!hasPermissions(AppSettings.PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, AppSettings.PERMISSIONS, AppSettings.PERMISSION_ALL);
            }
        }
    }

    public boolean hasPermissions(String... permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppSettings.PERMISSION_ALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(v_main, "Permissions Granted.",
                        Snackbar.LENGTH_SHORT).show();

                exportCurrentGraph();
            }
            else {
                final Snackbar snackbar = Snackbar.make(v_main, "Couldn't export graph [No write permission granted].",
                        Snackbar.LENGTH_LONG);
                snackbar.setAction("Give Permissions", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPermissions();
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}