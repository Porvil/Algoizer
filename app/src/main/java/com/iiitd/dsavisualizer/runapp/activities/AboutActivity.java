package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class AboutActivity extends AppCompatActivity {

    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_about);

        btn_back = findViewById(R.id.btn_back);

        initToolTipTexts();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void initToolTipTexts(){
        TooltipCompat.setTooltipText(btn_back, "Go Back");
    }

}