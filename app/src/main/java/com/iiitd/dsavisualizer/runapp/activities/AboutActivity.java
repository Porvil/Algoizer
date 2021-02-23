package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}