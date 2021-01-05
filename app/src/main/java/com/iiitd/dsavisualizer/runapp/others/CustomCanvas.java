package com.iiitd.dsavisualizer.runapp.others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

public class CustomCanvas {

    public Context context;
    public Canvas canvas;
    public Bitmap bitmap;
    public ImageView imageView;

    public CustomCanvas(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;

        int width = imageView.getWidth();
        int height = imageView.getHeight();

        System.out.println("Canvas = " + width + "x" +height);

        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageView.setImageBitmap(bitmap);
        this.canvas = new Canvas(bitmap);
    }

}
