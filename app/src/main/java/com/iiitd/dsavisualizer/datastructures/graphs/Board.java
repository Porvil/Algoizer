package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

import java.util.Map;

public class Board {

    private final int nodeSize = 10;// in mm
    private final float circleRatio = 0.75f;
    Context context;

    public float X;//width
    public float Y;//height
    public int xCount;//no of columns
    public int yCount;//no of rows
    public float xSize;//column width
    public float ySize;//row height
    public Data[][] data;

    private final Paint paintVertex;
    private final Paint paintEdge;
    private final Paint paintText;
    private final int textSize;
    private final int edgeWidth;

    CustomCanvas customCanvas;

    public Board(Context context, CustomCanvas customCanvas) {
        this.context = context;
        this.X = customCanvas.imageView.getWidth();
        this.Y = customCanvas.imageView.getHeight();
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

        System.out.println("xSize = " + xSize + " | " + " ySize = " + ySize);
        System.out.println("no of rows = " + yCount + " | " + "no of columns = " + xCount);

        this.data = new Data[yCount][xCount];
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                data[r][c] = new Data();
            }
        }

        this.textSize = context.getResources().getDimensionPixelSize(R.dimen.nodeText);
        this.edgeWidth = context.getResources().getDimensionPixelSize(R.dimen.edgeWidth);

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
    }

    // Re-Draws the complete graph
    public void update(Graph graph){
        // Nodes
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(data[r][c].state){
                    Rect rect = getRect(r, c);
                    drawNode(rect, data[r][c].vertex);
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

                drawEdge(rect1, rect2, edge);
            }
        }
    }

    // Draws a single Node
    public void drawNode(Rect rect, Vertex vertex) {
        int x = rect.centerX();
        int y = rect.centerY();

        float radius = getRadius(rect);
        String text = String.valueOf(vertex.name);

        customCanvas.canvas.drawCircle(x, y, radius, paintVertex);

        Rect rectText = new Rect();
        paintText.getTextBounds(text, 0, text.length(), rectText);
        customCanvas.canvas.drawText(text, x, y - (paintText.descent() + paintText.ascent()) / 2, paintText);

    }

    // Draws a single Edge
    public void drawEdge(Rect rect1, Rect rect2, Edge edge) {
        double[] lineCoordinates = getLineCoordinates(rect1, rect2);

        float lx1 = (float) lineCoordinates[0];
        float ly1 = (float) lineCoordinates[1];

        float lx2 = (float) lineCoordinates[2];
        float ly2 = (float) lineCoordinates[3];

        customCanvas.canvas.drawLine(lx1, ly1, lx2, ly2, paintEdge);
    }

    // Adds Vertex element to grid element and calls drawNode
    public void addVertex(float xAxisPos, float yAxisPos, Vertex vertex) {
        // Row and Col of the vertex
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        // Change its state and add vertex reference
        data[row][col].state = true;
        data[row][col].vertex = vertex;

        Rect rect = getRect(row, col);
        drawNode(rect, vertex);
    }

    // Returns state of the grid element, whether it is being used or not
    public boolean getState(float xAxisPos, float yAxisPos){
        int col = (int) xAxisPos;
        int row = (int) yAxisPos;

        return data[row][col].state;
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

}
