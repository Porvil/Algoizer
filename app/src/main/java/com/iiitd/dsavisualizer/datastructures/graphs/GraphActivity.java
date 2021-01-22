package com.iiitd.dsavisualizer.datastructures.graphs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.Map;
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
    ImageButton btn_back;
    ImageButton btn_menu;
    ImageButton btn_grid;
    ImageButton btn_info;
    SeekBar sb_animspeed;
    TextView tv_info;

    ImageButton btn_closemenu;
    Button btn_bfs;
    Button btn_dfs;
    Button btn_cleartree;
    Button btn_tree1;
    Button btn_tree2;
    Button btn_tree3;
    Button btn_edge;
    Button btn_import;
    Button btn_export;
    EditText et1;
    EditText et_customgraphinput;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    GraphWrapper graphWrapper;
    CustomCanvas customCanvas;
    GraphControls graphControls;

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
        iv_grid = v_main.findViewById(R.id.iv_grid);
        iv_coordinates = v_main.findViewById(R.id.iv_coordinates);
        iv_graph = v_main.findViewById(R.id.iv_graph);
        iv_anim = v_main.findViewById(R.id.iv_anim);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        rg_graphcontrols = v_main.findViewById(R.id.rg_graphcontrols);
        rb_graphcontrol_view = v_main.findViewById(R.id.rb_graphcontrol_view);
        rb_graphcontrol_vertex = v_main.findViewById(R.id.rb_graphcontrol_vertex);
        rb_graphcontrol_edge = v_main.findViewById(R.id.rb_graphcontrol_edge);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_grid = v_main.findViewById(R.id.btn_grid);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_back = v_main.findViewById(R.id.btn_back);
        tv_info = v_main.findViewById(R.id.tv_info);
        cl_info = v_main.findViewById(R.id.cl_info);

        btn_closemenu = v_menu.findViewById(R.id.btn_closemenu);
        btn_bfs = v_menu.findViewById(R.id.btn_bfs);
        btn_dfs = v_menu.findViewById(R.id.btn_dfs);
        btn_cleartree = v_menu.findViewById(R.id.btn_cleartree);
        btn_tree1 = v_menu.findViewById(R.id.btn_tree1);
        btn_tree2 = v_menu.findViewById(R.id.btn_tree2);
        btn_tree3 = v_menu.findViewById(R.id.btn_tree3);
        btn_edge = v_menu.findViewById(R.id.btn_edge);
        btn_import = v_menu.findViewById(R.id.btn_import);
        btn_export = v_menu.findViewById(R.id.btn_export);
        et1 = v_menu.findViewById(R.id.et1);
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

        btn_bfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startTimer("INORDER", -1);
            }
        });

        btn_dfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startTimer("PREORDER", -1);
            }
        });


        btn_cleartree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clears animation graph only
//                graphWrapper.board.customCanvas.canvasAnimation.drawColor(0, PorterDuff.Mode.CLEAR);
                graphWrapper.board.clearCanvasAnim();
            }
        });
//
        btn_tree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paint paint = new Paint(Color.RED);
                paint.setColor(Color.RED);
                graphWrapper.board.customCanvas.canvasAnimation.drawCircle(100,100,50, paint);

            }
        });
//
        btn_tree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VertexOld vertexOld0 = graphOld.createVertex(graphOld.noOfVertices, 0, 0);
//                board.addVertex(0, 0, vertexOld0);
//
//                VertexOld vertexOld1 = graphOld.createVertex(graphOld.noOfVertices, 0, 3);
//                board.addVertex(0, 3, vertexOld1);
//
//                VertexOld vertexOld2 = graphOld.createVertex(graphOld.noOfVertices, 2, 0);
//                board.addVertex(2, 0, vertexOld2);
//
//                VertexOld vertexOld3 = graphOld.createVertex(graphOld.noOfVertices, 2, 4);
//                board.addVertex(2, 4, vertexOld3);
//
//                VertexOld vertexOld4 = graphOld.createVertex(graphOld.noOfVertices, 4, 3);
//                board.addVertex(4, 3, vertexOld4);




                graphWrapper.graph.addVertex(0,0,0);graphWrapper.board.addVertex(0,0,0);
                graphWrapper.graph.addVertex(1,0,3);graphWrapper.board.addVertex(0,3,1);
                graphWrapper.graph.addVertex(2,2,0);graphWrapper.board.addVertex(2,0,2);
                graphWrapper.graph.addVertex(3,2,4);graphWrapper.board.addVertex(2,4,3);
                graphWrapper.graph.addVertex(4,4,3);graphWrapper.board.addVertex(4,3,4);


                graphWrapper.graph.addEdge(0, 1, 1);
                graphWrapper.graph.addEdge(0, 2, 1);
                graphWrapper.graph.addEdge(1, 2, 1);
                graphWrapper.graph.addEdge(2, 3, 1);
                graphWrapper.graph.addEdge(3, 4, 1);

//                graph.addEdge(0, 1);
//                graph.addEdge(0, 2);
//                graph.addEdge(1, 2);
//                graph.addEdge(2, 0);
//                graph.addEdge(2, 3);
//                graph.addEdge(3, 3);


                graphWrapper.board.update(graphWrapper.graph);
            }
        });
//
        btn_tree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                VertexOld source = board.data[0][0].vertexOld;
//                BFSOld bfs = new BFSOld(graphOld);
//                bfs.run(source);

                BFS bfs = new BFS(graphWrapper.graph);
                bfs.run(0);

                startTimer("BFS", bfs);


            }
        });

        rg_graphcontrols.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_graphcontrol_view:
                        graphControls.selectedState = 0;
                        graphControls.viewState--;
                        break;
                    case R.id.rb_graphcontrol_vertex:
                        graphControls.selectedState = 1;
                        graphControls.vertexState--;
                        break;
                    case R.id.rb_graphcontrol_edge:
                        graphControls.selectedState = 2;
                        graphControls.edgeState--;
                        break;
                }
            }
        });

        btn_edge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String s1 = et1.getText().toString();
//
//                String[] split = s1.split("-");
//
//                int i1 = Integer.parseInt(split[0]);
//                int i2 = Integer.parseInt(split[1]);
//
//                graphWrapper.graph.addEdge(i1, i2,1);
//                graphWrapper.board.update(graphWrapper.graph);


                String s = et_customgraphinput.getText().toString();
                String[] ss = s.split("\\n");

//                System.out.println(s);
                for(String sss : ss){
                    System.out.println(sss);
                }

                int length = ss.length;
                String[] starts = ss[0].split("\\s+");
                boolean directed = starts[0].equals("0") ? false : true;
                boolean weighted = starts[1].equals("0") ? false : true;
                ArrayList<Integer> vertices = new ArrayList<>();
                ArrayList<Edge> edges = new ArrayList<>();

                for(int i=1;i<length;i++){
                    String[] split = ss[i].split("\\s+");

                    if(split[0].equals("V")){
                        for(int j=1;j<split.length;j++){
                            int vertex = Integer.parseInt(split[j]);
                            vertices.add(vertex);
                        }
                    }
                    else if(split[0].equals("E")){
                        int src = Integer.parseInt(split[1]);
                        int des = Integer.parseInt(split[2]);
                        int weight = weighted ? Integer.parseInt(split[3]) : 1;
                        edges.add(new Edge(src, des, weight));
                    }
                }

                graphWrapper.customInput(vertices, edges);

            }
        });

        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(graphWrapper != null){
                    StringBuilder stringBuilder = new StringBuilder();
                    String newLine = "\n";

                    //graph type
                    stringBuilder.append(graphWrapper.directed)
                            .append(" ")
                            .append(graphWrapper.weighted)
                            .append(newLine);

                    //vertices
                    int noOfVertices = graphWrapper.graph.noOfVertices;
                    stringBuilder.append("VC ")
                            .append(noOfVertices)
                            .append(newLine);
                    for( Map.Entry<Integer, Vertex> vertexEntry : graphWrapper.graph.vertexMap.entrySet()){
                        stringBuilder.append("V ")
                                .append(vertexEntry.getKey())
                                .append(" ")
                                .append(vertexEntry.getValue().row)
                                .append(" ")
                                .append(vertexEntry.getValue().col)
                                .append(newLine);
                    }

                    //edges
                    for( Map.Entry<Integer, ArrayList<Edge>> entry : graphWrapper.graph.map.entrySet()){
                        for(Edge i : entry.getValue()){
                            stringBuilder.append("E ")
                                    .append(i.src)
                                    .append(" ")
                                    .append(i.des)
                                    .append(" ")
                                    .append(i.weight)
                                    .append(newLine);
                        }
                    }

                    String string = stringBuilder.toString();
                    System.out.println(string);

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Graph to Clipboard", string);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(context,"Copied Graph to Clipboard", Toast.LENGTH_SHORT).show();
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
                    task(animDurationTemp, bfs);
                }
            }, animStepDuration, animStepDuration);

        }
    }

    private void task(final int animDurationTemp, final BFS bfs) {
        if (bfs != null) {
            final int curSeqNo = bfs.graphSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);
            bfs.graphSequence.forward();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    graphWrapper.board.paintVertex.setColor(getResources().getColor(R.color.mainColorDone));
                    graphWrapper.board.paintEdge.setColor(getResources().getColor(R.color.mainColorDone));

                    if(curSeqNo < bfs.graphSequence.graphAnimationStates.size()) {
                        GraphAnimationState graphAnimationState = bfs.graphSequence.graphAnimationStates.get(curSeqNo);
                        System.out.println(graphAnimationState);

                        for(GraphElementAnimationData graphElementAnimationData : graphAnimationState.elementAnimationData){
                            System.out.println(graphElementAnimationData.toString());
                            int row = graphElementAnimationData.row;
                            int col = graphElementAnimationData.col;

                            System.out.println(graphElementAnimationData);

                            Canvas canvas = graphWrapper.board.customCanvas.canvasAnimation;
                            Rect rect = graphWrapper.board.getRect(row, col);
//                            graphWrapper.board.__drawNode(canvas, rect, graphWrapper.board.data[row][col].data);
                            graphWrapper.board.drawNodeAnim(rect, graphElementAnimationData.des);
                            if(graphElementAnimationData.src != -1){
                                int[] vertex1 = graphWrapper.board.getCoordinates(graphElementAnimationData.src);
                                int[] vertex2 = graphWrapper.board.getCoordinates(graphElementAnimationData.des);

                                Rect rect1 = graphWrapper.board.getRect(vertex1[0], vertex1[1]);
                                Rect rect2 = graphWrapper.board.getRect(vertex2[0], vertex2[1]);

                                graphWrapper.board.drawEdgeAnim(rect1, rect2);
                            }

                            iv_anim.invalidate();
//                            graphWrapper.update();
                        }

//                        switch (graphAnimationState.info){
//
//                        }
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


    private void initViews() {

        graphControls = new GraphControls();

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
                                graphWrapper = new GraphWrapper(context, customCanvas, true, false);
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
                        graphWrapper.addVertex(event);
                        break;
                    case VERTEX_REMOVE:
                        graphWrapper.removeVertex(event);
                        break;
                    case EDGE_ADD:
                        if(graphWrapper.board.getState(row, col)){
                            if(graphControls.startEdge != -1){//some node already selected
                                int des = graphWrapper.board.data[row][col].data;
                                int src = graphControls.startEdge;
                                if(src != des) {
                                    graphWrapper.addEdge(src, des);
                                }
                                graphControls.startEdge = -1;
                            }
                            else{// first node getting selected now
                                int data = graphWrapper.board.data[row][col].data;
                                graphControls.startEdge = data;
                            }
                        }
                        break;
                    case EDGE_REMOVE:
                        if(graphWrapper.board.getState(row, col)){
                            if(graphControls.startEdge != -1){//some node already selected
                                int des = graphWrapper.board.data[row][col].data;
                                int src = graphControls.startEdge;
                                graphWrapper.removeEdge(src, des);
                                graphControls.startEdge = -1;
                            }
                            else{// first node getting selected now
                                int data = graphWrapper.board.data[row][col].data;
                                graphControls.startEdge = data;
                            }
                        }
                        break;

                }

//                graphWrapper.update();
                System.out.println(graphWrapper.board.getState(row, col));

                //Careful about this below line, MUST BE CALLED
                iv_graph.invalidate();

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

    public void onGraphControlsClick(View view) {
        int checkedId = view.getId();
//        System.out.println(graphControls.getCurrentState());

        switch (checkedId){
            case R.id.rb_graphcontrol_view:
                graphControls.changeState(0);
                break;
            case R.id.rb_graphcontrol_vertex:
                graphControls.changeState(1);
                break;
            case R.id.rb_graphcontrol_edge:
                graphControls.changeState(2);
                break;
        }

        System.out.println(graphControls.getCurrentState());
        switch(graphControls.getCurrentState()){
            case VIEW:{
                Drawable drawable = UtilUI.getDrawable(this, R.drawable.ic_baseline_pan_tool_24);
                rb_graphcontrol_view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            }
            case VERTEX_ADD: {
                Drawable drawable = UtilUI.getDrawable(this, R.drawable.ic_baseline_add_24);
                rb_graphcontrol_vertex.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            }
            case VERTEX_REMOVE: {
                Drawable drawable = UtilUI.getDrawable(this, R.drawable.ic_baseline_remove_24);
                rb_graphcontrol_vertex.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            }
            case EDGE_ADD:{
                Drawable drawable = UtilUI.getDrawable(this, R.drawable.ic_baseline_linear_scale_24);
                rb_graphcontrol_edge.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            }
            case EDGE_REMOVE:{
                Drawable drawable = UtilUI.getDrawable(this, R.drawable.ic_baseline_delete_24);
                rb_graphcontrol_edge.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            }
        }
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

}