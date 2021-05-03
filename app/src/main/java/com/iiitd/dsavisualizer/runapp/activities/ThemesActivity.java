package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class ThemesActivity extends AppCompatActivity {

    FrameLayout fl_currenttheme;
    ImageButton btn_back;
    Button btn_theme_blue;            // 1
    Button btn_theme_purple;          // 2
    Button btn_theme_green;           // 3
    Button btn_theme_orange;          // 4
    Button btn_theme_brown;           // 5
    Button btn_theme_pink;            // 6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        fl_currenttheme = findViewById(R.id.fl_currenttheme);
        btn_back = findViewById(R.id.btn_back);
        btn_theme_blue = findViewById(R.id.btn_theme_blue);
        btn_theme_purple = findViewById(R.id.btn_theme_purple);
        btn_theme_green = findViewById(R.id.btn_theme_green);
        btn_theme_orange = findViewById(R.id.btn_theme_orange);
        btn_theme_brown = findViewById(R.id.btn_theme_brown);
        btn_theme_pink = findViewById(R.id.btn_theme_pink);

        initToolTipTexts();

        fl_currenttheme.setBackgroundColor(UtilUI.getCurrentThemeColor(this, R.attr.base));

        btn_theme_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 1);
                recreate();
            }
        });

        btn_theme_purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 2);
                recreate();
            }
        });

        btn_theme_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 3);
                recreate();
            }
        });

        btn_theme_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 4);
                recreate();
            }
        });

        btn_theme_brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 5);
                recreate();
            }
        });

        btn_theme_pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilUI.changeCurrentAppTheme(getApplicationContext(), 6);
                recreate();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    protected void initToolTipTexts(){
        TooltipCompat.setTooltipText(btn_back, "Go Back");
    }

}