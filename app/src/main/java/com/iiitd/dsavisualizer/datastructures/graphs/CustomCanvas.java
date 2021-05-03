package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.FrameLayout;
import android.widget.ImageView;

// CustomCanvas class holds different canvases used by Graph Activity
public class CustomCanvas {

    public Context context;
    public Canvas canvasGraph;
    public Canvas canvasGrid;
    public Canvas canvasCoordinates;
    public Canvas canvasAnimation;
    public Bitmap bitmapGraph;
    public Bitmap bitmapGrid;
    public Bitmap bitmapCoordinates;
    public Bitmap bitmapAnimation;
    public ImageView imageViewGraph;
    public ImageView imageViewGrid;
    public ImageView imageViewCoordinates;
    public ImageView imageViewAnimation;

    FrameLayout frameLayout;

    // frameLayout must have been laid out already
    public CustomCanvas(Context context, FrameLayout frameLayout, ImageView imageViewGraph, ImageView imageViewGrid, ImageView imageViewAnimation, ImageView imageViewCoordinates) {
        this.context = context;
        this.frameLayout = frameLayout;
        this.imageViewGraph = imageViewGraph;
        this.imageViewGrid = imageViewGrid;
        this.imageViewCoordinates = imageViewCoordinates;
        this.imageViewAnimation = imageViewAnimation;

        int width = frameLayout.getWidth();
        int height = frameLayout.getHeight();

        System.out.println("FRAME LAYOUT = " + width + "x" + height);

        this.bitmapGraph = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageViewGraph.setImageBitmap(bitmapGraph);
        this.canvasGraph = new Canvas(bitmapGraph);

        this.bitmapGrid = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageViewGrid.setImageBitmap(bitmapGrid);
        this.canvasGrid = new Canvas(bitmapGrid);

        this.bitmapCoordinates = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageViewCoordinates.setImageBitmap(bitmapCoordinates);
        this.canvasCoordinates = new Canvas(bitmapCoordinates);

        this.bitmapAnimation = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.imageViewAnimation.setImageBitmap(bitmapAnimation);
        this.canvasAnimation = new Canvas(bitmapAnimation);
    }

}