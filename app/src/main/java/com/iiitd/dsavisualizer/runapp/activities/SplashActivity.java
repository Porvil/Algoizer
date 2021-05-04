package com.iiitd.dsavisualizer.runapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class SplashActivity extends AppCompatActivity {

    Context context;
    ImageView iv_splashimage;
    FrameLayout fl_splash;
    TextView tv_splash_left;
    TextView tv_splash_centre;
    TextView tv_splash_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilUI.setWindowSettings(getWindow());
        setContentView(R.layout.activity_splash);
        context = this;

        iv_splashimage = findViewById(R.id.iv_splashimage);
        fl_splash = findViewById(R.id.fl_splash);
        tv_splash_left = findViewById(R.id.tv_splash_left);
        tv_splash_centre = findViewById(R.id.tv_splash_centre);
        tv_splash_right = findViewById(R.id.tv_splash_right);

        iv_splashimage.post(new Runnable() {
            @Override
            public void run() {
                fl_splash.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int translateDistance = 2*fl_splash.getWidth()/5;
                        int duration = AppSettings.SPLASH_TIME;

                        // 0.1 to 1.9s
                        ViewAnimator
                                .animate(tv_splash_left)
                                .translationX(translateDistance)
                                .alpha(1, 0)
                                .andAnimate(tv_splash_right)
                                .translationX(-translateDistance)
                                .alpha(1, 0)
                                .andAnimate(iv_splashimage)
                                .newsPaper()
                                .fadeIn()
                                .duration(duration)
                                .start();

                        // 1s to 2.8s
                        ViewAnimator
                                .animate(tv_splash_centre)
                                .alpha(0, 1)
                                .startDelay(duration/2)
                                .duration(duration)
                                .start()
                                .onStop(new AnimationListener.Stop() {
                                    @Override
                                    public void onStop() {
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // 2.8 to 3s
                                                UtilUI.startActivity(context, HomeActivity.class);
                                                finish();
                                            }
                                        }, AppSettings.SPLASH_TIME_DELAY_START);
                                    }
                                });
                    }
                }, AppSettings.SPLASH_TIME_DELAY_START/2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Back functionality removed from Splash Activity to prevent exceptions
    }

}