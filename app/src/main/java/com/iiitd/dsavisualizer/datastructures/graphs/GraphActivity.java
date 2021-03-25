package com.iiitd.dsavisualizer.datastructures.graphs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.BFS;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.BellmanFord;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.DFS;
import com.iiitd.dsavisualizer.datastructures.graphs.algorithms.Dijkstra;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;
import com.iiitd.dsavisualizer.runapp.others.OnBoardingPopUp;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;
import com.otaliastudios.zoom.ZoomLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GraphActivity extends AppCompatActivity {

    Context context;

    DrawerLayout dl_main;
    View v_main;
    View v_menu_left;
    View v_menu_right;
    ViewStub vs_main;
    ViewStub vs_menu_left;
    ViewStub vs_menu_right;
    LinearLayout ll_anim;
    ImageView iv_grid;
    ImageView iv_coordinates;
    ImageView iv_graph;
    ImageView iv_anim;
    ImageButton btn_controls;
    ConstraintLayout cl_controls;
    RadioGroup rg_graphcontrols;
    RadioButton rb_graphcontrol_view;
    RadioButton rb_graphcontrol_vertex;
    RadioButton rb_graphcontrol_edge;
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
    RadioGroup rg_graphsize;
    RadioGroup rg_weighted;
    RadioGroup rg_directed;
    Button btn_bfs;
    EditText et_bfs;
    Button btn_dfs;
    EditText et_dfs;
    Button btn_dijkstra;
    EditText et_dijkstra;
    Button btn_bellmanford;
    EditText et_bellmanford;
    Button btn_kruskal;
    Button btn_prim;
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
    Button btn_tree4;
    Button btn_tree5;
    Button btn_tree6;
    EditText et_customgraphinput;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    ImageButton btn_closenav;
    ConstraintLayout cl_home;

    GraphWrapper graphWrapper;
    CustomCanvas customCanvas;
    GraphControls graphControls;
    GraphSequence graphSequence;
    GraphTree graphTree;
    GraphTreePopUp graphTreePopUp;
    GraphDSPopUp graphTreeDSPopUp;

    LayoutInflater layoutInflater;
    Timer timer = null;
    int animStepDuration = AppSettings.DEFAULT_ANIM_SPEED;
    int animDuration = AppSettings.DEFAULT_ANIM_DURATION;
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
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_base);
        context = this;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // findViewById
        dl_main = findViewById(R.id.dl_main);
        vs_main = findViewById(R.id.vs_main);
        vs_menu_left = findViewById(R.id.vs_menu_left);
        vs_menu_right = findViewById(R.id.vs_menu_right);
        vs_main.setLayoutResource(LAYOUT_MAIN);
        vs_menu_left.setLayoutResource(LAYOUT_LEFT);
        vs_menu_right.setLayoutResource(LAYOUT_RIGHT);
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
        rg_graphcontrols = v_main.findViewById(R.id.rg_graphcontrols);
        cl_controls = v_main.findViewById(R.id.cl_controls);
        btn_controls = v_main.findViewById(R.id.btn_controls);
        rb_graphcontrol_view = v_main.findViewById(R.id.rb_graphcontrol_view);
        rb_graphcontrol_vertex = v_main.findViewById(R.id.rb_graphcontrol_vertex);
        rb_graphcontrol_edge = v_main.findViewById(R.id.rb_graphcontrol_edge);
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
        btn_dfs = v_menu_left.findViewById(R.id.btn_dfs);
        et_dfs = v_menu_left.findViewById(R.id.et_dfs);
        btn_dijkstra = v_menu_left.findViewById(R.id.btn_dijkstra);
        et_dijkstra = v_menu_left.findViewById(R.id.et_dijkstra);
        btn_bellmanford = v_menu_left.findViewById(R.id.btn_bellmanford);
        et_bellmanford = v_menu_left.findViewById(R.id.et_bellmanford);
        btn_kruskal = v_menu_left.findViewById(R.id.btn_kruskals);
        btn_prim = v_menu_left.findViewById(R.id.btn_prims);

        // Right Drawer findViewById's
        btn_closemenu = v_menu_right.findViewById(R.id.btn_closemenu);
        rg_graphsize = v_menu_right.findViewById(R.id.rg_graphsize);
        rg_weighted = v_menu_right.findViewById(R.id.rg_weighted);
        rg_directed = v_menu_right.findViewById(R.id.rg_directed);
        btn_clearcustominput = v_menu_right.findViewById(R.id.btn_clearcustominput);
        btn_copygraph = v_menu_right.findViewById(R.id.btn_copygraph);
        btn_pastecustominput = v_menu_right.findViewById(R.id.btn_pastecustominput);
        btn_custominput = v_menu_right.findViewById(R.id.btn_custominput);
        btn_savecustominput = v_menu_right.findViewById(R.id.btn_savecustominput );
        btn_custominput = v_menu_right.findViewById(R.id.btn_custominput);
        btn_cleargraph = v_menu_right.findViewById(R.id.btn_cleargraph);
        btn_cleargraphanim = v_menu_right.findViewById(R.id.btn_cleargraphanim);
        btn_tree1 = v_menu_right.findViewById(R.id.btn_tree1);
        btn_tree2 = v_menu_right.findViewById(R.id.btn_tree2);
        btn_tree3 = v_menu_right.findViewById(R.id.btn_tree3);
        btn_tree4 = v_menu_right.findViewById(R.id.btn_tree4);
        btn_tree5 = v_menu_right.findViewById(R.id.btn_tree5);
        btn_tree6 = v_menu_right.findViewById(R.id.btn_tree6);
        et_customgraphinput = v_menu_right.findViewById(R.id.et_customgraphinput);
        et_insert = v_menu_right.findViewById(R.id.et_insert);
        et_search = v_menu_right.findViewById(R.id.et_search);
        et_delete = v_menu_right.findViewById(R.id.et_delete);

        initOnBoarding();
        initViews();
        initNavigation();

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

        btn_controls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cl_controls.getVisibility() == View.VISIBLE){
                    cl_controls.setVisibility(View.GONE);
                    btn_controls.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_arrow_left_24));
                }
                else{
                    cl_controls.setVisibility(View.VISIBLE);
                    btn_controls.setImageDrawable(UtilUI.getDrawable(context, R.drawable.ic_baseline_arrow_right_24));
                }
            }
        });

        // Menu Button
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dl_main.isOpen()) {
                    dl_main.openDrawer(GravityCompat.END);
                }
            }
        });

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.openDrawer(GravityCompat.START);
            }

        });

        btn_closemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        btn_bfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = et_bfs.getText().toString();
                int vertexNumber;
                if(!s.isEmpty()){
                    vertexNumber = Integer.parseInt(s.trim());
                }
                else{
                    et_bfs.setError("Cant be empty");
                    return;
                }

                if(graphWrapper.getNoOfNodes() <= 0){
                    et_bfs.setError("Empty Graph");
                    return;
                }
                else if(!graphWrapper.graph.checkContainsVertices(vertexNumber)){
                    et_bfs.setError("Vertex not present in graph");
                    return;
                }


                resetGraphSequence();
                graphWrapper.board.clearGraph(true);

                BFS bfs = new BFS(graphWrapper.graph);
                GraphSequence run = bfs.run(vertexNumber);
                graphSequence = run;

                GraphTree rungt = bfs.graphTree;
                graphTree = rungt;

                System.out.println("--------------DONE ------------ ");

                graphTree.printEdges();
                graphTree.printVertices();

                System.out.println("DONE ------------ ");

                graphTreePopUp.create("BFS Tree", graphTree);
                graphTreePopUp.show();

                graphTreeDSPopUp.create("QUEUE", GraphAlgorithmType.BFS);

                dl_main.closeDrawer(GravityCompat.START);

                System.out.println(graphSequence);
                UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
            }
        });

        btn_dfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = et_dfs.getText().toString();
                int vertexNumber;
                if(!s.isEmpty()){
                    vertexNumber = Integer.parseInt(s.trim());
                }
                else{
                    et_dfs.setError("Cant be empty");
                    return;
                }

                if(graphWrapper.getNoOfNodes() <= 0){
                    et_dfs.setError("Empty Graph");
                    return;
                }
                else if(!graphWrapper.graph.checkContainsVertices(vertexNumber)){
                    et_bfs.setError("Vertex not present in graph");
                    return;
                }

                resetGraphSequence();
                graphWrapper.board.clearGraph(true);

                DFS dfs = new DFS(graphWrapper.graph);

                GraphSequence run = dfs.run(vertexNumber);
                graphSequence = run;

                GraphTree rungt = dfs.graphTree;
                graphTree = rungt;

                graphTree.printEdges();
                graphTree.printVertices();

                graphTreePopUp.create("DFS Tree", graphTree);
                graphTreePopUp.show();

                graphTreeDSPopUp.create("STACK", GraphAlgorithmType.DFS);

                dl_main.closeDrawer(GravityCompat.START);

                System.out.println(graphSequence);
                UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
            }
        });

        btn_dijkstra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = et_dijkstra.getText().toString();
                int vertexNumber;
                if(!s.isEmpty()){
                    vertexNumber = Integer.parseInt(s.trim());
                }
                else{
                    et_dijkstra.setError("Cant be empty");
                    return;
                }

                if(graphWrapper.getNoOfNodes() <= 0){
                    et_dijkstra.setError("Empty Graph");
                    return;
                }
                else if(!graphWrapper.graph.checkContainsVertices(vertexNumber)){
                    et_dijkstra.setError("Vertex not present in graph");
                    return;
                }

                resetGraphSequence();
                graphWrapper.board.clearGraph(true);

                Dijkstra dijkstra = new Dijkstra(graphWrapper.graph);
                GraphSequence run = dijkstra.run(vertexNumber);
                graphSequence = run;

                btn_controls.performClick();
                dl_main.closeDrawer(GravityCompat.START);

                System.out.println(graphSequence);
                UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
            }
        });


        btn_bellmanford.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = et_bellmanford.getText().toString();
                int vertexNumber;
                if(!s.isEmpty()){
                    vertexNumber = Integer.parseInt(s.trim());
                }
                else{
                    et_bellmanford.setError("Cant be empty");
                    return;
                }

                if(graphWrapper.getNoOfNodes() <= 0){
                    et_bellmanford.setError("Empty Graph");
                    return;
                }
                else if(!graphWrapper.graph.checkContainsVertices(vertexNumber)){
                    et_bellmanford.setError("Vertex not present in graph");
                    return;
                }

                resetGraphSequence();
                graphWrapper.board.clearGraph(true);

                BellmanFord bellmanFord = new BellmanFord(graphWrapper.graph);
                GraphSequence run = bellmanFord.run(vertexNumber);
                graphSequence = run;


                dl_main.closeDrawer(GravityCompat.START);

                System.out.println(graphSequence);
                UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
            }
        });

        btn_kruskal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(graphWrapper.getNoOfNodes() <= 0){
                    et_bellmanford.setError("Empty Graph");
                    return;
                }

                resetGraphSequence();
                graphWrapper.board.clearGraph(true);

//                BellmanFord bellmanFord = new BellmanFord(graphWrapper.graph);
//                GraphSequence run = bellmanFord.run(vertexNumber);
//                graphSequence = run;


                dl_main.closeDrawer(GravityCompat.START);

//                System.out.println(graphSequence);
//                UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
            }
        });

        btn_prim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(graphWrapper.getNoOfNodes() <= 0){
                    et_bellmanford.setError("Empty Graph");
                    return;
                }

                resetGraphSequence();
                graphWrapper.board.clearGraph(true);

//                BellmanFord bellmanFord = new BellmanFord(graphWrapper.graph);
//                GraphSequence run = bellmanFord.run(vertexNumber);
//                graphSequence = run;


                dl_main.closeDrawer(GravityCompat.START);

//                System.out.println(graphSequence);
//                UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
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
                graphWrapper.reset();
                graphWrapper.board.clearGraph(true);
                graphSequence = null;

                // Removes the popUpWindows also
                graphTreePopUp.dismiss();
                graphTreeDSPopUp.dismiss();

                resetGraphSequence();
            }
        });

        btn_cleargraphanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphWrapper.board.clearGraph(true);
                resetGraphSequence();
            }
        });

        btn_clearcustominput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_customgraphinput.setText("");
            }
        });

        btn_tree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = GraphExamples.example1;

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        btn_tree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = GraphExamples.example2;

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        btn_tree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = GraphExamples.example3;

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        btn_tree4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = GraphExamples.example4;

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        btn_tree5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = GraphExamples.example5;

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

                dl_main.closeDrawer(GravityCompat.END);
            }
        });

        btn_tree6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customGraphString = GraphExamples.example6;

                graphWrapper.reset();
                parseAndShowCustomInput(customGraphString);

                dl_main.closeDrawer(GravityCompat.END);
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

                // Clears everything
                Toast.makeText(context, "Graphs will be cleared", Toast.LENGTH_SHORT).show();

                // Re-init's everything
                initCanvasAndGraph();

                graphSequence = null;
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

                // Clears everything
                Toast.makeText(context, "Graphs will be cleared", Toast.LENGTH_SHORT).show();
                graphWrapper.changeDirected(directed);
                graphSequence = null;
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

                // Clears everything
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
            btn_nav.setEnabled(false);
            btn_info.setEnabled(false);
        }

        if(timer == null) {

//            final int animDurationTemp = this.animDuration;
//            timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    task(animDurationTemp);
//                }
//            }, animStepDuration, animStepDuration);

        }
    }

    private void taskStep(final int curSeqNo) {
        if (graphSequence != null) {
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(curSeqNo < graphSequence.graphAnimationStates.size() && curSeqNo >= 0) {
                        UtilUI.setText(tv_seqno, curSeqNo+1 + "/" + graphSequence.size);
                        UtilUI.setText(tv_info, graphSequence.graphAnimationStates.get(curSeqNo).info);

                        graphWrapper.board.clearGraph(true);
                        GraphAnimationState graphAnimationState = graphSequence.graphAnimationStates.get(curSeqNo);
                        System.out.println(graphAnimationState);

                        // MAY BE CHECK IF GRAPH_ANIMATION_STATE_EXTRA != NULL
                        if(graphSequence.graphAlgorithmType == GraphAlgorithmType.BFS) {
                            System.out.println(graphAnimationState.graphAnimationStateExtra.queues);

                            graphTreeDSPopUp.create("QUEUE", GraphAlgorithmType.BFS);
                            graphTreeDSPopUp.update(graphAnimationState.graphAnimationStateExtra.queues);
                            graphTreeDSPopUp.show();
                        }

                        else if(graphSequence.graphAlgorithmType == GraphAlgorithmType.DFS) {
                            System.out.println(graphAnimationState.graphAnimationStateExtra.stacks);

                            graphTreeDSPopUp.create("STACK", GraphAlgorithmType.DFS);
                            graphTreeDSPopUp.update(graphAnimationState.graphAnimationStateExtra.stacks);
                            graphTreeDSPopUp.show();
                        }

                        for(Vertex vertex : graphAnimationState.vertices){
//                            Rect rect = graphWrapper.board.getRect(vertex.data);
//                            graphWrapper.board.drawNodeAnim(rect, vertex.data);
                            graphWrapper.board.drawVertex(vertex.data, true);
                        }

                        for(Edge edge : graphAnimationState.edges){
                            graphWrapper.board.drawEdge(edge, graphWrapper.directed, graphWrapper.weighted, true);
//                            Rect rect1 = graphWrapper.board.getRect(edge.src);
//                            Rect rect2 = graphWrapper.board.getRect(edge.des);
//                            graphWrapper.board.drawEdgeAnim(rect1, rect2, edge, graphWrapper.weighted, graphWrapper.directed);
                        }

                        if(graphAnimationState.graphAnimationStateExtra != null){
                            if(graphAnimationState.graphAnimationStateExtra.map != null){
                                System.out.println(graphAnimationState.graphAnimationStateExtra.map);
                                for (Map.Entry<Integer, Integer> entry : graphAnimationState.graphAnimationStateExtra.map.entrySet()) {
                                    graphWrapper.board.drawVertexWeight(
                                            entry.getKey(), entry.getValue(), true);
                                }
                            }
                        }

                        graphWrapper.board.refresh(true);

                    }
                    else{
                        UtilUI.setText(tv_info, "Done");
                        System.out.println("Canceled");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                btn_nav.setEnabled(true);
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

        graphControls = new GraphControls(context, rb_graphcontrol_view, rb_graphcontrol_vertex, rb_graphcontrol_edge);
        graphControls.updateDrawables();

        // Draws Grid and Graph View After Layouts have been laid out
        ll_anim.post(new Runnable() {
            @Override
            public void run() {
                int treePopUpWidth = (int) UtilUI.dpToPx(context, 200);
                int treeDSPopUpWidth = (int) UtilUI.dpToPx(context, 150);
                graphTreePopUp = new GraphTreePopUp(context, treePopUpWidth, ll_anim.getHeight(), ll_anim);
                graphTreeDSPopUp = new GraphDSPopUp(context, treeDSPopUpWidth, ll_anim.getHeight(), ll_anim);
            }
        });

        zl_graph.post(new Runnable() {
            @Override
            public void run() {

//                isLargeGraph = true;

                initCanvasAndGraph();

//                int rows = GraphSettings.getNoOfRows(isLargeGraph);
//                int cols = GraphSettings.getNoOfCols(isLargeGraph);
//
//                int px = (int) UtilUI.dpToPx(context, GraphSettings.getNodeSize(isLargeGraph));
//
//                int width = cols * px;
//                int height = rows * px;
//
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_graph.getLayoutParams();
//                layoutParams.height = height;
//                layoutParams.width = width;
//
//                fl_graph.setLayoutParams(layoutParams);
//                fl_graph.setBackgroundColor(UtilUI.getCurrentThemeColor(context, R.attr.shade));
//
//                fl_graph.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        customCanvas = new CustomCanvas(context, fl_graph, iv_graph, iv_grid, iv_anim, iv_coordinates);
//                        graphWrapper = new GraphWrapper(context, customCanvas, startDirected, startWeighted, isLargeGraph);
//                        updateGraphViewState();
//
//                        zl_graph.panTo(0,0, false);
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
                                final int des = graphWrapper.board.boardElements[row][col].value;
                                final int src = graphControls.startEdge;
                                if(src != des) {
                                    if (graphWrapper.weighted) {
                                        View myView = layoutInflater.inflate(R.layout.layout_edge_weight, null);

                                        final Dialog dialog = new Dialog(context);

                                        ImageButton btn_decreaseweight = myView.findViewById(R.id.btn_decreaseweight);
                                        ImageButton btn_increaseweight = myView.findViewById(R.id.btn_increaseweight);
                                        final EditText et_weight = myView.findViewById(R.id.et_weight);
                                        Button btn_edge_cancel = myView.findViewById(R.id.btn_edge_cancel);
                                        Button btn_edge_confirm = myView.findViewById(R.id.btn_edge_confirm);

                                        boolean updateOldEdge = false;
                                        if(graphWrapper.graph.checkContainsEdge(src, des)){
                                            updateOldEdge = true;
                                            Edge edge = graphWrapper.graph.getEdge(src, des);
                                            if(edge != null){
                                                et_weight.setText(String.valueOf(edge.weight));
                                            }
                                        }

                                        btn_decreaseweight.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String s = et_weight.getText().toString();
                                                int edgeWeight;
                                                if(!s.isEmpty()){
                                                    edgeWeight = Integer.parseInt(s.trim());
                                                    edgeWeight--;
                                                    et_weight.setText(String.valueOf(edgeWeight));
                                                }
                                                else{
                                                    et_weight.setText(String.valueOf(1));
                                                }
                                            }
                                        });

                                        btn_increaseweight.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String s = et_weight.getText().toString();
                                                int edgeWeight;
                                                if(!s.isEmpty()){
                                                    edgeWeight = Integer.parseInt(s.trim());
                                                    edgeWeight++;
                                                    et_weight.setText(String.valueOf(edgeWeight));
                                                }
                                                else{
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
                                                if(!s.isEmpty()){
                                                    edgeWeight = Integer.parseInt(s.trim());
                                                }
                                                else{
                                                    et_weight.setError("Cant be empty");
                                                    return;
                                                }

                                                graphWrapper.addEdge(src, des, edgeWeight, finalUpdateOldEdge);
                                                graphControls.startEdge = -1;
                                                Toast.makeText(context, src + " -> " + des, Toast.LENGTH_SHORT).show();
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
                                    }
                                    else{
                                        graphWrapper.addEdge(src, des);
                                        graphControls.startEdge = -1;
                                        Toast.makeText(context, src + " -> " + des, Toast.LENGTH_SHORT).show();
                                    }
                                }
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
                graphWrapper.board.refresh(false);

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

    public void initCanvasAndGraph(){
        int rows = GraphSettings.getNoOfRows(isLargeGraph);
        int cols = GraphSettings.getNoOfCols(isLargeGraph);

        int px = (int) UtilUI.dpToPx(context, GraphSettings.getNodeSize(isLargeGraph));

        int width = cols * px;
        int height = rows * px;

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_graph.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;

        fl_graph.setLayoutParams(layoutParams);
        fl_graph.setBackgroundColor(UtilUI.getCurrentThemeColor(context, R.attr.shade));

        fl_graph.post(new Runnable() {
            @Override
            public void run() {
                customCanvas = new CustomCanvas(context, fl_graph, iv_graph, iv_grid, iv_anim, iv_coordinates);
                graphWrapper = new GraphWrapper(context, customCanvas, directed, weighted, isLargeGraph);
                updateGraphViewState();

                zl_graph.panTo(0,0, false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (dl_main.isDrawerOpen(GravityCompat.START) || dl_main.isDrawerOpen(GravityCompat.END)){
            dl_main.closeDrawer(GravityCompat.START);
            dl_main.closeDrawer(GravityCompat.END);
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
                btn_nav.setEnabled(true);
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


                /*
                // NOT COMPLETED YET, half working
                int[] currentLimits = graphWrapper.board.getCurrentLimits();
                float zoomRatio = graphWrapper.board.getZoomRatio();

                graphWrapper.board.getZoomCentre();
                Rect zoomCentre = graphWrapper.board.getZoomCentre();
//                zl_graph.panTo(-zoomCentre.centerX(), -zoomCentre.centerY(), false);

//                zl_graph.zoomIn();
//                zl_graph.panTo(-379,-914, false);

                float zoomLayoutCenterX = (float) (zl_graph.getWidth() / 2);
                float zoomLayoutCenterY = (float) (zl_graph.getHeight() / 2);
                float contentCenterX    = (float) (zoomCentre.centerX()/ 2);
                float contentCenterY    = (float) (zoomCentre.centerY() / 2);
                float diffX = contentCenterX - zoomLayoutCenterX;
                float diffY = contentCenterY - zoomLayoutCenterY;
//                zl_graph.panTo(-diffX, -diffY, true);
                zl_graph.moveTo(1, 0,0,false);
                zl_graph.moveTo(zoomRatio, -diffX, -diffY, true);

//                zl_graph.panTo(-100,-100, false);
//                zl_graph.zoomTo(zoomRatio, false);
//                zl_graph.moveTo(zoomRatio, -zoomCentre.centerX(), -zoomCentre.centerY(), false);

                // UPTO HERE
                */

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
        graphWrapper.board.clearGraph(true);

        if(graphSequence != null){
            graphSequence.curSeqNo = 0;
            UtilUI.setText(tv_seqno, "1/" + graphSequence.size);
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

    private void initNavigation() {
        int color = UtilUI.getCurrentThemeColor(context, R.attr.shade);

//        cl_bst.setBackgroundColor(color);
//
//        cl_bst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dl_main.closeDrawer(GravityCompat.START);
//            }
//        });
//
//        cl_avl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                startActivity(new Intent(context, AVLActivity.class));
//            }
//        });

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_closenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.closeDrawer(GravityCompat.START);
            }
        });

    }

    private void initOnBoarding() {
        v_main.post(new Runnable() {
            @Override
            public void run() {
                boolean tutorialState = UtilUI.getTutorialState(context, ONBOARDING_KEY);
                if(!tutorialState) {
                    OnBoardingPopUp onboardingPopUp = new OnBoardingPopUp(context,
                            v_main.getWidth(), v_main.getHeight(),
                            v_main, ONBOARDING_KEY);
                    onboardingPopUp.show();
                }
            }
        });
    }

}