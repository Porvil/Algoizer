package com.iiitd.dsavisualizer.runapp.others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

public class CustomCanvas {

    public Context context;
    public Canvas canvasGraph;
    public Canvas canvasGrid;
    public Bitmap bitmapGraph;
    public Bitmap bitmapGrid;
    public ImageView imageViewGraph;
    public ImageView imageViewGrid;

    public CustomCanvas(Context context, ImageView imageViewGraph, ImageView imageViewGrid) {
        this.context = context;
        this.imageViewGraph = imageViewGraph;
        this.imageViewGrid = imageViewGrid;

        int width = imageViewGraph.getWidth();
        int height = imageViewGraph.getHeight();

        System.out.println("Canvas = " + width + "x" + height);

        this.bitmapGraph = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageViewGraph.setImageBitmap(bitmapGraph);
        this.canvasGraph = new Canvas(bitmapGraph);

        this.bitmapGrid = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageViewGrid.setImageBitmap(bitmapGrid);
        this.canvasGrid = new Canvas(bitmapGrid);
    }

}
