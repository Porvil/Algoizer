package com.iiitd.dsavisualizer.datastructures.trees.bst;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.trees.NodeState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayout;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayoutElement;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;


public class BSTActivity extends AppCompatActivity {

    Context context;

    DrawerLayout dl_main;
    View v_main;
    View v_menu;
    ViewStub vs_main;
    ViewStub vs_menu;
    LinearLayout ll_anim;
    ConstraintLayout cl_info;
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
    ScrollView sv_psuedocode;
    LinearLayout ll_psuedocode;

    Button btn_insert;
    Button btn_search;
    Button btn_delete;
    EditText et_insert;
    EditText et_search;
    EditText et_delete;

    TableLayout tableLayout;
    ArrayList<TableRow> tableRows = new ArrayList<>();

    ArrayList<List<TreeLayoutElement>> treeLayout = TreeLayout.treeLayout;
    BST bst;
    TextView[] textViews;

    Random random = new Random();
    Timer timer = new Timer();
    boolean isAutoPlay = false;
    boolean isRandomArray = true;
    boolean isPseudocode = true;
    int autoAnimSpeed = AppSettings.DEFAULT_ANIM_SPEED;
    final int LAYOUT = R.layout.activity_base;
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
        btn_play = v_main.findViewById(R.id.btn_play);
        btn_menu = v_main.findViewById(R.id.btn_menu);
        btn_code = v_main.findViewById(R.id.btn_code);
        btn_info = v_main.findViewById(R.id.btn_info);
        btn_back = v_main.findViewById(R.id.btn_back);
        btn_backward = v_main.findViewById(R.id.btn_backward);
        btn_forward = v_main.findViewById(R.id.btn_forward);
        tv_seqno = v_main.findViewById(R.id.tv_seqno);
        tv_info = v_main.findViewById(R.id.tv_info);
        sv_psuedocode = v_main.findViewById(R.id.sv_psuedocode);
        ll_psuedocode = v_main.findViewById(R.id.ll_pseudocode);
        cl_info = v_main.findViewById(R.id.cl_info);

        btn_insert = v_menu.findViewById(R.id.btn_insert);
        btn_search = v_menu.findViewById(R.id.btn_search);
        btn_delete = v_menu.findViewById(R.id.btn_delete);
        et_insert = v_menu.findViewById(R.id.et_insert);
        et_search = v_menu.findViewById(R.id.et_search);
        et_delete = v_menu.findViewById(R.id.et_delete);

        addPseudocode();
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

        // Auto Animation Play/Pause Button
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(bst != null){
//                    if(isAutoPlay){
//                        isAutoPlay = false;
//                        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
//                        timer.cancel();
//                    }
//                    else{
//                        isAutoPlay = true;
//                        btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PAUSE_BUTTON));
//                        timer = new Timer();
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                if (bst.sequence.curSeqNo < bst.sequence.size)
//                                    onForwardClick();
//                                else {
//                                    isAutoPlay = false;
//                                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
//                                    timer.cancel();
//                                }
//                            }
//                        }, 0, autoAnimSpeed);
//                    }
//                }
            }
        });

        // One Animation Step-Back
        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();
                onBackwardClick();
            }
        });

        // One Animation Step-Forward
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoPlay = false;
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();
                onForwardClick();
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

//                String comparisons = "-";
//                if(bst != null) {
//                    isAutoPlay = false;
//                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
//                    timer.cancel();
//                    comparisons = String.valueOf(bst.comparisons);
//                }
//
//                tv_name.setText(BSTStats.name);
//                tv_avg.setText(BSTStats.avg);
//                tv_worst.setText(BSTStats.worst);
//                tv_best.setText(BSTStats.best);
//                tv_space.setText(BSTStats.space);
//                tv_stable.setText(BSTStats.stable);
//                tv_comparisons.setText(comparisons);

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
                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                    timer.cancel();
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
                    btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                    timer.cancel();
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
                btn_play.setImageDrawable(UtilUI.getDrawable(context, AppSettings.PLAY_BUTTON));
                timer.cancel();

                View view = getLayoutInflater().inflate(R.layout.layout_back_confirmation, null);

                Button btn_cancel = view.findViewById(R.id.btn_cancel);
                Button btn_yes = view.findViewById(R.id.btn_yes);

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(view);
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
                        dialog.dismiss();
                        finish();
                    }
                });
            }
        });

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPseudocode) {
                    sv_psuedocode.setVisibility(View.GONE);
                    sv_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 0);
                }
                else {
                    sv_psuedocode.setVisibility(View.VISIBLE);
                    sv_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 0);

                }
                isPseudocode = !isPseudocode;

                if(isPseudocode)
                    treeLayout.get(2).get(3).state = NodeState.ELEMENT_SHOWN;
                else
                    treeLayout.get(2).get(3).state = NodeState.ELEMENT_HIDDEN;
                tableRows.get(2).getChildAt(3).setVisibility(treeLayout.get(2).get(3).getVisibility());

            }
        });


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_insert.getText().toString();
                int data = random.nextInt(100);
                if(!s.isEmpty()){
                    data = Integer.parseInt(s.trim());
                }

                System.out.println("Data ----------> " + data);

                int lastElementIndex = bst.insert(data);
                System.out.println(lastElementIndex);
                if(lastElementIndex!=-1){
                    Pair<Integer, Integer> pair = TreeLayout.map.get(lastElementIndex);
                    TextView textView = tableRows.get(pair.first).getChildAt(pair.second).findViewById(R.id.tv_elementvalue);
                    textView.setText(String.valueOf(data));
                    System.out.println(pair.first + ", " + pair.second);
                }
                else {
                    System.out.println("-_-");
                }

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        bst = new BST();

        tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll_anim.addView(tableLayout);


        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int row = 0;
        int col = 0;
        for(List<TreeLayoutElement> treeLayout : treeLayout){
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(layoutParams);

            col = 0;
            for(TreeLayoutElement layoutElement : treeLayout){
                tableRow.addView(UtilUI.getBSTView(context, layoutInflater, layoutElement, row, col));
                col++;
            }

            tableRows.add(tableRow);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,0,1));
            row++;
        }

    }

    private void addPseudocode(){
//        int sizeOfPseudocode = BSTInfo.psuedocode.length;
//        textViews = new TextView[sizeOfPseudocode];
//        for(int i=0;i<sizeOfPseudocode;i++){
//            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            TextView textView = new TextView(this);
//            textView.setLayoutParams(lparams);
//            textView.setText(BSTInfo.psuedocode[i]);
//            textView.setPadding(5, 0,0,0);
//            textViews[i] = textView;
//            ll_psuedocode.addView(textView);
//        }
//
//        for(int i : BSTInfo.boldIndexes){
//            textViews[i].setTypeface(textViews[i].getTypeface(), Typeface.BOLD);
//        }
    }

    private void onForwardClick(){
//        if (bst != null) {
//            bst.forward();
//            final int curSeqNo = bst.sequence.curSeqNo;
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tv_seqno.setText(curSeqNo + " / " + bst.sequence.animationStates.size());
//                    if(curSeqNo < bst.sequence.size) {
//                        String state = bst.sequence.animationStates.get(curSeqNo).state;
//                        if(BSTInfo.map.containsKey(state)){
//                            Integer[] integers = BSTInfo.map.get(state);
//                            UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
//                        }
//                        UtilUI.setText(tv_info, bst.sequence.animationStates.get(curSeqNo).info);
//                        UtilUI.highlightViews(context, bst.sequence.views,
//                                bst.sequence.animationStates.get(curSeqNo).highlightIndexes);
//                    }
//                    else{
//                        UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, null);
//                        UtilUI.highlightViews(context, bst.sequence.views,null);
//                        UtilUI.setText(tv_info, "Array is sorted");
//                    }
//                }
//            });
//        }

        Random random = new Random();
        int u = random.nextInt();
        int lastElementIndex = bst.insert(u);
        System.out.println("Ele =========================== " + u);
        System.out.println(lastElementIndex);
        if(lastElementIndex!=-1){
            Pair<Integer, Integer> pair = TreeLayout.map.get(lastElementIndex);
            TextView textView = tableRows.get(pair.first).getChildAt(pair.second).findViewById(R.id.tv_elementvalue);
            textView.setText(String.valueOf(u));
            System.out.println(pair.first + ", " + pair.second);
//                    tableRows.get(pair.first).getChildAt(pair.second).setVisibility(treeLayout.get(2).get(3).getVisibility());
        }
        else {
            System.out.println("-_-");
        }

    }

    private void onBackwardClick(){
//        if (bst != null) {
//            bst.backward();
//            final int curSeqNo = bst.sequence.curSeqNo;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tv_seqno.setText(curSeqNo + " / " + bst.sequence.animationStates.size());
//                    UtilUI.setText(tv_info, bst.sequence.animationStates.get(curSeqNo).info);
//                    String state = bst.sequence.animationStates.get(curSeqNo).state;
//                    if (BSTInfo.map.containsKey(state)) {
//                        Integer[] integers = BSTInfo.map.get(state);
//                        UtilUI.changeTextViewsColors(context, sv_psuedocode, textViews, integers);
//                    }
//                    UtilUI.highlightViews(context, bst.sequence.views,
//                            bst.sequence.animationStates.get(curSeqNo).highlightIndexes);
//                }
//            });
//        }
    }
}