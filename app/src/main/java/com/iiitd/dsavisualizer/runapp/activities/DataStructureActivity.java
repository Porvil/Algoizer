package com.iiitd.dsavisualizer.runapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.datastructures.trees.avl.AVLActivity;
import com.iiitd.dsavisualizer.datastructures.trees.bst.BSTActivity;

public class DataStructureActivity extends AppCompatActivity {

    Button btn_bst;
    Button btn_avl;
    Button btn_redblack;
    Button btn_splay;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structure);

        context = this;

        btn_bst = findViewById(R.id.btn_bst);
        btn_avl = findViewById(R.id.btn_avl);
        btn_redblack = findViewById(R.id.btn_redblack);
        btn_splay = findViewById(R.id.btn_splay);

        btn_bst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BSTActivity.class));
            }
        });

        btn_avl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AVLActivity.class));
            }
        });

        btn_redblack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(context, RedBlackActivity.class));
            }
        });

        btn_splay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(context, SplaytActivity.class));
            }
        });

    }
}