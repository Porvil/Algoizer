package com.iiitd.dsavisualizer.runapp.others;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;

import androidx.viewpager.widget.ViewPager;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class OnBoardingPopUp {

    Context context;
    LayoutInflater inflater;

    PopupWindow popupwindow;
    View parent;
    View popUpView;

    int width;
    int height;
    int size;
    String id;
    int[] onBoardingImages;

    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    Button btn_onboarding_back;
    Button btn_onboarding_skip;
    Button btn_onboarding_next;
    CheckBox cb_onboarding_remember;

    public static OnBoardingPopUp getInstance(Context _context, int _width, int _height, View _parent, String _id){
        return new OnBoardingPopUp(_context, _width, _height, _parent, _id);
    }

    private OnBoardingPopUp(Context _context, int _width, int _height, View _parent, String _id){
        this.context = _context;
        this.width = _width;
        this.height = _height;
        this.parent = _parent;
        this.id = _id;

        switch (id){
            case AppSettings.SORTING_KEY:
                onBoardingImages = OnBoardingInfo.sortingOnBoarding;
                break;
            case AppSettings.TREE_KEY:
                onBoardingImages = OnBoardingInfo.treeOnBoarding;
                break;
            case AppSettings.GRAPH_KEY:
                onBoardingImages = OnBoardingInfo.graphOnBoarding;
                break;
        }

        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.popUpView = inflater.inflate(R.layout.layout_onboarding, null);

        this.mViewPager = popUpView.findViewById(R.id.viewPagerMain);
        this.mViewPagerAdapter = new ViewPagerAdapter(context, onBoardingImages);
        this.mViewPager.setAdapter(mViewPagerAdapter);

        this.size = mViewPager.getAdapter().getCount();

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
                if(mViewPager.getCurrentItem() + 1 == size){
                    dismiss();
                    return;
                }

                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                updateState(position);
            }

        });

        popupwindow = new PopupWindow(popUpView, width, height, false);
    }

    private void updateState(int position){

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

    // Dismisses and save state for next time to whether show onBoardingPopUp or not
    private void dismiss(){
        if(popupwindow != null){
            UtilUI.setTutorialState(context, id, cb_onboarding_remember.isChecked());
            popupwindow.dismiss();
        }
    }
}