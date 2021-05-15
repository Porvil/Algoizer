package com.iiitd.dsavisualizer.constants;

import android.Manifest;
import android.os.Environment;

import com.iiitd.dsavisualizer.R;

// Constants used in the Application
public class AppSettings {

    // Permissions used by the Application
    public static String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    // String Constants
    public static final String REPORT_BUG_EMAIL = "algoizeriiitd@gmail.com";
    public static final String DIRECTORY = "/Algoizer";
    public static final String SEPARATOR = "/";
    public static final String SHARED_PREFERENCES = "Shared Preferences";
    public static final String CURRENT_THEME_KEY = "Theme";
    public static final String SORTING_KEY = "Sorting";
    public static final String TREE_KEY = "Tree";
    public static final String GRAPH_KEY = "Graph";
    public static final String GRAPH_SAVEFILE_EXTENSION = ".graph";

    // Int Constants
    public static final int THEMES_REQ_CODE = 264;
    public static final int GRAPH_PICKFILE_RESULT_CODE = 1223;
    public static final int PERMISSION_ALL_EXPORT = 1000;
    public static final int PERMISSION_ALL_IMPORT = 1001;
    public static final int SPLASH_TIME = 1800;
    public static final int SPLASH_TIME_DELAY_START = 200;
    public static final int DEFAULT_ANIM_SPEED = 1500;    // Range 500ms to 2500ms
    public static final int DEFAULT_ANIM_DURATION = 750;  // Half of DEFAULT_ANIM_SPEED
    public static final int SORTING_ELEMENT_BOUND = 20;
    public static final int TEXT_SMALL = 12;
    public static final int TEXT_MEDIUM = 14;
    public static final int ACTIVITY_ITEM_WIDTH = 250;

    // Drawable ID's [ used in whole Application ]
    public static final int PLAY_BUTTON = R.drawable.ic_play;
    public static final int PAUSE_BUTTON = R.drawable.ic_pause;
    public static final int ROUNDED_RECT_ELEMENT = R.drawable.rounded_rect_sorting_element_normal;

    public static String getExternalStoragePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}