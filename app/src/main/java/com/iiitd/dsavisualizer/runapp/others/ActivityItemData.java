package com.iiitd.dsavisualizer.runapp.others;

// Contains activity class name, display text and drawable [ used by main activities ]
public class ActivityItemData {
    public String activityClassName;
    public String text;
    public int drawable;

    public ActivityItemData(String activityClassName, String text, int drawable) {
        this.activityClassName = activityClassName;
        this.text = text;
        this.drawable = drawable;
    }

}