package com.iiitd.dsavisualizer.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.iiitd.dsavisualizer.R;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        final ConstraintLayout constraintLayout = findViewById(R.id.cltest);
        constraintLayout.post(new Runnable() {
            @Override
            public void run() {
                int height = constraintLayout.getHeight();
                int width = constraintLayout.getWidth();

                System.out.println(width + " X " + height);
            }
        });

    }
}