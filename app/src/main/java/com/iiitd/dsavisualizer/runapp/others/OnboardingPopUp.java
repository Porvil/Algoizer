package com.iiitd.dsavisualizer.runapp.others;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.ArrayList;
import java.util.Collections;

public class OnboardingPopUp {

    Context context;
    LayoutInflater inflater;

    PopupWindow popupwindow;
    View parent;
    View popUpView;

    int width;
    int height;
    final int MIN_WIDTH = 200;
    final int MIN_HEIGHT = 200;

    final int MINIMIZE_ICON = R.drawable.ic_baseline_remove_24;
    final int MAXIMIZE_ICON = R.drawable.ic_baseline_open_in_full_24;

    ViewPager mViewPager;
    Button btn_onboarding_back;
    Button btn_onboarding_skip;
    Button btn_onboarding_next;
    CheckBox cb_onboarding_remember;


    // images array
    int[] images = {R.drawable.ic_avl,
            R.drawable.ic_bst,
            R.drawable.ic_graphs};
    int size;

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    String id;

    public OnboardingPopUp(Context _context, int _width, int _height, View _parent, String _id){
        this.context = _context;
        this.width = _width;
        this.height = _height;
        this.parent = _parent;
        this.id = _id;

        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.popUpView = inflater.inflate(R.layout.layout_onboarding, null);

        // Initializing the ViewPager Object
        mViewPager = popUpView.findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(context, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        size = mViewPager.getAdapter().getCount();
        System.out.println("size = " + size);

        this.btn_onboarding_back = popUpView.findViewById(R.id.btn_onboarding_back);
        this.btn_onboarding_skip = popUpView.findViewById(R.id.btn_onboarding_skip);
        this.btn_onboarding_next = popUpView.findViewById(R.id.btn_onboarding_next);
        this.cb_onboarding_remember = popUpView.findViewById(R.id.cb_onboarding_remember);

        updateState(mViewPager.getCurrentItem());
        boolean tutorialState = UtilUI.getTutorialState(context, AppSettings.GRAPH_KEY);
        cb_onboarding_remember.setChecked(tutorialState);

        btn_onboarding_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SAVE STATE
                dismiss();
            }
        });

        btn_onboarding_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });

        btn_onboarding_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem()+1 == size){
                    dismiss();
                    return;
                }

                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("position = " + position);

                updateState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        popupwindow = new PopupWindow(popUpView, width, height, false);
    }

    private void updateState(int position){
        // last item
        if(position+1 == size){
            btn_onboarding_next.setText("Finish");
        }
        else{
            btn_onboarding_next.setText("Next");
        }

        if(position == 0){
            btn_onboarding_back.setVisibility(View.INVISIBLE);
        }
        else {
            btn_onboarding_back.setVisibility(View.VISIBLE);
        }
    }

    // Shows the popUpWindow if not already showing
    public void show(){
        if(popupwindow != null && !popupwindow.isShowing()){
            popupwindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    // Reset icons and resets the state of popUp checkboxes
    private void dismiss(){
        if(popupwindow != null){
            UtilUI.setTutorialState(context, id, cb_onboarding_remember.isChecked());
            popupwindow.dismiss();
        }
    }
}
