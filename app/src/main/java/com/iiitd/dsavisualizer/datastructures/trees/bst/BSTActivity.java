package com.iiitd.dsavisualizer.datastructures.trees.bst;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.trees.NodeState;
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


public class BSTActivity extends AppCompatActivity {

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
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    TableLayout tableLayout;
    ArrayList<TableRow> tableRows = new ArrayList<>();

    ArrayList<List<TreeLayoutElement>> treeLayout = TreeLayout.treeLayout;
    BST bst;
    TreeLayoutData treeLayoutData;

    Random random = new Random();
    Timer timer = null;
//    Timer timer = new Timer();
    boolean isAutoPlay = false;
    boolean isRandomArray = true;
    boolean isPseudocode = true;
    int autoAnimSpeed = 600;
//    int autoAnimSpeed = AppSettings.DEFAULT_ANIM_SPEED;
    final int LAYOUT = R.layout.activity_tree;
    final int CONTROL = R.layout.controls_bst;

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
        et_insert = v_menu.findViewById(R.id.et_insert);
        et_search = v_menu.findViewById(R.id.et_search);
        et_delete = v_menu.findViewById(R.id.et_delete);

        initViews();

        // Auto Animation Speed
        sb_animspeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 2500ms to 500ms
                autoAnimSpeed = (2000 - seekBar.getProgress() * 20) + 500;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//               if (bst != null && isAutoPlay) {
//                    timer.cancel();
//                    timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            if (bst.sequence.curSeqNo < bst.sequence.size)
//                                onForwardClick();
//                            else {
//                                isAutoPlay = false;
//                                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
//                                timer.cancel();
//                            }
//                        }
//                    }, 0, autoAnimSpeed);
//               }
            }
        });

        // Info Button
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.layout_info, null);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView tv_avg = view.findViewById(R.id.tv_avg);
                TextView tv_worst = view.findViewById(R.id.tv_worst);
                TextView tv_best = view.findViewById(R.id.tv_best);
                TextView tv_space = view.findViewById(R.id.tv_space);
                TextView tv_stable = view.findViewById(R.id.tv_stable);
                TextView tv_comparisons = view.findViewById(R.id.tv_comparisons);
                ImageButton btn_close = view.findViewById(R.id.btn_close);

                String comparisons = "-";
                if(bst != null) {
                    isAutoPlay = false;
                    timer.cancel();
                    comparisons = String.valueOf(1);
//                    comparisons = String.valueOf(bst.comparisons);
                }

                tv_name.setText(BSTStats.name);
                tv_avg.setText(BSTStats.avg);
                tv_worst.setText(BSTStats.worst);
                tv_best.setText(BSTStats.best);
                tv_space.setText(BSTStats.space);
                tv_stable.setText(BSTStats.stable);
                tv_comparisons.setText(comparisons);

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
                    isAutoPlay = false;
//                    timer.cancel();
                    dl_main.openDrawer(Gravity.RIGHT);
                }
            }
        });

        // Stop auto animation on drawer open
        dl_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset){
                if(slideOffset <= 0.40){
                    isAutoPlay = false;
//                    timer.cancel();
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView){}

            @Override
            public void onDrawerClosed(@NonNull View drawerView){}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
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
                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    }
                });

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
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        btn_inorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inorder();
            }
        });

        btn_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preorder();
            }
        });

        btn_postorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postorder();
            }
        });

        Button button2 = v_menu.findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<TreeAnimationState>> ch = new ArrayList<>();


                ch.add(bst.insert(100));
                ch.add(bst.insert(50));
                ch.add(bst.insert(150));
                ch.add(bst.insert(20));
                ch.add(bst.insert(70));
                ch.add(bst.insert(60));
                ch.add(bst.insert(80));
                ch.add(bst.insert(120));
                ch.add(bst.insert(200));
                ch.add(bst.insert(220));
                ch.add(bst.insert(110));
                ch.add(bst.insert(130));


//                ch.add(bst.insert(100));
////                ch.add(bst.insert(50));
//                ch.add(bst.insert(150));
////                ch.add(bst.insert(20));
////                ch.add(bst.insert(70));
////                ch.add(bst.insert(60));
////                ch.add(bst.insert(80));
//                ch.add(bst.insert(120));
//                ch.add(bst.insert(200));
//                ch.add(bst.insert(220));
//                ch.add(bst.insert(110));
//                ch.add(bst.insert(130));


                for(ArrayList<TreeAnimationState> insert : ch){
                    for(TreeAnimationState treeAnimationState: insert) {
                        for(TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {

                            System.out.println(treeElementAnimationData);
                            if (treeElementAnimationData.elementIndex != -1) {
                                int first = treeElementAnimationData.row;
                                int second = treeElementAnimationData.col;
                                View view = tableRows.get(first).getChildAt(second);
                                TextView value = view.findViewById(R.id.tv_elementvalue);
                                TextView count = view.findViewById(R.id.tv_elementcount);

                                int data = treeElementAnimationData.data;
                                int count1 = treeElementAnimationData.count;
                                TreeLayoutElement layoutElement = treeLayout.get(first).get(second);

                                value.setText(String.valueOf(data));
                                count.setText(String.valueOf(count1));
//                            System.out.println("COINT -> ========================  " + count1);
                                if (layoutElement.state == NodeState.ELEMENT_HIDDEN) {
                                    value.setText(String.valueOf(data));
                                    count.setText(String.valueOf(count1));
//                                System.out.println(first + ", " + second);
                                    tableRows.get(first).getChildAt(second).setVisibility(View.VISIBLE);
                                    treeLayout.get(first).get(second).state = NodeState.ELEMENT_SHOWN;
                                    ViewAnimator.animate(view).bounceIn().duration(1000).start();
                                } else if (layoutElement.state == NodeState.ELEMENT_SHOWN) {
                                    System.out.println("Shown");
                                    ViewAnimator.animate(view).flash().duration(1000).start();
                                }

//                        value.setText(String.valueOf(data));
////                        count.setText(String.valueOf(lastElementIndex.count));
//                        System.out.println(first + ", " + second);
//                        tableRows.get(first).getChildAt(second).setVisibility(View.VISIBLE);
                                if (first > 0) {
                                    View view1 = tableRows.get(first - 1).getChildAt(second);
                                    TreeLayoutElement layoutElement1 = treeLayout.get(first - 1).get(second);
                                    if (layoutElement1.state == NodeState.ARROW_HIDDEN) {
//                                    value.setText(String.valueOf(data));
//                                    count.setText(String.valueOf(count1));
//                                    System.out.println(first + ", " + second);
                                        tableRows.get(first - 1).getChildAt(second).setVisibility(View.VISIBLE);
                                        treeLayout.get(first - 1).get(second).state = NodeState.ARROW_SHOWN;
                                        ViewAnimator.animate(view1).bounceIn().duration(1000).start();
                                    } else if (layoutElement1.state == NodeState.ARROW_SHOWN) {
                                        System.out.println("Shown");
                                        ViewAnimator.animate(view1).flash().duration(1000).start();
                                    }
//                                tableRows.get(first-1).getChildAt(second).setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    private void preorder() {
        if(timer == null) {

            bst.preorder();

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (bst.treeSequence.curSeqNo < bst.treeSequence.size) {
                        System.out.println("TASK =  " + bst.treeSequence.curSeqNo);
                        bstPreOrder();
                    } else {
//                            isAutoPlay = false;
//                            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        System.out.println("Canceled");
//                        this.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            }, autoAnimSpeed, autoAnimSpeed);

            System.out.println("---------------------------------------");
        }
    }

    private void postorder() {
        if(timer == null) {

            bst.postorder();

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (bst.treeSequence.curSeqNo < bst.treeSequence.size) {
                        System.out.println("TASK =  " + bst.treeSequence.curSeqNo);
                        bstPostOrder();
                    } else {
//                            isAutoPlay = false;
//                            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        System.out.println("Canceled");
//                        this.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            }, autoAnimSpeed, autoAnimSpeed);

            System.out.println("---------------------------------------");
        }
    }

    private void inorder() {
        if(timer == null) {

            bst.inorder();

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (bst.treeSequence.curSeqNo < bst.treeSequence.size) {
                        System.out.println("TASK =  " + bst.treeSequence.curSeqNo);
                        bstInOrder();
                    } else {
//                            isAutoPlay = false;
//                            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        System.out.println("Canceled");
//                        this.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            }, autoAnimSpeed, autoAnimSpeed);

            System.out.println("---------------------------------------");
        }
    }

    private void search() {
        String s = et_search.getText().toString();
        int data = Integer.parseInt(s);
        System.out.println("search : " + s);

        if(!dl_main.isOpen()) {
            System.out.println("OPEN");
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            btn_menu.setEnabled(false);
        }
        else{
            System.out.println("Close");
        }

        if(timer == null) {

            System.out.println("Data ----------> " + data);
            ArrayList<TreeAnimationState> animationStates = bst.search(data);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (bst.treeSequence.curSeqNo < bst.treeSequence.size) {
                        System.out.println("TASK =  " + bst.treeSequence.curSeqNo);
                        bstSearchTask();
                    } else {
//                            isAutoPlay = false;
//                            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        System.out.println("Canceled");
//                        this.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            }, autoAnimSpeed, autoAnimSpeed);


            System.out.println("---------------------------------------");
        }
    }

    private void delete() {
        String s = et_delete.getText().toString();
        int data = Integer.parseInt(s);
        System.out.println("del : " + s);
//        if(s.isEmpty()){
//            Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            ArrayList<TreeAnimationState> delete = bst.delete(Integer.parseInt(s));
//
//        }

        if(!dl_main.isOpen()) {
            System.out.println("OPEN");
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            btn_menu.setEnabled(false);
        }
        else{
            System.out.println("Close");
        }

        if(timer == null) {

            System.out.println("Data ----------> " + data);
            ArrayList<TreeAnimationState> animationStates = bst.delete(data);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (bst.treeSequence.curSeqNo < bst.treeSequence.size) {
                        System.out.println("TASK =  " + bst.treeSequence.curSeqNo);
                        bstDeleteTask();
                    } else {
//                            isAutoPlay = false;
//                            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        System.out.println("Canceled");
//                        this.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            }, autoAnimSpeed, autoAnimSpeed);


            System.out.println("---------------------------------------");
        }
    }

    private void insert() {

        String s = et_insert.getText().toString();
        int data = random.nextInt(100);
        if(!s.isEmpty()){
            data = Integer.parseInt(s.trim());
        }

        if(!dl_main.isOpen()) {
            System.out.println("OPEN");
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            btn_menu.setEnabled(false);
        }
        else{
            System.out.println("Close");
        }

        if(timer == null) {

            System.out.println("Data ----------> " + data);
            ArrayList<TreeAnimationState> animationStates = bst.insert(data);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (bst.treeSequence.curSeqNo < bst.treeSequence.size) {
                        System.out.println("TASK =  " + bst.treeSequence.curSeqNo);
                        bstInsertTask();
                    } else {
//                            isAutoPlay = false;
//                            btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                        System.out.println("Canceled");
//                        this.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_menu.setEnabled(true);
                                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        timer.cancel();
                        timer = null;
                    }
                }
            }, autoAnimSpeed, autoAnimSpeed);


            System.out.println("---------------------------------------");

        }
    }

    private void bstInsertTask() {
        if (bst != null) {

            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.animationStates.get(curSeqNo);
                        for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                            System.out.println(treeElementAnimationData);
                            if (treeElementAnimationData.elementIndex != -1) {
                                int first = treeElementAnimationData.row;
                                int second = treeElementAnimationData.col;
                                View view = tableRows.get(first).getChildAt(second);
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
                                    ViewAnimator.animate(view).bounceIn().duration(1000).start();
                                } else if (layoutElement.state == NodeState.ELEMENT_SHOWN) {
                                    System.out.println("Shown");
                                    ViewAnimator.animate(view).flash().duration(1000).start();
                                }

                                if (first > 0) {
                                    View view1 = tableRows.get(first - 1).getChildAt(second);
                                    TreeLayoutElement layoutElement1 = treeLayout.get(first - 1).get(second);
                                    if (layoutElement1.state == NodeState.ARROW_HIDDEN) {
                                        tableRows.get(first - 1).getChildAt(second).setVisibility(View.VISIBLE);
                                        treeLayout.get(first - 1).get(second).state = NodeState.ARROW_SHOWN;
                                        ViewAnimator.animate(view1).bounceIn().duration(1000).start();
                                    } else if (layoutElement1.state == NodeState.ARROW_SHOWN) {
                                        System.out.println("Shown");
                                        ViewAnimator.animate(view1).flash().duration(1000).start();
                                    }
                                }
                            } else {
                                Toast.makeText(context, "No space in tree", Toast.LENGTH_SHORT).show();
                                System.out.println("No space in tree");
                            }
                        }

                    }
                }
            });

            bst.forward();
        }
    }

    private void bstDeleteTask() {
        if (bst != null) {

            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    tv_seqno.setText(curSeqNo + " / " + bst.treeSequence.animationStates.size());
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.animationStates.get(curSeqNo);
                        System.out.println("----------------------------------");
                        System.out.println(treeAnimationState);
                        switch (treeAnimationState.info){
                            case "D":
                                System.out.println("DEL");
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    System.out.println(treeElementAnimationData);
                                    final int first = treeElementAnimationData.row;
                                    final int second = treeElementAnimationData.col;
                                    final View view = tableRows.get(first).getChildAt(second);
                                    ViewAnimator.animate(view).duration(1000).bounce().start().onStop(new AnimationListener.Stop() {
                                        @Override
                                        public void onStop() {
                                            TreeLayoutElement layoutElement = treeLayout.get(first).get(second);
//                                            layoutElement.state = NodeState.ELEMENT_HIDDEN;
//                                            view.setVisibility(View.INVISIBLE);
                                            treeLayoutData.hideElement(treeElementAnimationData.elementIndex);
                                        }
                                    });

                                    if(first > 0){

                                    }

                                }
                                break;
                            case "CM":
                                System.out.println("Copy move");
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    System.out.println(treeElementAnimationData);
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    Pair<Integer, Integer> nextPair = TreeLayout.map.get(treeElementAnimationData.newElementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);
                                    final View nextView = tableRows.get(nextPair.first).getChildAt(nextPair.second);
                                    TreeLayoutElement curElement = treeLayout.get(curPair.first).get(curPair.second);
                                    TreeLayoutElement nextElement = treeLayout.get(nextPair.first).get(nextPair.second);

                                    treeLayoutData.hideElement(treeElementAnimationData.elementIndex);
//                                    currentView.setVisibility(View.INVISIBLE);
//                                    curElement.state = NodeState.ELEMENT_HIDDEN;

                                    TextView value = nextView.findViewById(R.id.tv_elementvalue);
                                    TextView count = nextView.findViewById(R.id.tv_elementcount);

                                    value.setText(treeElementAnimationData.data+"");
                                    count.setText(treeElementAnimationData.count+"");


                                    nextView.setVisibility(View.VISIBLE);
                                    nextElement.state = NodeState.ELEMENT_SHOWN;

                                    float nextX = nextView.getX();
                                    float nextY = tableRows.get(nextPair.first).getY();

                                    float curX = currentView.getX();
                                    float curY = tableRows.get(curPair.first).getY();

                                    System.out.println("Cur = " + curX + ":" + curY + " | Next = " + nextX + ":" + nextY);

                                    float X = curX - nextX;
                                    float Y = curY - nextY;

//                                    ViewAnimator.animate(nextView).duration(1).translationX(X).translationY(Y).start()
//                                    .onStop(new AnimationListener.Stop() {
//                                        @Override
//                                        public void onStop() {
////                                            treeLayoutData.showElement(treeElementAnimationData.newElementIndex);
//                                        }
//                                    });
                                    nextView.animate().setDuration(0).translationX(X).start();
                                    nextView.animate().setDuration(0).translationY(Y).start();

                                }
                                break;
                            case "MB":
                                System.out.println("move back");
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    System.out.println(treeElementAnimationData);
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

                                    value.setText(treeElementAnimationData.data+"");
                                    count.setText(treeElementAnimationData.count+"");
                                    nextView.setVisibility(View.VISIBLE);
                                    nextElement.state = NodeState.ELEMENT_SHOWN;


                                    float nextX = nextView.getX();
                                    float nextY = tableRows.get(nextPair.first).getY();

                                    float curX = currentView.getX();
                                    float curY = tableRows.get(curPair.first).getY();

                                    System.out.println("Cur = " + curX + ":" + curY + " | Next = " + nextX + ":" + nextY);

                                    float X = curX - nextX;
                                    float Y = curY - nextY;

                                    ViewAnimator.animate(nextView)
                                            .duration(500)
                                            .translationX(0)
                                            .translationY(0)
                                            .start()
                                            .onStop(new AnimationListener.Stop() {
                                        @Override
                                        public void onStop() {
                                            treeLayoutData.showElement(treeElementAnimationData.newElementIndex);
                                        }
                                    });
//                                    nextView.animate().setDuration(0).translationX(X).start();
//                                    nextView.animate().setDuration(0).translationY(Y).start();
                                }
                                break;

                            case "S":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    System.out.println(treeElementAnimationData);
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(treeElementAnimationData.data+"");
                                    count.setText(treeElementAnimationData.count+"");

                                    ViewAnimator.animate(currentView).duration(500).bounce().start();
                                }
                                break;
                            case "C":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    System.out.println(treeElementAnimationData);
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(treeElementAnimationData.data+"");
                                    count.setText(treeElementAnimationData.count+"");

                                    ViewAnimator.animate(currentView).duration(500).flash().start();
                                }
                                break;
                            case "1":
                                for (final TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    System.out.println(treeElementAnimationData);
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);
                                    final TreeLayoutElement curElement = treeLayout.get(curPair.first).get(curPair.second);


                                    ViewAnimator.animate(currentView).duration(500).flash().start().onStop(new AnimationListener.Stop() {
                                        @Override
                                        public void onStop() {
//                                            curElement.state = NodeState.ELEMENT_HIDDEN;
//                                            currentView.setVisibility(View.INVISIBLE);
                                            treeLayoutData.hideElement(treeElementAnimationData.elementIndex);
                                        }
                                    });
                                }
                                break;
                            case "NF":
                                Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }
            });

            bst.forward();
        }
    }

    private void bstSearchTask() {
        if (bst != null) {

            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.animationStates.get(curSeqNo);
                        System.out.println("----------------------------------");
                        System.out.println(treeAnimationState);
                        switch (treeAnimationState.info){
                            case "S":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(String.valueOf(treeElementAnimationData.data));
                                    count.setText(String.valueOf(treeElementAnimationData.count));

                                    ViewAnimator.animate(currentView).duration(500).bounce().start();
                                }
                                break;
                            case "NF":
                                Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
                                break;
                            case "F":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(String.valueOf(treeElementAnimationData.data));
                                    count.setText(String.valueOf(treeElementAnimationData.count));

                                    ViewAnimator.animate(currentView).duration(500).wobble().start();
                                }
                                break;
                        }

                    }
                }
            });

            bst.forward();
        }
    }

    private void bstInOrder() {
        if (bst != null) {

            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.animationStates.get(curSeqNo);
                        System.out.println("----------------------------------");
                        System.out.println(treeAnimationState);
                        switch (treeAnimationState.info){
                            case "P":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(String.valueOf(treeElementAnimationData.data));
                                    count.setText(String.valueOf(treeElementAnimationData.count));

                                    ViewAnimator.animate(currentView).duration(500).bounce().start();
                                }
                                break;
                        }
                    }
                }
            });

            bst.forward();
        }
    }


    private void bstPreOrder() {
        if (bst != null) {

            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.animationStates.get(curSeqNo);
                        System.out.println("----------------------------------");
                        System.out.println(treeAnimationState);
                        switch (treeAnimationState.info){
                            case "P":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(String.valueOf(treeElementAnimationData.data));
                                    count.setText(String.valueOf(treeElementAnimationData.count));

                                    ViewAnimator.animate(currentView).duration(500).bounce().start();
                                }
                                break;
                        }
                    }
                }
            });

            bst.forward();
        }
    }

    private void bstPostOrder() {
        if (bst != null) {

            final int curSeqNo = bst.treeSequence.curSeqNo;
            System.out.println("SEQ = "  + curSeqNo);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(curSeqNo < bst.treeSequence.size) {
                        TreeAnimationState treeAnimationState = bst.treeSequence.animationStates.get(curSeqNo);
                        System.out.println("----------------------------------");
                        System.out.println(treeAnimationState);
                        switch (treeAnimationState.info){
                            case "P":
                                for (TreeElementAnimationData treeElementAnimationData : treeAnimationState.elementAnimationData) {
                                    Pair<Integer, Integer> curPair = TreeLayout.map.get(treeElementAnimationData.elementIndex);
                                    final View currentView = tableRows.get(curPair.first).getChildAt(curPair.second);

                                    TextView value = currentView.findViewById(R.id.tv_elementvalue);
                                    TextView count = currentView.findViewById(R.id.tv_elementcount);

                                    value.setText(String.valueOf(treeElementAnimationData.data));
                                    count.setText(String.valueOf(treeElementAnimationData.count));

                                    ViewAnimator.animate(currentView).duration(500).bounce().start();
                                }
                                break;
                        }
                    }
                }
            });

            bst.forward();
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

        bst = new BST();

        tableLayout = new TableLayout(context);
        tableLayout.setClipChildren(false);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll_anim.addView(tableLayout);

        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                int height = tableLayout.getHeight() / TreeLayout.treeLayout.size();

                LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int row = 0;
                int col = 0;
                for(List<TreeLayoutElement> treeLayout : treeLayout){
                    TableRow tableRow = new TableRow(context);
                    col = 0;
                    for(TreeLayoutElement layoutElement : treeLayout){
                        tableRow.addView(UtilUI.getBSTView(context, layoutInflater, layoutElement, height, row, col));
                        col++;
                    }

                    tableRows.add(tableRow);
                    tableLayout.addView(tableRow);
                    row++;
                }

                treeLayoutData = new TreeLayoutData(context, treeLayout, tableRows);

            }
        });

    }

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
}