package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Pair;
import android.util.TypedValue;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

import java.util.ArrayList;
import java.util.Map;

public class Board {

    private final int topAngle = 45;// in mm
    private final int bottomAngle = 45;// in mm
    private final int nodeSize = 10;// in mm
    private final float circleRatio = 0.66f;
    private final float edgeArrowRatio = 0.24f;
    private final float nodeRadius;
    Context context;

    public float X;//width
    public float Y;//height
    public int xCount;//no of columns
    public int yCount;//no of rows
    public float xSize;//column width
    public float ySize;//row height
    public Data[][] data;
    public int maxVertices;

    private final Paint paintGrid;
    private final Paint paintGridCoordinates;
    private final Paint paintVertex;
    private final Paint paintEdge;
    private final Paint paintEdgeArrows;
    private final Paint paintText;
    private final int textSizeCoordinates;
    private final int textSize;
    private final int edgeWidth;
    private final int edgeArrowWidth;
    private final int arrowLength;

    CustomCanvas customCanvas;

    public Board(Context context, CustomCanvas customCanvas) {
        this.context = context;
        this.X = customCanvas.imageViewGraph.getWidth();
        this.Y = customCanvas.imageViewGraph.getHeight();
        this.customCanvas = customCanvas;

        // px = 1mm
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1,
                context.getResources().getDisplayMetrics());
        float cm = px * nodeSize;
        float x = (X / cm);
        float y = (Y / cm);

        xCount = (int) Math.ceil(x);
        yCount = (int) Math.ceil(y);

        xSize = (this.X / xCount);
        ySize = (this.Y / yCount);
        maxVertices = yCount * xCount;

        System.out.println("xSize = " + xSize + " | " + " ySize = " + ySize);
        System.out.println("no of rows = " + yCount + " | " + "no of columns = " + xCount);

        float minSide = Math.min(xSize, ySize);
        this.nodeRadius = ( minSide * circleRatio) / 2;
        this.arrowLength = (int) (( minSide * edgeArrowRatio) / 2);

        this.data = new Data[yCount][xCount];
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                data[r][c] = new Data();
            }
        }

        this.textSizeCoordinates = context.getResources().getDimensionPixelSize(R.dimen.coordinatesText);
        this.textSize = context.getResources().getDimensionPixelSize(R.dimen.nodeText);
        this.edgeWidth = context.getResources().getDimensionPixelSize(R.dimen.edgeWidth);
        this.edgeArrowWidth = context.getResources().getDimensionPixelSize(R.dimen.edgeArrowWidth);
//        this.arrowLength = 50;

        this.paintGrid = new Paint();
        this.paintGrid.setColor(context.getResources().getColor(R.color.mainColorDone));

        this.paintGridCoordinates = new Paint();
        this.paintGridCoordinates.setTextAlign(Paint.Align.RIGHT);
        this.paintGridCoordinates.setTextSize(textSizeCoordinates);
        this.paintGridCoordinates.setColor(Color.BLACK);

        this.paintText = new Paint();
        this.paintText.setTextAlign(Paint.Align.CENTER);
        this.paintText.setTextSize(textSize);
        this.paintText.setColor(Color.WHITE);
        this.paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        this.paintVertex = new Paint();
        this.paintVertex.setColor(context.getResources().getColor(R.color.mainColor));

        this.paintEdge = new Paint();
        this.paintEdge.setColor(context.getResources().getColor(R.color.mainColorDarkerShade));
        this.paintEdge.setStrokeWidth(edgeWidth);

        this.paintEdgeArrows = new Paint();
        this.paintEdgeArrows.setColor(context.getResources().getColor(R.color.mainColorDarkerShade));
        this.paintEdgeArrows.setStrokeWidth(edgeArrowWidth);

        // Draw Grid on Grid ImageView
        drawGrid();
    }

    private void drawGrid() {
        Rect rect = new Rect();

        for(int i=0; i<xCount+1; i++){
            int left = (int) (i*xSize);
            int right = left + 1;
            int top = 0;
            int bottom = (int) Y;
            rect.set(left, top, right, bottom);
            customCanvas.canvasGrid.drawRect(rect, paintGrid);
        }

        for(int i=0; i<yCount+1; i++){
            int top = (int) (i*ySize);
            int bottom = top + 1;
            int left = 0;
            int right = (int) X;
            rect.set(left, top, right, bottom);
            customCanvas.canvasGrid.drawRect(rect, paintGrid);
        }

        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                String text = "(" + r + "," + c + ")";
                Rect rect1 = getRect(r, c);
                int x = (int) (rect1.left + (rect1.width() * 0.95f));
                int y = (int) (rect1.top + (rect1.height() * 0.95f));
                Rect rectText = new Rect();
                paintGridCoordinates.getTextBounds(text, 0, text.length(), rectText);
                customCanvas.canvasCoordinates.drawText(text, x, y , paintGridCoordinates);
            }
        }

    }

    // Re-Draws the complete graphOld
    public void update(Graph graph){

        System.out.println("REDRAWING CANVAS");
        // clears canvas
        customCanvas.canvasGraph.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // Nodes
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(data[r][c].state){
                    Rect rect = getRect(r, c);
                    drawNode(rect, data[r][c].data);
                }
            }
        }

        //Edges
        for(Map.Entry<Integer, ArrayList<Edge>> vertex : graph.map.entrySet() ){

            for (Edge edge : vertex.getValue()) {
                System.out.println(vertex.getKey() + ":" + edge);

                int[] vertex1 = getCoordinates(vertex.getKey());
                int[] vertex2 = getCoordinates(edge.des);

                Rect rect1 = getRect(vertex1[0], vertex1[1]);
                Rect rect2 = getRect(vertex2[0], vertex2[1]);

                drawEdge(rect1, rect2);
            }
        }
    }

    // Draws a single Node
    public void drawNode(Rect rect, int name) {
        int x = rect.centerX();
        int y = rect.centerY();

        float radius = getRadius(rect);
        String text = String.valueOf(name);

        customCanvas.canvasGraph.drawCircle(x, y, radius, paintVertex);

        Rect rectText = new Rect();
        paintText.getTextBounds(text, 0, text.length(), rectText);
        customCanvas.canvasGraph.drawText(text, x, y - (paintText.descent() + paintText.ascent()) / 2, paintText);
    }

    // Draws a single EdgeOld
    public void drawEdge(Rect rect1, Rect rect2) {
        double[] lineCoordinates = getLineCoordinates(rect1, rect2);

        float lx1 = (float) lineCoordinates[0];
        float ly1 = (float) lineCoordinates[1];

        float lx2 = (float) lineCoordinates[2];
        float ly2 = (float) lineCoordinates[3];

        double distance = distance(lx1, ly1, lx2, ly2);
//        System.out.println("distance = " + distance);


        customCanvas.canvasGraph.drawLine(lx1, ly1, lx2, ly2, paintEdge);
        arrow12(lx1, ly1, lx2, ly2);
//        arrow21(lx1, ly1, lx2, ly2);
    }

    public void arrow12(float x, float y, float x1, float y1) {
        double degree = calculateDegree(x, x1, y, y1);

        float endX1 = (float) (x1 + ((arrowLength) * Math.cos(Math.toRadians((degree-topAngle)+90))));
        float endY1 = (float) (y1 + ((arrowLength) * Math.sin(Math.toRadians(((degree-topAngle)+90)))));

        float endX2 = (float) (x1 + ((arrowLength) * Math.cos(Math.toRadians((degree-bottomAngle)+180))));
        float endY2 = (float) (y1 + ((arrowLength) * Math.sin(Math.toRadians(((degree-bottomAngle)+180)))));

        customCanvas.canvasGraph.drawLine(x1, y1, endX1, endY1, paintEdgeArrows);
        customCanvas.canvasGraph.drawLine(x1, y1, endX2, endY2, paintEdgeArrows);
    }

    public void arrow21(float x, float y, float x1, float y1) {

        double degree1 = calculateDegree(x1, x, y1, y);
        float endX11 = (float) (x + ((arrowLength) * Math.cos(Math.toRadians((degree1-topAngle)+90))));
        float endY11 = (float) (y + ((arrowLength) * Math.sin(Math.toRadians(((degree1-topAngle)+90)))));

        float endX22 = (float) (x + ((arrowLength) * Math.cos(Math.toRadians((degree1-bottomAngle)+180))));
        float endY22 = (float) (y + ((arrowLength) * Math.sin(Math.toRadians(((degree1-bottomAngle)+180)))));

        customCanvas.canvasGraph.drawLine(x, y, endX11, endY11, paintEdgeArrows);
        customCanvas.canvasGraph.drawLine(x, y, endX22, endY22, paintEdgeArrows);
    }

    // Adds VertexOld element to grid element and calls drawNode
    public void addVertex(float xAxisPos, float yAxisPos, int name) {
        // Row and Col of the vertexOld
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        // Change its state and add vertexOld reference
        data[row][col].state = true;
        data[row][col].data = name;
//        data[row][col].vertexOld = vertexOld;

        Rect rect = getRect(row, col);
        drawNode(rect, data[row][col].data);
    }

    // Adds VertexOld element to grid element and calls drawNode
    public void addVertex(int row, int col, int name) {
        // Change its state and add vertexOld reference
        data[row][col].state = true;
        data[row][col].data = name;
//        data[row][col].vertexOld = vertexOld;

        Rect rect = getRect(row, col);
        drawNode(rect, data[row][col].data);
    }

    // Adds VertexOld element to grid element and calls drawNode
    public void removeVertex(int row, int col) {
        // Change its state and add vertexOld reference
        data[row][col].state = false;
        data[row][col].data = -1;
//        data[row][col].vertexOld = vertexOld;

    }

    // Returns state of the grid element, whether it is being used or not
    public boolean getState(float xAxisPos, float yAxisPos){
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        return data[row][col].state;
    }

    // Returns state of the grid element, whether it is being used or not
    public boolean getState(int row, int col){
        return data[row][col].state;
    }

    public int[] getCoordinates(int key){
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(data[r][c].data == key){
                    return new int[]{r, c};
                }
            }
        }

        return null;
    }

    // Returns Rect for given grid[row][col]
    public Rect getRect(float row, float col) {
        int c = (int) col;
        int r = (int) row;

        int left = (int) (c * xSize);
        int top = (int) (r * ySize);
        int right = (int) (left + xSize);
        int bottom = (int) (top + ySize);

        return new Rect(left, top, right, bottom);
    }

    // Returns radius for the node
    private float getRadius(Rect rect){
        int width = rect.width();
        int height = rect.height();

        System.out.println("rect = " + width + "x" + height);
        float diameter = Math.min(width, height) * circleRatio;
        float radius = diameter / 2;

        return radius;
    }

    // Returns double[4] = {startX, startY, endX, endY} for edge Line
    public double[] getLineCoordinates(Rect rect1, Rect rect2){
        double x1 = rect1.centerX();
        double y1 = rect1.centerY();

        double x2 = rect2.centerX();
        double y2 = rect2.centerY();

        double r1 = getRadius(rect1);
        double r2 = getRadius(rect2);

        double u = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));

        double a1 = x1 + (((x2-x1) * r1) / u);
        double b1 = y1 + (((y2-y1) * r1) / u);

        double a2 = x2 - (((x2-x1) * r2) / u);
        double b2 = y2 - (((y2-y1) * r2) / u);

        return new double[]{a1,b1,a2,b2};
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public double calculateDegree(float x1, float x2, float y1, float y2) {
        float startRadians = (float) Math.atan((y2 - y1) / (x2 - x1));
        startRadians += ((x2 >= x1) ? 90 : -90) * Math.PI / 180;
        return Math.toDegrees(startRadians);
    }
}
