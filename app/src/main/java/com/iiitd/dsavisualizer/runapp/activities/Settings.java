package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class Settings extends AppCompatActivity {

    Button btn_theme1;
    Button btn_theme2;
    Button btn_theme3;
    Button btn_theme4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_theme1 = findViewById(R.id.btn_theme1);
        btn_theme2 = findViewById(R.id.btn_theme2);
        btn_theme3 = findViewById(R.id.btn_theme3);
        btn_theme4 = findViewById(R.id.btn_theme4);

        btn_theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 1);
                recreate();
            }
        });

        btn_theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 2);
                recreate();
            }
        });

        btn_theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 3);
                recreate();
            }
        });

        btn_theme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 4);
                recreate();
            }
        });

    }
}