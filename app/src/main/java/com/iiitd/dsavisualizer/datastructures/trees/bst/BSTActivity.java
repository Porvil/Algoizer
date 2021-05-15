package com.iiitd.dsavisualizer.datastructures.trees.bst;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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

import androidx.appcompat.widget.TooltipCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
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
import com.iiitd.dsavisualizer.datastructures.trees.avl.AVLActivity;
import com.iiitd.dsavisualizer.runapp.activities.BaseActivity;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

// BST FrontEnd
public class BSTActivity extends BaseActivity {

    LinearLayout ll_anim;
    ImageButton btn_nav;
    ImageButton btn_menu;
    ImageButton btn_info;
    SeekBar sb_animspeed;
    TextView tv_info;

    ImageButton btn_closemenu;
    ImageButton btn_helpmenu;
    ImageButton btn_insertrandom;
    Button btn_insert;
    Button btn_search;
    Button btn_delete;
    Button btn_inorder;
    Button btn_preorder;
    Button btn_postorder;
    Button btn_cleartree;
    Button btn_example1;
    Button btn_example2;
    Button btn_example3;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    ImageButton btn_closenav;
    ConstraintLayout cl_home;
    ConstraintLayout cl_bst;
    ConstraintLayout cl_avl;

    TableLayout tableLayout;
    ArrayList<TableRow> tableRows = new ArrayList<>();

    ArrayList<List<TreeLayoutElement>> treeLayout = TreeLayout.treeLayout;
    BST bst;
    TreeLayoutData treeLayoutData;

    Random random = new Random();
    Timer timer = null;
    boolean isAnimating = false;
    int animStepDuration = AppSettings.DEFAULT_ANIM_SPEED;
    int animDuration = AppSettings.DEFAULT_ANIM_DURATION;
    final int LAYOUT_MAIN = R.layout.activity_tree;
    final int LAYOUT_LEFT = R.layout.navigation_tree;
    final int LAYOUT_RIGHT = R.layout.controls_bst;
    final String ONBOARDING_KEY =  AppSettings.TREE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(LAYOUT_MAIN, LAYOUT_LEFT, LAYOUT_RIGHT, ONBOARDING_KEY);
        super.onCreate(savedInstanceState);

        v_main = vs_main.inflate();
        v_menu_right = vs_menu_right.inflate();
        v_menu_left = vs_menu_left.inflate();

        ll_anim = v_main.findViewById(R.id.ll_anim);
        sb_animspeed = v_main.findViewById(R.id.sb_animspeed);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_nav = v_main.findViewById(R.id.btn_nav);
        tv_info = v_main.findViewById(R.id.tv_info);

        btn_closemenu = v_menu_right.findViewById(R.id.btn_closemenu);
        btn_helpmenu = v_menu_right.findViewById(R.id.btn_helpmenu);
        btn_insert = v_menu_right.findViewById(R.id.btn_insert);
        btn_insertrandom = v_menu_right.findViewById(R.id.btn_insertrandom);
        btn_search = v_menu_right.findViewById(R.id.btn_search);
        btn_delete = v_menu_right.findViewById(R.id.btn_delete);
        btn_inorder = v_menu_right.findViewById(R.id.btn_inorder);
        btn_preorder = v_menu_right.findViewById(R.id.btn_preorder);
        btn_postorder = v_menu_right.findViewById(R.id.btn_postorder);
        btn_cleartree = v_menu_right.findViewById(R.id.btn_cleartree);
        btn_example1 = v_menu_right.findViewById(R.id.btn_example1);
        btn_example2 = v_menu_right.findViewById(R.id.btn_example2);
        btn_example3 = v_menu_right.findViewById(R.id.btn_example3);
        et_insert = v_menu_right.findViewById(R.id.et_insert);
        et_search = v_menu_right.findViewById(R.id.et_search);
        et_delete = v_menu_right.findViewById(R.id.et_delete);

        btn_closenav = v_menu_left.findViewById(R.id.btn_closenav);
        cl_home = v_menu_left.findViewById(R.id.cl_home);
        cl_bst = v_menu_left.findViewById(R.id.cl_bst);
        cl_avl = v_menu_left.findViewById(R.id.cl_avl);

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

                if(bst != null) {
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                    }
                }

                tv_name.setText(BSTStats.name);
                UtilUI.setTextInBigONotation(tv_worst_insert, BSTStats.worst_insert);
                UtilUI.setTextInBigONotation(tv_best_insert, BSTStats.best_insert);
                UtilUI.setTextInBigONotation(tv_worst_search, BSTStats.worst_search);
                UtilUI.setTextInBigONotation(tv_best_search, BSTStats.best_search);
                UtilUI.setTextInBigONotation(tv_worst_delete, BSTStats.worst_delete);
                UtilUI.setTextInBigONotation(tv_best_delete, BSTStats.best_delete);
                tv_traversals.setText(BSTStats.traversals);
                tv_space.setText(BSTStats.space);

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

        btn_example1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExampleTree(BSTInfo.tree1);
            }
        });

        btn_example2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExampleTree(BSTInfo.tree2);
            }
        });

        btn_example3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExampleTree(BSTInfo.tree3);
            }
        });

    }

    private void createExampleTree(List<Integer> tree) {
        closeDrawer(2);
        // Clear the Tree
        clearTree();

        ArrayList<ArrayList<TreeAnimationState>> list = new ArrayList<>();

        for(Integer data : tree){
            list.add(bst.insert(data));
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
                        }
                        else if (layoutElement.state == NodeState.ELEMENT_SHOWN) {
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
                            }
                            else if (layoutElement1.state == NodeState.ARROW_SHOWN) {
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
        bst = new BST();
        treeLayout = TreeLayout.treeLayout;
        for(TableRow tableRow : tableRows){
            for(int i=0;i<tableRow.getChildCount();i++){
                tableRow.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }

        treeLayoutData = new TreeLayoutData(context, treeLayout, tableRows);
    }

    private void startTimer(String operation, int data){
        closeDrawer(0);
        disableUI();

        if(timer == null) {
            switch (operation) {
                case "INSERT":
                    bst.insert(data);
                    break;
                case "DELETE":
                    bst.delete(data);
                    break;
                case "SEARCH":
                    bst.search(data);
                    break;
                case "INORDER":
                    bst.inorder();
                    break;
                case "PREORDER":
                    bst.preorder();
                    break;
                case "POSTORDER":
                    bst.postorder();
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
        if (bst != null) {
            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);
            bst.treeSequence.forward();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.treeAnimationStates.get(curSeqNo);
                        System.out.println(treeAnimationState);
                        UtilUI.setText(tv_info, bst.treeSequence.treeAnimationStates.get(curSeqNo).info);

                        switch (treeAnimationState.state) {
                            case NO_SPACE: {
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Toast.makeText(context, "No space in tree :(", Toast.LENGTH_SHORT).show();
                                    System.out.println("No space in tree :(");
                                }
                                break;
                            }
                            case NOT_FOUND: {
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Toast.makeText(context, "Element not found :(", Toast.LENGTH_SHORT).show();
                                    System.out.println("Element not found :(");
                                }
                                break;
                            }
                            case FOUND: {
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
                            case ORDER_TRAVERSAL: {
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
                            case SEARCH: {
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
                            case INSERT: {
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
                            case DELETE_1_CHILD: {
                                System.out.println("DEL");
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
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
                            case DELETE_DECREASE: {
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
                            case DELETE_NO_CHILD: {
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
                            case COPY_AND_MOVE: {
                                System.out.println("Copy move");
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
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

                                    float nextX = nextView.getX();
                                    float nextY = tableRows.get(nextPair.first).getY();

                                    float curX = currentView.getX();
                                    float curY = tableRows.get(curPair.first).getY();

                                    float X = curX - nextX;
                                    float Y = curY - nextY;

                                    nextView.animate().setDuration(0).translationX(X).start();
                                    nextView.animate().setDuration(0).translationY(Y).start();
                                }
                                break;
                            }
                            case MOVE_BACK: {
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
                                enableUI();
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        timer.cancel();
                        timer = null;
                    }
                }
            });

        }

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
    protected void initPseudoCode() {}

    @Override
    protected void initViews() {
        bst = new BST();

        tableLayout = new TableLayout(context);
        tableLayout.setClipChildren(false);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll_anim.addView(tableLayout);

        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                final int height = tableLayout.getHeight() / TreeLayout.treeLayout.size();

                final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int row = 0;
                int col = 0;
                for(List<TreeLayoutElement> treeLayout : treeLayout){
                    TableRow tableRow = new TableRow(context);
                    col = 0;
                    for(final TreeLayoutElement layoutElement : treeLayout){
                        final View treeNodeView = UtilUI.getTreeNodeView(context, layoutInflater, layoutElement, height, row, col);
                        final int finalRow = row;
                        treeNodeView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("Clicked");
                                if(layoutElement.type == NodeType.ELEMENT) {

                                    System.out.println(v.getX() + " | " + v.getY());
                                    View myView = layoutInflater.inflate(R.layout.layout_node_popup, null);
                                    TextView value = myView.findViewById(R.id.tv_node_value);
                                    TextView count = myView.findViewById(R.id.tv_node_count);
                                    TextView name = myView.findViewById(R.id.tv_node_name);

                                    TextView valueR = treeNodeView.findViewById(R.id.tv_elementvalue);
                                    TextView countR = treeNodeView.findViewById(R.id.tv_elementcount);

                                    name.setText("BST Node");
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

                                    Window window = dialog.getWindow();
                                    window.setGravity(Gravity.TOP | Gravity.LEFT);
                                    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                                    layoutParams.x = (int) v.getX();
                                    layoutParams.y = height * finalRow;
                                    window.setAttributes(layoutParams);

                                    dialog.setContentView(myView);
                                    dialog.show();
                                }
                            }
                        });

                        tableRow.addView(treeNodeView);
                        col++;
                    }

                    tableRows.add(tableRow);
                    tableLayout.addView(tableRow);
                    row++;
                }

                treeLayoutData = new TreeLayoutData(context, treeLayout, tableRows);
                dl_main.openDrawer(GravityCompat.END);
            }
        });

    }

    @Override
    protected void initNavigation() {
        int color = UtilUI.getCurrentThemeColor(context, R.attr.shade);

        cl_bst.setBackgroundColor(color);

        cl_bst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(1);
            }
        });

        cl_avl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
                UtilUI.startActivity(context, AVLActivity.class);
            }
        });

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

    }

    @Override
    protected void back(){
        if(isAnimating)
            return;

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
                finishAfterTransition();
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

    protected void disableUI() {
        isAnimating = true;
        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        btn_menu.setEnabled(false);
        btn_nav.setEnabled(false);
        btn_info.setEnabled(false);
    }

    @Override
    protected void enableUI() {
        isAnimating = false;
        btn_menu.setEnabled(true);
        btn_nav.setEnabled(true);
        btn_info.setEnabled(true);
        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void initToolTipTexts(){
        TooltipCompat.setTooltipText(btn_info, "Info");
        TooltipCompat.setTooltipText(btn_menu, "Controls Menu");
        TooltipCompat.setTooltipText(btn_nav, "Navigation Menu");
        TooltipCompat.setTooltipText(tv_info, "Current Animation Info");

        // Right Menu
        TooltipCompat.setTooltipText(btn_closemenu, "Close Controls");
        TooltipCompat.setTooltipText(btn_helpmenu, "Show Help");
        TooltipCompat.setTooltipText(btn_insertrandom, "Random Number");

        // Left Menu
        TooltipCompat.setTooltipText(btn_closenav, "Close Navigation");
    }

}