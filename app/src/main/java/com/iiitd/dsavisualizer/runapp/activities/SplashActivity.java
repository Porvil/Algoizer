package com.iiitd.dsavisualizer.runapp.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Fade;
import android.transition.Slide;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;

public class SplashActivity extends AppCompatActivity {

    Context context;
    ImageView iv_splashimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_splash);
        context = this;

        iv_splashimage = findViewById(R.id.iv_splashimage);

        iv_splashimage.post(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.animate(iv_splashimage)
                    .duration(AppSettings.LOGO_ANIMATION_TIME)
                    .newsPaper()
                    .fadeIn()
                    .start().onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    startActivity(intent,
                                            ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
                                    finish();
                                }
                            }, AppSettings.SPLASH_TIME);
                        }
                    });
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Back functionality removed from Splash Activity to prevent exceptions [ Also not needed anyways ]
    }

}