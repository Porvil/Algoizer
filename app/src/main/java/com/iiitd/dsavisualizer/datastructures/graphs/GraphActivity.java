package com.iiitd.dsavisualizer.datastructures.graphs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TooltipCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.snackbar.Snackbar;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.GraphAlgorithm;
import com.iiitd.dsavisualizer.runapp.activities.BaseActivity;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;
import com.otaliastudios.zoom.ZoomLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

// GraphActivity FrontEnd
public class GraphActivity extends BaseActivity {

    LinearLayout ll_anim;
    ImageView iv_grid;
    ImageView iv_coordinates;
    ImageView iv_graph;
    ImageView iv_anim;
    ImageButton btn_controls;
    ImageButton btn_error;
    ConstraintLayout cl_controls;
    ConstraintLayout cl_bottom;
    View inc_graphcontrols;
    ImageButton btn_play;
    ImageButton btn_nav;
    ImageButton btn_menu;
    ImageButton btn_code;
    ImageButton btn_info;
    ImageButton btn_backward;
    ImageButton btn_forward;
    SeekBar sb_animspeed;
    TextView tv_seqno;
    TextView tv_info;
    ImageButton btn_grid;
    ZoomLayout zl_graph;
    FrameLayout fl_graph;

    ImageButton btn_closemenu;
    ImageButton btn_helpmenu;
    RadioGroup rg_graphsize;
    RadioGroup rg_weighted;
    RadioGroup rg_directed;
    Button btn_bfs;
    EditText et_bfs;
    Button btn_bfsCC;
    Button btn_dfs;
    EditText et_dfs;
    Button btn_dfsCC;
    Button btn_dijkstra;
    EditText et_dijkstra;
    Button btn_bellmanford;
    Button btn_kruskal;
    Button btn_prim;
    ImageButton btn_custominputhelp;
    ImageButton btn_opengraph;
    ImageButton btn_clearcustominput;
    ImageButton btn_copygraph;
    ImageButton btn_pastecustominput;
    ImageButton btn_custominput;
    ImageButton btn_savecustominput;
    Button btn_cleargraph;
    Button btn_cleargraphanim;
    Button btn_example1;
    Button btn_example2;
    Button btn_example3;
    Button btn_example4;
    Button btn_example5;
    Button btn_example6;
    Button btn_example7;
    Button btn_example8;
    Button btn_example9;
    EditText et_customgraphinput;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    ImageButton btn_closenav;
    ConstraintLayout cl_home;

    GraphWrapper graphWrapper;
    CustomCanvas customCanvas;
    GraphControls graphControls;
    GraphAlgorithm graphAlgorithm;

    Timer timer = null;
    int animStepDuration = AppSettings.DEFAULT_ANIM_SPEED;
    final int LAYOUT_MAIN = R.layout.activity_graph;
    final int LAYOUT_LEFT = R.layout.navigation_graph;
    final int LAYOUT_RIGHT = R.layout.controls_graph;
    final String ONBOARDING_KEY =  AppSettings.GRAPH_KEY;
    boolean isAutoPlay = false;
    boolean isLargeGraph = false;
    boolean directed = false;
    boolean weighted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(LAYOUT_MAIN, LAYOUT_LEFT, LAYOUT_RIGHT, ONBOARDING_KEY);
        super.onCreate(savedInstanceState);

        v_main = vs_main.inflate();
        v_menu_right = vs_menu_right.inflate();
        v_menu_left = vs_menu_left.inflate();

        // Main Layout findViewById's
        ll_anim = v_main.findViewById(R.id.ll_anim);
        iv_grid = v_main.findViewById(R.id.iv_grid);
        iv_coordinates = v_main.findViewById(R.id.iv_coordinates);
        iv_graph = v_main.findViewById(R.id.iv_graph);
        iv_anim = v_main.findViewById(R.id.iv_anim);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        cl_controls = v_main.findViewById(R.id.cl_controls);
        cl_bottom = v_main.findViewById(R.id.cl_bottom);
        btn_controls = v_main.findViewById(R.id.btn_controls);
        btn_error = v_main.findViewById(R.id.btn_error);
        inc_graphcontrols = v_main.findViewById(R.id.inc_graphcontrols);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        btn_play = v_main.findViewById(R.id.btn_play);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_code = v_main.findViewById(R.id.btn_code);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_nav = v_main.findViewById(R.id.btn_nav);
        btn_backward = v_main.findViewById(R.id.btn_backward);
        btn_forward = v_main.findViewById(R.id.btn_forward);
        tv_seqno = v_main.findViewById(R.id.tv_seqno);
        tv_info = v_main.findViewById(R.id.tv_info);
        btn_grid = v_main.findViewById(R.id.btn_grid);
        zl_graph = v_main.findViewById(R.id.zl_graph);
        fl_graph = v_main.findViewById(R.id.fl_graph);

        // Left Drawer findViewById's
        btn_closenav = v_menu_left.findViewById(R.id.btn_closenav);
        cl_home = v_menu_left.findViewById(R.id.cl_home);
        btn_bfs = v_menu_left.findViewById(R.id.btn_bfs);
        et_bfs = v_menu_left.findViewById(R.id.et_bfs);
        btn_bfsCC = v_menu_left.findViewById(R.id.btn_bfsCC);
        btn_dfs = v_menu_left.findViewById(R.id.btn_dfs);
        et_dfs = v_menu_left.findViewById(R.id.et_dfs);
        btn_dfsCC = v_menu_left.findViewById(R.id.btn_dfsCC);
        btn_dijkstra = v_menu_left.findViewById(R.id.btn_dijkstra);
        et_dijkstra = v_menu_left.findViewById(R.id.et_dijkstra);
        btn_bellmanford = v_menu_left.findViewById(R.id.btn_bellmanford);
        btn_kruskal = v_menu_left.findViewById(R.id.btn_kruskals);
        btn_prim = v_menu_left.findViewById(R.id.btn_prims);

        // Right Drawer findViewById's
        btn_closemenu = v_menu_right.findViewById(R.id.btn_closemenu);
        btn_helpmenu = v_menu_right.findViewById(R.id.btn_helpmenu);
        rg_graphsize = v_menu_right.findViewById(R.id.rg_graphsize);
        rg_weighted = v_menu_right.findViewById(R.id.rg_weighted);
        rg_directed = v_menu_right.findViewById(R.id.rg_directed);
        btn_custominputhelp = v_menu_right.findViewById(R.id.btn_custominputhelp);
        btn_opengraph = v_menu_right.findViewById(R.id.btn_opengraph);
        btn_clearcustominput = v_menu_right.findViewById(R.id.btn_clearcustominput);
        btn_copygraph = v_menu_right.findViewById(R.id.btn_copygraph);
        btn_pastecustominput = v_menu_right.findViewById(R.id.btn_pastecustominput);
        btn_custominput = v_menu_right.findViewById(R.id.btn_custominput);
        btn_savecustominput = v_menu_right.findViewById(R.id.btn_savecustominput );
        btn_custominput = v_menu_right.findViewById(R.id.btn_custominput);
        btn_cleargraph = v_menu_right.findViewById(R.id.btn_cleargraph);
        btn_cleargraphanim = v_menu_right.findViewById(R.id.btn_cleargraphanim);
        btn_example1 = v_menu_right.findViewById(R.id.btn_example1);
        btn_example2 = v_menu_right.findViewById(R.id.btn_example2);
        btn_example3 = v_menu_right.findViewById(R.id.btn_example3);
        btn_example4 = v_menu_right.findViewById(R.id.btn_example4);
        btn_example5 = v_menu_right.findViewById(R.id.btn_example5);
        btn_example6 = v_menu_right.findViewById(R.id.btn_example6);
        btn_example7 = v_menu_right.findViewById(R.id.btn_example7);
        btn_example8 = v_menu_right.findViewById(R.id.btn_example8);
        btn_example9 = v_menu_right.findViewById(R.id.btn_example9);
        et_customgraphinput = v_menu_right.findViewById(R.id.et_customgraphinput);
        et_insert = v_menu_right.findViewById(R.id.et_insert);
        et_search = v_menu_right.findViewById(R.id.et_search);
        et_delete = v_menu_right.findViewById(R.id.et_delete);

        initOnBoarding();
        initViews();
        initNavigation();
        initToolTipTexts();

        // Auto Animation Speed
        sb_animspeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 2500ms to 500ms
                animStepDuration = (2000 - seekBar.getProgress() * 20) + 500;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (graphAlgorithm!= null && graphAlgorithm.graphSequence != null && isAutoPlay) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (graphAlgorithm.graphSequence.curSeqNo < graphAlgorithm.graphSequence.getSize()-1){
                                graphAlgorithm.graphSequence.forward();
                                taskStep(graphAlgorithm.graphSequence.curSeqNo);
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

        });

        btn_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(graphControls != null) {
                    graphControls.changeGraphViewState();
                    updateGraphViewState();
                }
            }
        });

        // Info Button
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(graphAlgorithm != null && graphAlgorithm.graphAlgorithmType != GraphAlgorithmType.NULL) {
                    GraphAlgorithmStats graphAlgorithmStats = GraphAlgorithmStats.getInstance(graphAlgorithm.graphAlgorithmType);

                    View view = getLayoutInflater().inflate(R.layout.layout_graph_info, null);
                    TextView tv_name = view.findViewById(R.id.tv_name);
                    TextView tv_time = view.findViewById(R.id.tv_time);
                    TextView tv_space = view.findViewById(R.id.tv_space);
                    ImageButton btn_close = view.findViewById(R.id.btn_close);

                    pauseAnimation();

                    tv_name.setText(graphAlgorithmStats.algorithm);
                    UtilUI.setTextInBigONotation(tv_time, graphAlgorithmStats.time);
                    UtilUI.setTextInBigONotation(tv_space, graphAlgorithmStats.space);

                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(view);
                    dialog.show();

                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    Toast.makeText(context, "No Algorithm Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_controls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cl_controls.getVisibility() == View.VISIBLE){
                    hideControls();
                }
                else{
                    showControls();
                }
            }
        });

        dl_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(slideOffset >= .05){
                    pauseAnimation();
                    if(graphAlgorithm != null) {
                        graphAlgorithm.hidePopUpsWhenDrawerIsOpened();
                    }
                }
                else {
                    if(graphAlgorithm != null) {
                        graphAlgorithm.showPopUpsWhenDrawerIsClosed();
                    }
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {}

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}

            @Override
            public void onDrawerStateChanged(int newState) {}

        });

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(1);
            }
        });

        btn_closenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(1);
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(2);
            }
        });

        btn_closemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(2);
            }
        });

        btn_helpmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnBoarding();
            }
        });

        btn_bfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.BFS);
            }
        });

        btn_bfsCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.BFS_CC);
            }
        });

        btn_dfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.DFS);
            }
        });

        btn_dfsCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.DFS_CC);
            }
        });

        btn_dijkstra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.DIJKSTRA);
            }
        });

        btn_bellmanford.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.BELLMAN_FORD);
            }
        });

        btn_kruskal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.KRUSKALS);
            }
        });

        btn_prim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                algorithm(GraphAlgorithmType.PRIMS);
            }
        });

        btn_opengraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasPermissions = false;
                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    hasPermissions = hasPermissions(AppSettings.PERMISSIONS);
                    if (!hasPermissions) {
                        ActivityCompat.requestPermissions(GraphActivity.this, AppSettings.PERMISSIONS, AppSettings.PERMISSION_ALL_IMPORT);
                    }
                } else {
                    hasPermissions = true;
                }

                // True only if android version API <= 22 OR permissions granted automatically
                if (hasPermissions) {
                    openGraphFile();
                }
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
                parseAndShowCustomInput(customGraphString, false);
            }
        });

        btn_custominputhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = layoutInflater.inflate(R.layout.layout_graph_custominputhelp, null);
                ImageButton btn_close = view.findViewById(R.id.btn_close);

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(view);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        btn_cleargraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphWrapper.reset();
                graphAlgorithm.reset();

                closeDrawer(0);
                resetAlgorithm();
            }
        });

        btn_cleargraphanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(0);
                resetAlgorithm();
            }
        });

        btn_clearcustominput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_customgraphinput.setText("");
            }
        });

        btn_example1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example1);
            }
        });

        btn_example2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example2);
            }
        });

        btn_example3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example3);
            }
        });

        btn_example4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example4);
            }
        });

        btn_example5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example5);
            }
        });

        btn_example6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example6);
            }
        });

        btn_example7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example7);
            }
        });

        btn_example8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example8);
            }
        });

        btn_example9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example(GraphExamples.example9);
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

        rg_graphsize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_large) {
                    isLargeGraph = true;
                }
                else if (checkedId == R.id.rb_small) {
                    isLargeGraph = false;
                }

                // Re-init's everything
                initCanvasAndGraph();

                graphAlgorithm.reset();
                resetGraphSequence();
            }
        });

        rg_directed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_directed) {
                    directed = true;
                }
                else if (checkedId == R.id.rb_undirected) {
                    directed = false;
                }

                graphWrapper.changeDirected(directed);
                graphAlgorithm.reset();
                resetGraphSequence();
            }
        });

        rg_weighted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_weighted) {
                    weighted = true;
                }
                else if (checkedId == R.id.rb_unweighted) {
                    weighted = false;
                }

                graphWrapper.changeWeighted(weighted);
                graphAlgorithm.reset();
                resetGraphSequence();
            }
        });

        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Graph Modified, Re-Run the algorithm", Toast.LENGTH_LONG).show();
            }
        });

        // Auto Animation Play/Pause Button
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(graphAlgorithm != null && graphAlgorithm.graphSequence != null){
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
                                if (graphAlgorithm.graphSequence.curSeqNo < graphAlgorithm.graphSequence.getSize()-1){
                                    graphAlgorithm.graphSequence.forward();
                                    taskStep(graphAlgorithm.graphSequence.curSeqNo);
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
                pauseAnimation();
                if(graphAlgorithm != null && graphAlgorithm.graphSequence != null){
                    graphAlgorithm.graphSequence.backward();
                    taskStep(graphAlgorithm.graphSequence.curSeqNo);
                }

            }
        });

        // One Animation Step-Forward
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAnimation();
                if(graphAlgorithm != null && graphAlgorithm.graphSequence != null){
                    graphAlgorithm.graphSequence.forward();
                    taskStep(graphAlgorithm.graphSequence.curSeqNo);
                }
            }
        });

    }

    private void pauseAnimation(){
        if(graphAlgorithm != null && graphAlgorithm.graphSequence != null){
            isAutoPlay = false;
            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
            if(timer != null) {
                timer.cancel();
            }
        }
    }

    private void openGraphFile(){
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        startActivityForResult(
                Intent.createChooser(chooseFile, "Choose a " + AppSettings.GRAPH_SAVEFILE_EXTENSION +" file."),
                AppSettings.GRAPH_PICKFILE_RESULT_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == AppSettings.GRAPH_PICKFILE_RESULT_CODE && resultCode == RESULT_OK){
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();

                String path = uri.getPath();
                if(path != null && !path.isEmpty()){
                    if(Util.isValidGraphSaveFile(path)){
                        try {
                            String text = UtilUI.readTextFromUri(context, uri);
                            if(GraphWrapper.isGraphInput(text)){
                                et_customgraphinput.setText(text);
                                parseAndShowCustomInput(text, false);
                                closeDrawer(0);
                            }
                            else{
                                Toast.makeText(context,
                                        "The file doesn't contain graph input.",
                                        Toast.LENGTH_LONG).show();
                            }
                            System.out.println(text);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Exception generated while reading the file.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(context,
                                "Not a valid " + AppSettings.GRAPH_SAVEFILE_EXTENSION + " file.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }


    private void example(String exampleString){
        // Reset Graph
        graphWrapper.reset();

        // Reset Algorithm
        resetAlgorithm();

        // Parse the Example
        parseAndShowCustomInput(exampleString, true);

        // Close drawer
        closeDrawer(2);
    }

    private void algorithm(GraphAlgorithmType graphAlgorithmType){
        // error = 0 -> No Error
        // error = 1 -> Toast Error
        // error = 2 -> EditText Error
        int error = 0;
        String errorMessage = "";

        switch (graphAlgorithmType){
            case BFS: {
                    String s = et_bfs.getText().toString();
                    int vertexNumber;

                    if (graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "BFS needs un-weighted graph";
                    }
                    else if(s.isEmpty()){
                        error = 2;
                        et_bfs.setError("Cant be empty");
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else if (!graphWrapper.graph.checkContainsVertices(Integer.parseInt(s.trim()))) {
                        error = 2;
                        et_bfs.setError("Vertex not present in graph");
                    }
                    else {
                        error = 0;
                        vertexNumber = Integer.parseInt(s.trim());
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.BFS, vertexNumber);
                    }
                }
                break;
            case BFS_CC: {
                    if (graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "BFS connected components needs un-weighted graph";
                    }
                    else if (graphWrapper.directed) {
                        error = 1;
                        errorMessage = "BFS connected components needs un-directed graph";
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else {
                        error = 0;
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.BFS_CC, -1);
                    }
                }
                break;
            case DFS: {
                    String s = et_dfs.getText().toString();
                    int vertexNumber;

                    if (graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "DFS needs un-weighted graph";
                    }
                    else if(s.isEmpty()){
                        error = 2;
                        et_dfs.setError("Cant be empty");
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else if (!graphWrapper.graph.checkContainsVertices(Integer.parseInt(s.trim()))) {
                        error = 2;
                        et_dfs.setError("Vertex not present in graph");
                    }
                    else {
                        error = 0;
                        vertexNumber = Integer.parseInt(s.trim());
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.DFS, vertexNumber);
                    }
                }
                break;
            case DFS_CC: {
                    if (graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "DFS connected components needs un-weighted graph";
                    }
                    else if (graphWrapper.directed) {
                        error = 1;
                        errorMessage = "DFS connected components needs un-directed graph";
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else {
                        error = 0;
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.DFS_CC, -1);
                    }
                }
                break;
            case DIJKSTRA:{
                    String s = et_dijkstra.getText().toString();
                    int vertexNumber;

                    if (!graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "Dijkstra needs weighted graph";
                    }
                    else if (graphWrapper.graph.hasNegativeEdges()) {
                        error = 1;
                        errorMessage = "Dijkstra needs edge weight >= 0";
                    }
                    else if(s.isEmpty()){
                        error = 2;
                        et_dijkstra.setError("Cant be empty");
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else if (!graphWrapper.graph.checkContainsVertices(Integer.parseInt(s.trim()))) {
                        error = 2;
                        et_dijkstra.setError("Vertex not present in graph");
                    }
                    else {
                        error = 0;
                        vertexNumber = Integer.parseInt(s.trim());
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.DIJKSTRA, vertexNumber);
                    }
                }
                break;
            case BELLMAN_FORD:{
                    if (!graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "Bellman Ford needs weighted graph";
                    }
                    else if(!graphWrapper.directed && graphWrapper.graph.hasNegativeEdges()){
                        error = 1;
                        errorMessage = "Bellman Ford doesn't work for undirected graphs having negative edges[negative edge loops]";
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else {
                        error = 0;
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.BELLMAN_FORD, -1);
                    }
                }
                break;
            case KRUSKALS: {
                    if (!graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "Kruskal's needs weighted graph";
                    }
                    else if (graphWrapper.directed) {
                        error = 1;
                        errorMessage = "Kruskal's needs undirected graph";
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else {
                        error = 0;
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.KRUSKALS, -1);
                    }
                }
                break;
            case PRIMS:  {
                    if (!graphWrapper.weighted) {
                        error = 1;
                        errorMessage = "Prim's needs weighted graph";
                    }
                    else if (graphWrapper.directed) {
                        error = 1;
                        errorMessage = "Prim's needs undirected graph";
                    }
                    else if (graphWrapper.getNoOfNodes() <= 0) {
                        error = 1;
                        errorMessage = "Empty Graph";
                    }
                    else {
                        error = 0;
                        resetGraphSequence();
                        graphWrapper.board.clearGraph(true);

                        graphAlgorithm.runAlgo(graphWrapper.graph, GraphAlgorithmType.PRIMS, -1);
                    }
                }
                break;
            default:
                error = 1;
                errorMessage = "Algorithm Not Available";
                break;
        }

        switch (error){
            case 0: // No Error
                // Changes graph controls to VIEW_STATE
                graphControls.updateStateWithEnum(GraphControlState.VIEW);
                graphControls.updateDrawables();
                System.out.println(graphControls);

                setGraphChanged(false);
                hideControls();
                closeDrawer(0);

                if(graphAlgorithm.graphSequence.graphAnimationStates.size() > 0){
                    taskStep(0);
                    UtilUI.setText(tv_info, graphAlgorithm.graphSequence.graphAnimationStates.get(0).info);
                }

                UtilUI.setText(tv_seqno, "1/" + graphAlgorithm.graphSequence.size);
                break;
            case 1: // Toast Error
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                closeDrawer(0);
                break;
            case 2: // EditText Error
                break;
        }

    }

    private void taskStep(final int curSeqNo) {
        if (graphAlgorithm != null && graphAlgorithm.graphSequence != null) {
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(curSeqNo < graphAlgorithm.graphSequence.graphAnimationStates.size() && curSeqNo >= 0) {
                        String str_tv_info = graphAlgorithm.graphSequence.graphAnimationStates.get(curSeqNo).info;

                        UtilUI.setText(tv_seqno, curSeqNo+1 + "/" + graphAlgorithm.graphSequence.size);
                        UtilUI.setText(tv_info, UtilUI.stringToSpannableStringBuilder(context, tv_info, str_tv_info));

                        // Clear Graph
                        graphWrapper.board.clearGraph(true);

                        GraphAnimationState graphAnimationState = graphAlgorithm.graphSequence.graphAnimationStates.get(curSeqNo);
                        System.out.println(graphAnimationState);

                        graphAlgorithm.graphTreeDSPopUp.update(graphAnimationState.graphAnimationStateExtra);

                        // Edges
                        for(Edge edge : graphAnimationState.edges){
                            if(edge.graphAnimationStateType == GraphAnimationStateType.HIGHLIGHT){
                                graphWrapper.board.setPaintHighlight();
                                graphWrapper.board.drawEdge(edge, graphWrapper.directed, graphWrapper.weighted, true);
                            }
                            else if(edge.graphAnimationStateType == GraphAnimationStateType.NONE){}
                            else if(edge.graphAnimationStateType == GraphAnimationStateType.NORMAL){
                                graphWrapper.board.setPaintNormal();
                                graphWrapper.board.drawEdge(edge, graphWrapper.directed, graphWrapper.weighted, true);
                            }
                            else if(edge.graphAnimationStateType == GraphAnimationStateType.DONE){
                                graphWrapper.board.setPaintDone();
                                graphWrapper.board.drawEdge(edge, graphWrapper.directed, graphWrapper.weighted, true);
                            }
                        }

                        if(graphAnimationState.graphAnimationStateExtra != null){
                            if(graphAnimationState.graphAnimationStateExtra.cycles != null){
                                for(Edge edge : graphAnimationState.graphAnimationStateExtra.cycles) {
                                    graphWrapper.board.setPaintDone();
                                    graphWrapper.board.drawEdge(edge, graphWrapper.directed, graphWrapper.weighted, true);
                                }
                            }
                        }


                        // Vertices
                        for(Map.Entry<Integer, Vertex> entry : graphAnimationState.verticesState.entrySet()){
                            if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.HIGHLIGHT){
                                graphWrapper.board.setPaintHighlight();
                                Vertex vertex = entry.getValue();
                                graphWrapper.board.drawVertex(vertex.data, vertex.row, vertex.col,true);
                            }
                            else if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.NONE){
                                graphWrapper.board.setPaintNormal();
                                Vertex vertex = entry.getValue();
                                graphWrapper.board.drawVertex(vertex.data, vertex.row, vertex.col,true);
                            }
                            else if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.NORMAL){
                                graphWrapper.board.setPaintNormal();
                                Vertex vertex = entry.getValue();
                                graphWrapper.board.drawVertex(vertex.data, vertex.row, vertex.col,true);
                            }
                            else if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.DONE){
                                graphWrapper.board.setPaintDone();
                                Vertex vertex = entry.getValue();
                                graphWrapper.board.drawVertex(vertex.data, vertex.row, vertex.col,true);
                            }

                            // Weights
                            if(graphAnimationState.graphAnimationStateExtra != null){
                                if(graphAnimationState.graphAnimationStateExtra.map != null &&
                                graphAnimationState.graphAnimationStateExtra.map.size() > 0) {
                                    if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.HIGHLIGHT){
                                        graphWrapper.board.setPaintHighlight();
                                        graphWrapper.board.drawVertexWeight(
                                                entry.getKey(),
                                                graphAnimationState.graphAnimationStateExtra.map.get(entry.getKey()), true);
                                    }
                                    else if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.NONE){
                                        graphWrapper.board.setPaintNormal();
                                        graphWrapper.board.drawVertexWeight(
                                                entry.getKey(),
                                                graphAnimationState.graphAnimationStateExtra.map.get(entry.getKey()), true);
                                    }
                                    else if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.NORMAL){
                                        graphWrapper.board.setPaintNormal();
                                        graphWrapper.board.drawVertexWeight(
                                                entry.getKey(),
                                                graphAnimationState.graphAnimationStateExtra.map.get(entry.getKey()), true);
                                    }
                                    else if(entry.getValue().graphAnimationStateType == GraphAnimationStateType.DONE){
                                        graphWrapper.board.setPaintDone();
                                        graphWrapper.board.drawVertexWeight(
                                                entry.getKey(),
                                                graphAnimationState.graphAnimationStateExtra.map.get(entry.getKey()), true);
                                    }
                                }
                            }

                        }

                        graphWrapper.board.refresh(true);
                    }
                    else{
                        UtilUI.setText(tv_info, "Done");
                    }
                }
            });

        }
        else{
            System.out.println("NO ALGO SELECTED");
        }

    }

    public void initCanvasAndGraph(){
        GraphData graphData = new GraphData(isLargeGraph);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_graph.getLayoutParams();
        layoutParams.height = graphData.Y;
        layoutParams.width = graphData.X;

        fl_graph.setLayoutParams(layoutParams);
        fl_graph.setBackgroundColor(UtilUI.getCurrentThemeColor(context, R.attr.shade));

        fl_graph.post(new Runnable() {
            @Override
            public void run() {
                customCanvas = new CustomCanvas(context, fl_graph, iv_graph, iv_grid, iv_anim, iv_coordinates);
                graphWrapper = new GraphWrapper(context, customCanvas, directed, weighted, isLargeGraph);
                updateGraphViewState();
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
        final String fileName = "graph-" + currentTimeStamp + AppSettings.GRAPH_SAVEFILE_EXTENSION;

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
        if(graphControls != null) {
            graphControls.updateState(view);
            graphControls.updateDrawables();
            System.out.println(graphControls);
        }
    }

    public void updateGraphViewState(){
        switch (graphControls.graphViewState){
            case GRAPH_ONLY:
                btn_grid.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_grid_off));
                iv_grid.setVisibility(View.GONE);
                iv_coordinates.setVisibility(View.GONE);
                break;
            case GRAPH_GRID:
                btn_grid.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_grid_on));
                iv_grid.setVisibility(View.VISIBLE);
                iv_coordinates.setVisibility(View.GONE);
                break;
            case GRAPH_GRID_COORDINATES:
                btn_grid.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_grid_on_with_coordinates));
                iv_grid.setVisibility(View.VISIBLE);
                iv_coordinates.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void parseAndShowCustomInput(String customGraphString, boolean isExample){
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

        System.out.println(ss.length);
        if(customGraphString.length() == 0){
            error = true;
            response = "Input is Empty";
        }
        else {
            try {
                for (String line : ss) {
                    String[] chars = line.split("\\s+");

                    switch (chars[0].toUpperCase()) {
                        case "D":
                            if (!gotDirection) {
                                directed = chars[1].equals("1");
                                gotDirection = true;
                            } else {
                                error = true;
                                response = "Multiple times \"direction\" variable provided";
                            }
                            break;
                        case "W": {
                            if (!gotWeight) {
                                weighted = chars[1].equals("1");
                                gotWeight = true;
                            } else {
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
                            } else {
                                error = true;
                                response = "Multiple times \"vertex count\" variable provided";
                            }
                            break;
                        }
                        case "VA": {
                            int data = Integer.parseInt(chars[1]);
                            int row = Integer.parseInt(chars[2]);
                            int col = Integer.parseInt(chars[3]);

                            if (row < 0)
                                row = -1;
                            if (col < 0)
                                col = -1;

                            if (data < 0 || data > 999) {
                                error = true;
                                response = "Node Value should be [0, 999]";
                                break;
                            }

                            Vertex vertex = new Vertex(data, row, col);

                            if (!vertices.contains(vertex)) {
                                vertices.add(vertex);
                            } else {
                                int index = vertices.indexOf(vertex);
                                vertices.get(index).row = row;
                                vertices.get(index).col = col;
                            }
                            break;
                        }
                        case "V": {
                            for (int c = 1; c < chars.length; c++) {
                                int data = Integer.parseInt(chars[c]);

                                if (data < 0 || data > 999) {
                                    error = true;
                                    response = "Node Value should be [0, 999]";
                                    break;
                                }

                                Vertex vertex = new Vertex(data, -1, -1);
                                if (!vertices.contains(vertex)) {
                                    vertices.add(vertex);
                                }
                            }
                            break;
                        }
                        case "E": {
                            int src = Integer.parseInt(chars[1]);
                            int des = Integer.parseInt(chars[2]);
                            int weight = weighted ? Integer.parseInt(chars[3]) : 1;

                            edges.add(new Edge(src, des, weight, directed));
                            break;
                        }
                        default:
                            error = true;
                            response = "Bad Input";
                            break;
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Exception while parsing graph data");
                error = true;
                response = "Bad Input, Exception while parsing graph data";
            }
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

                graphAlgorithm.reset();
                resetGraphSequence();
                graphWrapper.changeDirectedWeighted(directed, weighted);
                graphWrapper.customInput(vertices, edges);

                zl_graph.zoomTo(GraphSettings.defZoom, true);

                response = "Custom Graph input successful";
                if(!isExample){
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }

                closeDrawer(0);

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
                        ActivityCompat.requestPermissions(this, AppSettings.PERMISSIONS, AppSettings.PERMISSION_ALL_EXPORT);
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

    public void resetAlgorithm(){
        // Reset Animation Graph
        graphWrapper.board.clearGraph(true);

        if(graphAlgorithm != null){
            graphAlgorithm.reset();
        }

        setGraphChanged(false);
        showControls();

        UtilUI.setText(tv_info, "-");
        UtilUI.setText(tv_seqno, "-");
    }

    public void resetGraphSequence(){
        graphWrapper.board.clearGraph(true);

        if(graphAlgorithm != null && graphAlgorithm.graphSequence != null){
            graphAlgorithm.graphSequence.curSeqNo = 0;
            UtilUI.setText(tv_seqno, "1/" + graphAlgorithm.graphSequence.size);
        }
        else{
            UtilUI.setText(tv_info, "-");
            UtilUI.setText(tv_seqno, "-");
        }
    }

    private void hideControls(){
        if(cl_controls.getVisibility() == View.VISIBLE){
            ViewAnimator.animate(cl_controls).alpha(1, 0).duration(500).start().onStop(new AnimationListener.Stop() {
                @Override
                public void onStop() {
                    cl_controls.setVisibility(View.GONE);
                }
            });
            btn_controls.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_left_block));
        }
    }

    private void showControls(){
        if(cl_controls.getVisibility() != View.VISIBLE) {
            cl_controls.setVisibility(View.VISIBLE);
            ViewAnimator.animate(cl_controls).alpha(0, 1).duration(500).start();
            btn_controls.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_right_block));
        }
    }

    private void checkPermissions(int permission){
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!hasPermissions(AppSettings.PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, AppSettings.PERMISSIONS, permission);
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
        if (requestCode == AppSettings.PERMISSION_ALL_EXPORT) {
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
                        checkPermissions(AppSettings.PERMISSION_ALL_EXPORT);
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        }
        else if (requestCode == AppSettings.PERMISSION_ALL_IMPORT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(v_main, "Permissions Granted.",
                        Snackbar.LENGTH_SHORT).show();

                openGraphFile();
            }
            else {
                final Snackbar snackbar = Snackbar.make(v_main, "Couldn't import graph [No read permission granted].",
                        Snackbar.LENGTH_LONG);
                snackbar.setAction("Give Permissions", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPermissions(AppSettings.PERMISSION_ALL_IMPORT);
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

    private void setGraphChanged(boolean changed){
        if(changed && graphAlgorithm != null && graphAlgorithm.graphAlgorithmType != GraphAlgorithmType.NULL){
            btn_error.setAlpha(1f);
            btn_error.setClickable(true);
            btn_error.setEnabled(true);
            Toast.makeText(context, "Graph Modified, Re-Run the algorithm", Toast.LENGTH_LONG).show();
        }
        else{
            btn_error.setAlpha(0.1f);
            btn_error.setClickable(false);
            btn_error.setEnabled(false);
        }
    }

    @Override
    protected void initPseudoCode() {}

    @Override
    protected void initViews() {
        graphControls = new GraphControls(context, inc_graphcontrols);
        graphControls.updateDrawables();

        // Draws Grid and Graph View After Layouts have been laid out
        ll_anim.post(new Runnable() {
            @Override
            public void run() {
                graphAlgorithm = GraphAlgorithm.getInstance(context, ll_anim);
                setGraphChanged(false);
                UtilUI.setText(tv_info, "-");
                UtilUI.setText(tv_seqno, "-");
            }
        });

        zl_graph.post(new Runnable() {
            @Override
            public void run() {
                initCanvasAndGraph();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                System.out.println("Single touch up");

                float x1 = event.getX();
                float y1 = event.getY();

                final String edgeToast = graphWrapper.directed ? " " + UtilUI.getRightArrow() + " " : " ── ";

                TouchData touchData = graphWrapper.board.getTouchData(event);
                System.out.println(touchData);
                int row = touchData.row;
                int col = touchData.col;

                if(touchData.isElement || touchData.isExtendedElement) {
                    switch (graphControls.getCurrentState()) {
                        case VIEW:
                            break;
                        case VERTEX_ADD:
                            boolean addVertex = graphWrapper.addVertex(event);
                            setGraphChanged(addVertex);
                            if (!addVertex) {
                                Toast.makeText(context, "Vertex already present or too close to another vertex", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case VERTEX_REMOVE:
                            boolean removeVertex = graphWrapper.removeVertex(event);
                            setGraphChanged(removeVertex);
                            break;
                        case EDGE_ADD:
                            if (graphWrapper.board.getState(row, col)) {
                                if (graphControls.startEdge != -1) {//some node already selected
                                    final int des = graphWrapper.board.boardElements[row][col].value;
                                    final int src = graphControls.startEdge;
                                    if (src != des) {
                                        if (graphWrapper.weighted) {
                                            View myView = layoutInflater.inflate(R.layout.layout_edge_weight, null);

                                            final Dialog dialog = new Dialog(context);

                                            ImageButton btn_decreaseweight = myView.findViewById(R.id.btn_decreaseweight);
                                            ImageButton btn_increaseweight = myView.findViewById(R.id.btn_increaseweight);
                                            final EditText et_weight = myView.findViewById(R.id.et_weight);
                                            Button btn_edge_cancel = myView.findViewById(R.id.btn_edge_cancel);
                                            Button btn_edge_confirm = myView.findViewById(R.id.btn_edge_confirm);

                                            boolean updateOldEdge = false;
                                            if (graphWrapper.graph.checkContainsEdge(src, des)) {
                                                updateOldEdge = true;
                                                Edge edge = graphWrapper.graph.getEdge(src, des);
                                                if (edge != null) {
                                                    et_weight.setText(String.valueOf(edge.weight));
                                                }
                                            }

                                            btn_decreaseweight.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String s = et_weight.getText().toString();
                                                    int edgeWeight;
                                                    if (!s.isEmpty()) {
                                                        edgeWeight = Integer.parseInt(s.trim());
                                                        edgeWeight--;
                                                        et_weight.setText(String.valueOf(edgeWeight));
                                                    } else {
                                                        et_weight.setText(String.valueOf(1));
                                                    }
                                                }
                                            });

                                            btn_increaseweight.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String s = et_weight.getText().toString();
                                                    int edgeWeight;
                                                    if (!s.isEmpty()) {
                                                        edgeWeight = Integer.parseInt(s.trim());
                                                        edgeWeight++;
                                                        et_weight.setText(String.valueOf(edgeWeight));
                                                    } else {
                                                        et_weight.setText(String.valueOf(1));
                                                    }
                                                }
                                            });

                                            btn_edge_cancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    graphControls.startEdge = -1;
                                                    dialog.dismiss();
                                                }
                                            });

                                            final boolean finalUpdateOldEdge = updateOldEdge;
                                            btn_edge_confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String s = et_weight.getText().toString();
                                                    int edgeWeight;
                                                    if (!s.isEmpty()) {
                                                        edgeWeight = Integer.parseInt(s.trim());
                                                    } else {
                                                        et_weight.setError("Cant be empty");
                                                        return;
                                                    }

                                                    boolean addEdge = graphWrapper.addEdge(src, des, edgeWeight, finalUpdateOldEdge);
                                                    setGraphChanged(addEdge);
                                                    graphControls.startEdge = -1;
                                                    Toast.makeText(context, src + edgeToast + des, Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            });

                                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialog) {
                                                    graphControls.startEdge = -1;
                                                }
                                            });

                                            Window window = dialog.getWindow();
                                            window.setGravity(Gravity.TOP | Gravity.LEFT);
                                            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                                            layoutParams.x = (int) x1;
                                            layoutParams.y = (int) y1;
                                            window.setAttributes(layoutParams);

                                            dialog.setContentView(myView);
                                            dialog.show();
                                        } else {
                                            boolean addEdge = graphWrapper.addEdge(src, des);
                                            setGraphChanged(addEdge);
                                            graphControls.startEdge = -1;
                                            Toast.makeText(context, src + edgeToast + des, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {// first node getting selected now
                                    int data = graphWrapper.board.boardElements[row][col].value;
                                    graphControls.startEdge = data;
                                    Toast.makeText(context, data + " selected", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                        case EDGE_REMOVE:
                            if (graphWrapper.board.getState(row, col)) {
                                if (graphControls.startEdge != -1) {//some node already selected
                                    int des = graphWrapper.board.boardElements[row][col].value;
                                    int src = graphControls.startEdge;
                                    boolean removeEdge = graphWrapper.removeEdge(src, des);
                                    setGraphChanged(removeEdge);
                                    graphControls.startEdge = -1;
                                } else {// first node getting selected now
                                    int data = graphWrapper.board.boardElements[row][col].value;
                                    graphControls.startEdge = data;
                                    Toast.makeText(context, data + " selected", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                    }
                }

                // Careful about this below line, MUST BE CALLED
                graphWrapper.board.refresh(false);

                return true;
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
    protected void initNavigation() {
        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    @Override
    protected void back(){
        pauseAnimation();

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
                dialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(graphAlgorithm != null){
                    graphAlgorithm.graphTreePopUp.popupwindow.dismiss();
                    graphAlgorithm.graphTreeDSPopUp.popupwindow.dismiss();
                }
                dialog.dismiss();
                finishAfterTransition();
            }
        });

    }

    @Override
    protected void disableUI() {}

    @Override
    protected void enableUI() {}

    @Override
    protected void initToolTipTexts(){
        TooltipCompat.setTooltipText(btn_info, "Info");
        TooltipCompat.setTooltipText(btn_grid, "Grid Toggle");
        TooltipCompat.setTooltipText(btn_menu, "Controls Menu");
        TooltipCompat.setTooltipText(btn_nav, "Navigation Menu");
        TooltipCompat.setTooltipText(btn_play, "Play/Pause");
        TooltipCompat.setTooltipText(btn_forward, "Forward");
        TooltipCompat.setTooltipText(btn_backward, "Backward");
        TooltipCompat.setTooltipText(btn_controls, "Show/Hide Controls");
        TooltipCompat.setTooltipText(tv_info, "Current Animation Info");
        TooltipCompat.setTooltipText(tv_seqno, "Animation Step Counter");

        // Right Menu
        TooltipCompat.setTooltipText(btn_closemenu, "Close Controls");
        TooltipCompat.setTooltipText(btn_helpmenu, "Show Help");
        TooltipCompat.setTooltipText(btn_custominputhelp, "Custom Input Help");
        TooltipCompat.setTooltipText(btn_opengraph, "Load Graph");
        TooltipCompat.setTooltipText(btn_savecustominput, "Save Graph");
        TooltipCompat.setTooltipText(btn_copygraph, "Copy Graph");
        TooltipCompat.setTooltipText(btn_pastecustominput, "Paste Graph");
        TooltipCompat.setTooltipText(btn_clearcustominput, "Clear Custom Input");
        TooltipCompat.setTooltipText(btn_custominput, "Run Custom Input");

        // Left Menu
        TooltipCompat.setTooltipText(btn_closenav, "Close Navigation");
    }

}