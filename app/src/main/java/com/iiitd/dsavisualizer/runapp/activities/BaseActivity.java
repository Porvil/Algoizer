package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.runapp.others.OnBoardingPopUp;
import com.iiitd.dsavisualizer.utility.UtilUI;

public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    public LayoutInflater layoutInflater;

    public DrawerLayout dl_main;
    public ViewStub vs_main;
    public ViewStub vs_menu_left;
    public ViewStub vs_menu_right;
    public View v_main;
    public View v_menu_left;
    public View v_menu_right;

    public int LAYOUT_MAIN;
    public int LAYOUT_LEFT;
    public int LAYOUT_RIGHT;
    public String ONBOARDING_KEY;
    public boolean isConfigured = false;

    /** Subclasses are obligated to call this before calling super.onCreate() */
    protected void configure(int LAYOUT_MAIN, int LAYOUT_LEFT, int LAYOUT_RIGHT, String ONBOARDING_KEY) {
        this.LAYOUT_MAIN =  LAYOUT_MAIN;
        this.LAYOUT_LEFT =  LAYOUT_LEFT;
        this.LAYOUT_RIGHT = LAYOUT_RIGHT;
        this.ONBOARDING_KEY = ONBOARDING_KEY;

        isConfigured = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!isConfigured) {
            throw new IllegalStateException("configure() not called prior to onCreate()");
        }

        int theme = UtilUI.getCurrentAppTheme(getApplicationContext());
        setTheme(theme);

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_base);

        context = this;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dl_main = findViewById(R.id.dl_main);
        vs_main = findViewById(R.id.vs_main);
        vs_menu_left = findViewById(R.id.vs_menu_left);
        vs_menu_right = findViewById(R.id.vs_menu_right);

        vs_main.setLayoutResource(LAYOUT_MAIN);
        vs_menu_left.setLayoutResource(LAYOUT_LEFT);
        vs_menu_right.setLayoutResource(LAYOUT_RIGHT);
//        v_main = vs_main.inflate();
//        v_menu_right = vs_menu_right.inflate();
//        v_menu_left = vs_menu_left.inflate();
    }

    // id = 0 => both, 1 => left, 2 => right
    public boolean isDrawerOpen(int id){
        if(id == 0){
            return (dl_main.isDrawerOpen(GravityCompat.START) || dl_main.isDrawerOpen(GravityCompat.END));
        }
        else if (id == 1) {
            return dl_main.isDrawerOpen(GravityCompat.START);
        }
        else if (id == 2) {
            return dl_main.isDrawerOpen(GravityCompat.END);
        }

        return false;
    }

    // id = 0 => both, 1 => left, 2 => right
    public void openDrawer(int id){
        if(id == 0){
            dl_main.openDrawer(GravityCompat.START);
            dl_main.openDrawer(GravityCompat.END);
        }
        else if (id == 1) {
            dl_main.openDrawer(GravityCompat.START);
        }
        else if (id == 2) {
            dl_main.openDrawer(GravityCompat.END);
        }
    }

    // id = 0 => both, 1 => left, 2 => right
    public void closeDrawer(int id){
        if(id == 0){
            dl_main.closeDrawer(GravityCompat.START);
            dl_main.closeDrawer(GravityCompat.END);
        }
        else if (id == 1) {
            dl_main.closeDrawer(GravityCompat.START);
        }
        else if (id == 2) {
            dl_main.closeDrawer(GravityCompat.END);
        }
    }

    // must be called only after v_main is laid out
    public OnBoardingPopUp showOnBoarding(){
        OnBoardingPopUp onBoardingPopUp = OnBoardingPopUp.getInstance(context,
                v_main.getWidth(), v_main.getHeight(),
                v_main, ONBOARDING_KEY);
        onBoardingPopUp.show();
        return onBoardingPopUp;
    }

    @Override
    public void onBackPressed() {
        if (dl_main.isDrawerOpen(GravityCompat.START) || dl_main.isDrawerOpen(GravityCompat.END)){
            dl_main.closeDrawer(GravityCompat.START);
            dl_main.closeDrawer(GravityCompat.END);
        }
        else{
            back();
        }
    }

    protected abstract void back();

}