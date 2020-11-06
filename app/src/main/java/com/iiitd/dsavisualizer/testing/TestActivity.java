package com.iiitd.dsavisualizer.testing;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iiitd.dsavisualizer.R;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    boolean isPseudocode = true;
    Context context;
    ArrayList<TableRow> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        context = this;
        final TableLayout tableLayout = findViewById(R.id.ll_anim);
        ImageButton btn_code = findViewById(R.id.btn_code);
        final ScrollView sv_psuedocode = findViewById(R.id.sv_psuedocode);

        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                System.out.println(tableLayout.getWidth());
                System.out.println(tableLayout.getHeight());
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
                            System.out.println(tableLayout.getWidth());
                            System.out.println(tableLayout.getHeight());
                        }
                    }, 0);
                }
                else {
                    sv_psuedocode.setVisibility(View.VISIBLE);
                    sv_psuedocode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(tableLayout.getWidth());
                            System.out.println(tableLayout.getHeight());
                        }
                    }, 0);

                }
                isPseudocode = !isPseudocode;
            }
        });


        ImageButton imageButton = findViewById(R.id.btn_info);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, (int) 100, 1);
//                LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View myView = vi.inflate(R.layout.element_quick_sort, null);
//                myView.setLayoutParams(layoutParams);
//                myView.setPadding(5,5,5,5);
//                TextView tv = myView.findViewById(R.id.tv_elementvalue);
//                tv.setText("Hi");
//                tv.setTextColor(Color.WHITE);
////                tv.getLayoutParams().height = (int) (height * h * .75f);
//                tv.setBackground(UtilUI.getDrawable(context, AppSettings.ROUNDED_RECT_ELEMENT));
//                tableLayout.addView(myView);


                int height = tableLayout.getHeight();
                int h = height / 7;
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, h, 1);
                TableRow tr = new TableRow(context);
                tr.setLayoutParams(layoutParams);

                /* Create a Button to be the row-content. */
                Button b = new Button(context);
                b.setText("Dynamic Button");
                b.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                /* Add Button to row. */
                tr.addView(b);

                Button b2 = new Button(context);
                b2.setText("PRO");
                b2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                /* Add Button to row. */
                tr.addView(b2);

                tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));








            }
        });

        int height = tableLayout.getHeight();
        int h = height / 7;
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, h, 1);
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int r=0;r<7;r++){
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(layoutParams);

            for(int c=0;c<15;c++){
                View myView = vi.inflate(R.layout.element_bst, null);
                myView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT,1));
//                myView.setLayoutParams(new LinearLayout.LayoutParams(0, (int) 100, 1));
                myView.setPadding(5,5,5,5);
                TextView tv = myView.findViewById(R.id.tv_elementvalue);
                tv.setText("Hi");
                tv.setTextColor(Color.WHITE);
                tableRow.addView(myView);
//                Button b2 = new Button(context);
//                b2.setText("PRO");
//                b2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT,1));
//                /* Add Button to row. */
//                tableRow.addView(b2);

            }
            arrayList.add(tableRow);
//            tableLayout.addView(tableRow, layoutParams);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,0,1));

        }


    }
}