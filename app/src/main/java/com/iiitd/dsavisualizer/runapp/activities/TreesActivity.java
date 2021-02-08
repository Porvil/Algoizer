package com.iiitd.dsavisualizer.runapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.constants.AppSettings;
import com.iiitd.dsavisualizer.datastructures.trees.avl.AVLActivity;
import com.iiitd.dsavisualizer.datastructures.trees.bst.BSTActivity;
import com.iiitd.dsavisualizer.runapp.others.ActivityItemData;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class TreesActivity extends AppCompatActivity {

    Context context;
    LinearLayout linearLayout;

    ActivityItemData[] activityItemData = new ActivityItemData[]{
            new ActivityItemData(BSTActivity.class.getName(), "BST", R.drawable.ic_bst),
            new ActivityItemData(AVLActivity.class.getName(), "AVL", R.drawable.ic_avl)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_trees);
        context = this;

        linearLayout = findViewById(R.id.ll_parent);

        int width = (int) UtilUI.dpToPx(context, AppSettings.ACTIVITY_ITEM_WIDTH);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,
                ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0; i< activityItemData.length; i++){
            final ActivityItemData activityItemData = this.activityItemData[i];
            View view = getLayoutInflater().inflate(R.layout.layout_sortingitem, null);
            ImageView imageView = view.findViewById(R.id.iv_sorticon);
            TextView textView = view.findViewById(R.id.tv_sortname);

            imageView.setImageDrawable(ContextCompat.getDrawable(context, activityItemData.drawable));
            textView.setText(activityItemData.text);

            layoutParams.setMargins(5,5,5,5);
            view.setLayoutParams(layoutParams);

            linearLayout.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Class<?> aClass = Class.forName(activityItemData.activityClassName);
                        startActivity(new Intent(context, aClass));
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}