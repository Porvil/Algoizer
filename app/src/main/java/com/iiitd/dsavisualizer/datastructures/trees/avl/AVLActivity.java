package com.iiitd.dsavisualizer.datastructures.trees.avl;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.trees.NodeState;
import com.iiitd.dsavisualizer.datastructures.trees.NodeType;
import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeElementAnimationData;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayout;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayoutData;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayoutElement;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AVLActivity extends AppCompatActivity {

    Context context;

    DrawerLayout dl_main;
    View v_main;
    View v_menu;
    ViewStub vs_main;
    ViewStub vs_menu;
    LinearLayout ll_anim;
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
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    TableLayout tableLayout;
    ArrayList<TableRow> tableRows = new ArrayList<>();
    ArrayList<ArrayList<Pair<Integer, Integer>>> tableRowsCoordinates = new ArrayList<>();

    ArrayList<List<TreeLayoutElement>> treeLayout = TreeLayout.treeLayout;
    AVL avl;
    TreeLayoutData treeLayoutData;

    Random random = new Random();
    Timer timer = null;
    int animStepDuration = AppSettings.DEFAULT_ANIM_SPEED;
    int animDuration = AppSettings.DEFAULT_ANIM_DURATION;
    final int LAYOUT = R.layout.activity_tree;
    final int CONTROL = R.layout.controls_avl;

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
                View view = getLayoutInflater().inflate(R.layout.layout_trees_info, null);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView tv_worst_insert = view.findViewById(R.id.tv_worst_insert);
                TextView tv_best_insert = view.findViewById(R.id.tv_best_insert);
                TextView tv_worst_search = view.findViewById(R.id.tv_worst_search);
                TextView tv_best_search  = view.findViewById(R.id.tv_best_search);
                TextView tv_worst_delete = view.findViewById(R.id.tv_worst_delete);
                TextView tv_best_delete = view.findViewById(R.id.tv_best_delete);
                TextView tv_traversals = view.findViewById(R.id.tv_traversals);
                TextView tv_space = view.findViewById(R.id.tv_space);
                ImageButton btn_close = view.findViewById(R.id.btn_close);

                if(avl != null) {
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                    }
                }

                tv_name.setText(AVLStats.name);
                tv_worst_insert.setText(AVLStats.worst_insert);
                tv_best_insert.setText(AVLStats.best_insert);
                tv_worst_search.setText(AVLStats.worst_search);
                tv_best_search.setText(AVLStats.best_search);
                tv_worst_delete.setText(AVLStats.worst_delete);
                tv_best_delete.setText(AVLStats.best_delete);
                tv_traversals.setText(AVLStats.traversals);
                tv_space.setText(AVLStats.space);

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

        btn_insertrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int data = random.nextInt(100);
                et_insert.setText(String.valueOf(data));
                et_insert.setError(null);
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_insert.getText().toString();
                int data;
                if(!s.isEmpty()){
                    data = Integer.parseInt(s.trim());
                }
                else{
                    et_insert.setError("Cant be empty");
                    return;
                }

                startTimer("INSERT", data);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_search.getText().toString();
                int data;
                if(!s.isEmpty()){
                    data = Integer.parseInt(s.trim());
                }
                else{
                    et_search.setError("Cant be empty");
                    return;
                }

                startTimer("SEARCH", data);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_delete.getText().toString();
                int data;
                if(!s.isEmpty()){
                    data = Integer.parseInt(s.trim());
                }
                else{
                    et_delete.setError("Cant be empty");
                    return;
                }

                startTimer("DELETE", data);
            }
        });

        btn_inorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer("INORDER", -1);
            }
        });

        btn_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer("PREORDER", -1);
            }
        });

        btn_postorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer("POSTORDER", -1);
            }
        });

        btn_cleartree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTree();
            }
        });

        btn_tree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExampleTree(AVLInfo.tree1);
            }
        });

        btn_tree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExampleTree(AVLInfo.tree2);
            }
        });

        btn_tree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExampleTree(AVLInfo.tree3);
            }
        });

    }

    private void createExampleTree(List<Integer> tree) {

        // Clear the Tree
        clearTree();

        ArrayList<ArrayList<TreeAnimationState>> list = new ArrayList<>();

        for(Integer data : tree){
            list.add(avl.insert(data));
        }

        for(ArrayList<TreeAnimationState> insert : list){
            for(TreeAnimationState treeAnimationState : insert) {
                for(TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                    if (treeElementAnimationData.elementIndex != -1) {
                        int first = treeElementAnimationData.row;
                        int second = treeElementAnimationData.col;
                        View view = tableRows.get(first).getChildAt(second);
                        view.setVisibility(View.VISIBLE);

                        TextView value = view.findViewById(R.id.tv_elementvalue);
                        TextView count = view.findViewById(R.id.tv_elementcount);

                        int data = treeElementAnimationData.data;
                        int count1 = treeElementAnimationData.count;
                        TreeLayoutElement layoutElement = treeLayout.get(first).get(second);

                        value.setText(String.valueOf(data));
                        count.setText(String.valueOf(count1));

                        if (layoutElement.state == NodeState.ELEMENT_HIDDEN) {
                            value.setText(String.valueOf(data));
                            count.setText(String.valueOf(count1));
                            tableRows.get(first).getChildAt(second).setVisibility(View.VISIBLE);
                            treeLayout.get(first).get(second).state = NodeState.ELEMENT_SHOWN;
                            ViewAnimator.animate(view).bounceIn().duration(animDuration).start();
                        } else if (layoutElement.state == NodeState.ELEMENT_SHOWN) {
                            System.out.println("Shown");
                            ViewAnimator.animate(view).flash().duration(animDuration).start();
                        }

                        if (first > 0) {
                            View view1 = tableRows.get(first - 1).getChildAt(second);
                            view1.setVisibility(View.VISIBLE);
                            TreeLayoutElement layoutElement1 = treeLayout.get(first - 1).get(second);
                            if (layoutElement1.state == NodeState.ARROW_HIDDEN) {
                                tableRows.get(first - 1).getChildAt(second).setVisibility(View.VISIBLE);
                                treeLayout.get(first - 1).get(second).state = NodeState.ARROW_SHOWN;
                                ViewAnimator.animate(view1).bounceIn().duration(animDuration).start();
                            } else if (layoutElement1.state == NodeState.ARROW_SHOWN) {
                                System.out.println("Shown");
                                ViewAnimator.animate(view1).flash().duration(animDuration).start();
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearTree() {
        avl = new AVL();
        treeLayout = TreeLayout.treeLayout;
        for(TableRow tableRow : tableRows){
            for(int i=0;i<tableRow.getChildCount();i++){
                tableRow.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
        treeLayoutData = new TreeLayoutData(context, treeLayout, tableRows);
    }

    private void startTimer(String operation, int data){
        if(!dl_main.isOpen()) {
            System.out.println("OPEN");
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            btn_menu.setEnabled(false);
            btn_back.setEnabled(false);
            btn_info.setEnabled(false);
        }

        if(timer == null) {
            switch (operation) {
                case "INSERT":
                    avl.insert(data);
                    break;
                case "DELETE":
                    avl.delete(data);
                    break;
                case "SEARCH":
                    avl.search(data);
                    break;
                case "INORDER":
                    avl.inorder();
                    break;
                case "PREORDER":
                    avl.preorder();
                    break;
                case "POSTORDER":
                    avl.postorder();
                    break;
            }

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
        if (avl != null) {
            final int curSeqNo = avl.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);
            avl.treeSequence.forward();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < avl.treeSequence.size) {
                        TreeAnimationState treeAnimationState = avl.treeSequence.animationStates.get(curSeqNo);
                        System.out.println(treeAnimationState);
                        UtilUI.setText(tv_info, avl.treeSequence.animationStates.get(curSeqNo).info);

//                        for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                            switch (treeAnimationState.state) {
                                case "NS": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Toast.makeText(context, "No space in tree :(", Toast.LENGTH_SHORT).show();
                                        System.out.println("No space in tree :(");
                                    }
                                    break;
                                }
                                case "NF": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Toast.makeText(context, "Element not found :(", Toast.LENGTH_SHORT).show();
                                        System.out.println("Element not found :(");
                                    }
                                    break;
                                }
                                case "F": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                        TextView count = currentView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));

                                        ViewAnimator.animate(currentView).duration(animDurationTemp).flash().start();
                                        if (curPair.first > 0) {
                                            final View currentView1 = tableRows.get(curPair.first - 1).getChildAt(curPair.second);
                                            ViewAnimator.animate(currentView1).duration(animDurationTemp).flash().start();
                                        }
                                    }
                                    break;
                                }
                                case "P": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                        TextView count = currentView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));

                                        ViewAnimator.animate(currentView).duration(animDurationTemp).flash().start();
                                    }
                                    break;
                                }
                                case "S": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                        TextView count = currentView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));

                                        ViewAnimator.animate(currentView).duration(animDurationTemp).swing().start();
                                        if (curPair.first > 0) {
                                            final View currentView1 = tableRows.get(curPair.first - 1).getChildAt(curPair.second);
                                            ViewAnimator.animate(currentView1).duration(animDurationTemp).swing().start();
                                        }
                                    }
                                    break;
                                }
                                case "I": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                        TextView count = currentView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));

                                        if (curPair.first > 0) {
                                            final View currentView1 = tableRows.get(curPair.first - 1).getChildAt(curPair.second);
                                            TreeLayoutElement layoutElement = treeLayout.get(curPair.first - 1).get(curPair.second);
                                            if (layoutElement.state == NodeState.ARROW_HIDDEN) {
                                                layoutElement.state = NodeState.ARROW_SHOWN;
                                                ViewAnimator.animate(currentView1).duration(animDurationTemp).bounceIn().start();
                                            } else if (layoutElement.state == NodeState.ARROW_SHOWN) {
                                                ViewAnimator.animate(currentView1).duration(animDurationTemp).bounceIn().start();
                                            }
                                        }

                                        treeLayoutData.showElement(treeElementAnimationData.elementIndex);
                                        ViewAnimator.animate(currentView).duration(animDurationTemp).bounceIn().start();
                                    }
                                    break;
                                }
                                case "D": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        System.out.println("DEL");
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        ViewAnimator.animate(currentView).duration(animDurationTemp).flash().start()
                                                .onStop(new AnimationListener.Stop() {
                                                    @Override
                                                    public void onStop() {
                                                        treeLayoutData.hideElement(treeElementAnimationData.elementIndex);
                                                    }
                                                });
                                    }
                                    break;
                                }
                                case "C": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                        TextView count = currentView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));

                                        ViewAnimator.animate(currentView).duration(animDurationTemp).flash().start();
                                    }
                                    break;
                                }
                                case "1": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                        ViewAnimator.animate(currentView).duration(animDurationTemp).flash().start().onStop(new AnimationListener.Stop() {
                                            @Override
                                            public void onStop() {
                                                treeLayoutData.hideElement(treeElementAnimationData.elementIndex);
                                            }
                                        });
                                    }
                                    break;
                                }
                                case "CM": {
                                    ArrayList<Integer> visibleViews = new ArrayList<>();
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        visibleViews.add(treeElementAnimationData.newElementIndex);
                                    }

                                    System.out.println(visibleViews);
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        System.out.println("Copy move");
                                        System.out.println(treeElementAnimationData);
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        Pair<Integer, Integer> nextPair = TreeLayout.map.get(treeElementAnimationData.newElementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);
                                        final View nextView = tableRows.get(nextPair.first).getChildAt(nextPair.second);
                                        TreeLayoutElement nextElement = treeLayout.get(nextPair.first).get(nextPair.second);

                                    treeLayoutData.hideElement(treeElementAnimationData.elementIndex);

                                        TextView value = nextView.findViewById(R.id.tv_elementvalue);
                                        TextView count = nextView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));

                                        nextView.setVisibility(View.VISIBLE);
                                        nextElement.state = NodeState.ELEMENT_SHOWN;

//                                    float nextX = nextView.getX();
//                                    float nextY = tableRows.get(nextPair.first).getY();
//
//                                    float curX = currentView.getX();
//                                    float curY = tableRows.get(curPair.first).getY();

                                        float nextX = tableRowsCoordinates.get(nextPair.first).get(nextPair.second).first;
                                        float nextY = tableRowsCoordinates.get(nextPair.first).get(nextPair.second).second;

                                        float curX = tableRowsCoordinates.get(curPair.first).get(curPair.second).first;
                                        float curY = tableRowsCoordinates.get(curPair.first).get(curPair.second).second;

                                        float X = curX - nextX;
                                        float Y = curY - nextY;

//                                    System.out.println("Cur  = " + curX + " | " + curY);
//                                    System.out.println("Next = " + nextX + " | " + nextY);
//                                    System.out.println(" diff = " + X + " | " + Y);

                                        nextView.animate().setDuration(0).translationX(X).start();
                                        nextView.animate().setDuration(0).translationY(Y).start();
                                    }

                                    for (Integer i : visibleViews) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(i);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);
                                        currentView.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                }
                                case "MB": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        System.out.println("move back");
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        Pair<Integer, Integer> nextPair = TreeLayout.map.get(treeElementAnimationData.newElementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);
                                        final View nextView = tableRows.get(nextPair.first).getChildAt(nextPair.second);
                                        TreeLayoutElement curElement = treeLayout.get(curPair.first).get(curPair.second);
                                        TreeLayoutElement nextElement = treeLayout.get(nextPair.first).get(nextPair.second);
                                        treeLayoutData.hideElement(treeElementAnimationData.elementIndex);
                                        currentView.setVisibility(View.INVISIBLE);
                                        curElement.state = NodeState.ELEMENT_HIDDEN;

                                        TextView value = nextView.findViewById(R.id.tv_elementvalue);
                                        TextView count = nextView.findViewById(R.id.tv_elementcount);

                                        value.setText(String.valueOf(treeElementAnimationData.data));
                                        count.setText(String.valueOf(treeElementAnimationData.count));
                                        nextView.setVisibility(View.VISIBLE);
                                        nextElement.state = NodeState.ELEMENT_SHOWN;

                                        ViewAnimator.animate(nextView)
                                                .duration(animDurationTemp)
                                                .translationX(0)
                                                .translationY(0)
                                                .start()
                                                .onStop(new AnimationListener.Stop() {
                                                    @Override
                                                    public void onStop() {
                                                        treeLayoutData.showElement(treeElementAnimationData.newElementIndex);
                                                    }
                                                });
                                    }
                                    break;
                                }
                                case "R": {
                                    for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                        Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                        final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);
                                        View viewById = currentView.findViewById(R.id.fl_main);

                                        Drawable defColor = getResources().getDrawable(R.drawable.rounded_rectangle);
                                        Drawable highlightColor = getResources().getDrawable(R.drawable.rounded_rectangle_highlighted);
                                        ViewAnimator.animate(viewById).duration(animDurationTemp)
//                                                .backgroundColor(defColor, highlightColor, defColor)
                                                .flash().start();

//                                        ViewAnimator.animate(viewById).rotation(360).duration(animDuration).start();
                                    }
                                    break;
                                }
                                case "NULL": {

                                    System.out.println("NULLLLL");

                                    break;
                                }
                                default:
                                    System.out.println("DEFAULT SWITCH IN TASK -__-");
                                    Toast.makeText(context, "DEFAULT SWITCH IN TASK -__-", Toast.LENGTH_SHORT).show();
                            }
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
//                }
            });


        }

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

        avl = new AVL();

        tableLayout = new TableLayout(context);
        tableLayout.setClipChildren(false);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll_anim.addView(tableLayout);

        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                final int height = tableLayout.getHeight() / TreeLayout.treeLayout.size();

                final LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int row = 0;
                int col = 0;
                for(List<TreeLayoutElement> treeLayout : treeLayout){
                    final TableRow tableRow = new TableRow(context);
                    final ArrayList<Pair<Integer, Integer>> tableRowCoordinate = new ArrayList<>();
                    col = 0;
                    for(final TreeLayoutElement layoutElement : treeLayout){
                        final View bstView = UtilUI.getTreeNodeView(context, layoutInflater, layoutElement, height, row, col);
                        final int finalRow = row;
                        bstView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("Clicked");
                                if(layoutElement.type == NodeType.ELEMENT) {

                                    System.out.println(v.getX() + " | " + v.getY());
                                    View myView = layoutInflater.inflate(R.layout.layout_node_popup, null);
                                    TextView value = myView.findViewById(R.id.tv_node_value);
                                    TextView count = myView.findViewById(R.id.tv_node_count);
                                    TextView name = myView.findViewById(R.id.tv_node_name);

                                    TextView valueR = bstView.findViewById(R.id.tv_elementvalue);
                                    TextView countR = bstView.findViewById(R.id.tv_elementcount);

                                    name.setText("AVL Node");
                                    value.setText(valueR.getText().toString().trim());
                                    count.setText(countR.getText().toString().trim());

                                    final Dialog dialog = new Dialog(context);

                                    ImageButton btn_bst_close = myView.findViewById(R.id.btn_bst_close);

                                    btn_bst_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    // Setting dialogview
                                    Window window = dialog.getWindow();
                                    window.setGravity(Gravity.TOP | Gravity.LEFT);
                                    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
                                    wmlp.x = (int) v.getX();
                                    wmlp.y = height * finalRow;
                                    window.setAttributes(wmlp);
                                    dialog.setContentView(myView);
                                    dialog.show();
                                }
                            }
                        });
                        tableRow.addView(bstView);
                        col++;
                    }

                    tableRows.add(tableRow);
                    tableLayout.addView(tableRow);
                    row++;
                }

                tableLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int rowF = 0;
                        for(TableRow tableRow : tableRows){
                            System.out.println(tableRow);
                            final int finalRow = rowF;
                            ArrayList<Pair<Integer, Integer>> tableRowCoordinate = new ArrayList<>();
                            for(int i=0;i<tableRow.getChildCount();i++){
                                View childAt = tableRow.getChildAt(i);
//                                System.out.println(childAt.getX() + " | " + childAt.getY());
                                tableRowCoordinate.add(new Pair<>((int)childAt.getX(), height * finalRow));
                            }
                            tableRowsCoordinates.add(tableRowCoordinate);
                            rowF++;
                            System.out.println();
                        }
                    }
                },50);


                for(ArrayList<Pair<Integer, Integer>> a : tableRowsCoordinates){
                    for(Pair<Integer, Integer> p :  a){
                        System.out.print(p);
                    }
                    System.out.println();
                }

                treeLayoutData = new TreeLayoutData(context, treeLayout, tableRows);
                dl_main.openDrawer(Gravity.RIGHT);
            }
        });

    }

    // Not used, Reset All Tree Views to Default Positions
    private void reset(){
        int row = 0;
        for(TableRow tableRow : tableRows){
            int old = 0;
            for(int i=0;i<tableRow.getChildCount();i++){
                int weight = treeLayout.get(row).get(i).weight;
                float v = ((float) tableRow.getWidth() / 15) * old;
                tableRow.getChildAt(i).setX(v);
                tableRow.getChildAt(i).setY(0);
                NodeState state = treeLayout.get(row).get(i).state;
                if(state == NodeState.ELEMENT_SHOWN){
                    tableRow.getChildAt(i).setVisibility(View.VISIBLE);
                }
                else{
                    tableRow.getChildAt(i).setVisibility(View.INVISIBLE);
                }

                old += weight;
            }
            row++;
        }

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
                System.out.println("Dismmised");
                btn_menu.setEnabled(true);
                btn_back.setEnabled(true);
                btn_info.setEnabled(true);
                dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
    }

}