package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;
import android.util.TypedValue;

import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Board {

    private final float circleRatio = 0.75f;
    Context context;

    public float X;//width
    public float Y;//height
    public int xCount;//no of columns
    public int yCount;//no of rows
    public float xSize;//column width
    public float ySize;//row height
    public Data[][] data;

    CustomCanvas customCanvas;

    public Board(Context context, CustomCanvas customCanvas) {
        this.context = context;
        this.X = customCanvas.imageView.getWidth();
        this.Y = customCanvas.imageView.getHeight();
        this.customCanvas = customCanvas;

        // px = 1mm
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1,
                context.getResources().getDisplayMetrics());
        float cm = px * 10;
        float x = (X / cm);
        float y = (Y / cm);

        xCount = (int) Math.ceil(x);
        yCount = (int) Math.ceil(y);

        xSize = (this.X / xCount);
        ySize = (this.Y / yCount);

        System.out.println("xSize = " + xSize + " | " + " ySize = " + ySize);
        System.out.println("no of rows = " + yCount + " | " + "no of columns = " + xCount);

        this.data = new Data[yCount][xCount];
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                data[r][c] = new Data();
            }
        }
    }

    public void update(Graph graph){

        // Nodes
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(data[r][c].state){
                    Rect rect = getRect(r, c);

                    int x = rect.centerX();
                    int y = rect.centerY();

                    int width = Math.abs(rect.right - rect.left);
                    int height = Math.abs(rect.top - rect.bottom);

                    float diameter = Math.min(width, height) * circleRatio;
                    float radius = diameter / 2;

                    System.out.println(width + " -- " + height + " ---- " + radius);
                    Paint paint = new Paint();
                    paint.setColor(Color.MAGENTA);
                    Paint paint2 = new Paint(Paint.UNDERLINE_TEXT_FLAG);
                    paint2.setTextSize(100);
                    paint2.setTextAlign(Paint.Align.CENTER);
                    customCanvas.canvas.drawCircle(x, y, radius, paint);
                    String text = String.valueOf(data[r][c].vertex.name);
                    System.out.println("tetx " + text);
                    customCanvas.canvas.drawText(text, x, y, paint2);
//                    drawCenter(rect, text);

                }
            }
        }

        //Edges
        for(Map.Entry<Integer, Vertex> vertex : graph.vertices.entrySet() ){

            for (Edge edge : vertex.getValue().edges) {

                System.out.println(vertex.getKey() + " -> " + edge.dest.name);
                System.out.println(vertex.getValue().row + ":" + vertex.getValue().col);
                System.out.println(edge.dest.row + ":" + edge.dest.col);

                Rect rect1 = getRect(vertex.getValue().row, vertex.getValue().col);
                Rect rect2 = getRect(edge.dest.row, edge.dest.col);

                Paint paint1 = new Paint();
                paint1.setColor(Color.BLUE);

                double[] lineCoordinates = getLineCoordinates(rect1, rect2);

                float lx1 = (float) lineCoordinates[0];
                float ly1 = (float) lineCoordinates[1];

                float lx2 = (float) lineCoordinates[2];
                float ly2 = (float) lineCoordinates[3];

                customCanvas.canvas.drawLine(lx1, ly1, lx2, ly2, paint1);
            }
        }
    }

    private void drawCenter(Rect r, String text) {
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        customCanvas.canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        customCanvas.canvas.drawText(text, x, y, paint);
    }

    public Rect getRect(float row, float col) {
        int c = (int) col;
        int r = (int) row;
        System.out.println("touch =---= " + r + " = " + c);
        int left = (int) (c * xSize);
        int top = (int) (r * ySize);
        int right = (int) (left + xSize);
        int bottom = (int) (top + ySize);

        Rect rect = new Rect(left, top, right, bottom);
        return rect;
    }

    public boolean getState(float xAxisPos, float yAxisPos){
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        return data[row][col].state;
    }

    public boolean switchState(float xAxisPos, float yAxisPos){
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        data[row][col].state = !data[row][col].state;

        return data[row][col].state;
    }

    public Rect getBox(float xAxisPos, float yAxisPos) {
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;
        System.out.println("touch === " + row + " = " + col);
        int left = (int) (col * xSize);
        int top = (int) (row * ySize);
        int right = (int) (left + xSize);
        int bottom = (int) (top + ySize);


        data[row][col].state = !data[row][col].state;


        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);

        if (!data[row][col].state) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.GREEN);
        }

        customCanvas.canvas.drawRect(rect, paint);


        ArrayList<Pair<Integer, Integer>> arrayList = new ArrayList<>();
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if (data[r][c].state) {
                    arrayList.add(new Pair<>(r, c));
                }
            }
        }

        for (int i1 = 0; i1 < arrayList.size(); i1++) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (i1 < i2) {
                    Pair<Integer, Integer> pair1 = arrayList.get(i1);
                    Pair<Integer, Integer> pair2 = arrayList.get(i2);

                    Rect rect1 = getRect(pair1.first, pair1.second);
                    Rect rect2 = getRect(pair2.first, pair2.second);

                    Paint paint1 = new Paint();
                    paint1.setColor(Color.BLUE);

                    double[] lineCoordinates = getLineCoordinates(rect1, rect2);

                    float lx1 = (float) lineCoordinates[0];
                    float ly1 = (float) lineCoordinates[1];

                    float lx2 = (float) lineCoordinates[2];
                    float ly2 = (float) lineCoordinates[3];

                    customCanvas.canvas.drawLine(lx1, ly1, lx2, ly2, paint1);
                }
            }
        }

        drawNode(rect);

        return rect;
    }

    public void drawNode(Rect rect) {
        int x = rect.centerX();
        int y = rect.centerY();

        int width = Math.abs(rect.right - rect.left);
        int height = Math.abs(rect.top - rect.bottom);

        float diameter = Math.min(width, height) * circleRatio;
        float radius = diameter / 2;

        System.out.println(width + " -- " + height + " ---- " + radius);
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        customCanvas.canvas.drawCircle(x, y, radius, paint);
    }

    public double[] getLineCoordinates(Rect rect1, Rect rect2){
        double width1 = Math.abs(rect1.right - rect1.left);
        double height1 = Math.abs(rect1.top - rect1.bottom);

        double diameter1 = Math.min(width1, height1) * circleRatio;
        double radius1 = diameter1 / 2;

        double width2 = Math.abs(rect2.right - rect2.left);
        double height2 = Math.abs(rect2.top - rect2.bottom);

        double diameter2 = Math.min(width2, height2) * circleRatio;
        double radius2 = diameter2 / 2;


        double x1 = rect1.centerX();
        double y1 = rect1.centerY();

        double x2 = rect2.centerX();
        double y2 = rect2.centerY();

        double r1 = radius1;
        double r2 = radius2;

        double u = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));

        double a1 = x1 + (((x2-x1) * r1) / u);
        double b1 = y1 + (((y2-y1) * r1) / u);

        double a2 = x2 - (((x2-x1) * r2) / u);
        double b2 = y2 - (((y2-y1) * r2) / u);

        return new double[]{a1,b1,a2,b2};
    }

    public void addVertex(float xAxisPos, float yAxisPos, Vertex vertex) {
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        data[row][col].state = true;
        data[row][col].vertex = vertex;
    }
}
