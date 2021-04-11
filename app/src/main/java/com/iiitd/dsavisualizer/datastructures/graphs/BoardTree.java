package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Pair;
import android.widget.ImageView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.util.Collections;
import java.util.Map;

// Used by GraphActivity Class
// Used old Board Functions and variables [ MAY NEED TO UPDATE ]
public class BoardTree {

    Context context;

    // Constants
    private final boolean ANTI_ALIAS = true;       // ANTI-ALIASING is ON
    private final int topAngle = 45;               // in degrees
    private final int bottomAngle = 45;            // in degrees
    private final int nodeSize = 5;                // in mm
    private final float circleRatio = 0.66f;       // in ratio [0,1]
    private final float edgeArrowRatio = 0.24f;    // in ratio [0,1]
    private final float nodeRadius;                // in pixels [Radius of one node]
    private final float coordinatesOffset;         // in ratio [0,1]
    private final int textSizeCoordinates;         // in sp
    private final int textSize;                    // in sp
    private final int edgeWidth;                   // in dp
    private final int edgeArrowWidth;              // in dp
    private final int arrowLength;                 // in pixels

    // Board Variables
    public float X;                                // Width of Board
    public float Y;                                // Height of Board
    public int xCount;                             // No. of columns
    public int yCount;                             // No. of rows
    public float xSize;                            // One Column Width
    public float ySize;                            // One Row Height
    public BoardElement[][] boardElements;         // Contains data about board elements

    // Paint Variables
    private Paint paintGrid;
    private Paint paintGridCoordinates;
    private Paint paintVertex;
    private Paint paintText;

    private Paint paintEdgeTree;
    private Paint paintEdgeBack;
    private Paint paintEdgeCross;
    private Paint paintEdgeForward;

    boolean showTreeEdge;
    boolean showBackEdge;
    boolean showForwardEdge;
    boolean showCrossEdge;

    ImageView iv_graphtree;
    Canvas canvasGraphTree;
    Bitmap bitmapGraphTree;
    GraphTree graphTree;

    public BoardTree(Context context, GraphTree graphTree) {
        this.context = context;
        this.graphTree = graphTree;

        this.xCount = graphTree.noOfCols;
        this.yCount = graphTree.noOfRows;

        // px = 1mm
        float px = UtilUI.mmToPx(context, 1);
        float cm = px * nodeSize;

        xSize = (int) cm;
        ySize = (int) cm;

        this.X = xCount * xSize;
        this.Y = yCount * ySize;

        float minSide = Math.min(xSize, ySize);
        this.nodeRadius = ( minSide * circleRatio) / 2;
        this.arrowLength = (int) (( minSide * edgeArrowRatio) / 2);
        this.coordinatesOffset = 0.95f;

        this.boardElements = new BoardElement[yCount][xCount];
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                boardElements[r][c] = new BoardElement();
            }
        }

        for(Map.Entry<Integer, Pair<Integer, Integer>> entry: graphTree.vertexMap.entrySet()){
            Pair<Integer, Integer> value = entry.getValue();
            addVertex(value.first, value.second, entry.getKey());
        }

        this.textSizeCoordinates = context.getResources().getDimensionPixelSize(R.dimen.coordinatesText);
        this.textSize = context.getResources().getDimensionPixelSize(R.dimen.nodeText);
        this.edgeWidth = context.getResources().getDimensionPixelSize(R.dimen.edgeWidth);
        this.edgeArrowWidth = context.getResources().getDimensionPixelSize(R.dimen.edgeArrowWidth);


        showTreeEdge = true;
        showBackEdge = true;
        showForwardEdge = true;
        showCrossEdge = true;

        // Initializes all Paint Variables
        initPaints();
    }

    // Should be called only after imageView layout has been laid
    public void setImageViewAndCreateCanvas(final ImageView imageView) {
        this.iv_graphtree = imageView;

        bitmapGraphTree = Bitmap.createBitmap(iv_graphtree.getWidth(), iv_graphtree.getHeight(), Bitmap.Config.ARGB_8888);
        iv_graphtree.setImageBitmap(bitmapGraphTree);
        canvasGraphTree = new Canvas(bitmapGraphTree);

        drawGrid();

        drawGraph();
    }

    private void initPaints(){

        int shade = UtilUI.getCurrentThemeColor(context, R.attr.shade);
        int light = UtilUI.getCurrentThemeColor(context, R.attr.light);
        int base = UtilUI.getCurrentThemeColor(context, R.attr.base);
        int medium = UtilUI.getCurrentThemeColor(context, R.attr.medium);
        int dark = UtilUI.getCurrentThemeColor(context, R.attr.dark);
        int white = Color.WHITE;

        this.paintGrid = new Paint();
        this.paintGrid.setColor(light);
        this.paintGrid.setAntiAlias(ANTI_ALIAS);

        this.paintGridCoordinates = new Paint();
        this.paintGridCoordinates.setTextAlign(Paint.Align.RIGHT);
        this.paintGridCoordinates.setTextSize(textSizeCoordinates);
        this.paintGridCoordinates.setColor(light);
        this.paintGridCoordinates.setAntiAlias(ANTI_ALIAS);

        this.paintVertex = new Paint();
        this.paintVertex.setColor(base);
        this.paintVertex.setAlpha(192);
        this.paintVertex.setAntiAlias(ANTI_ALIAS);

        this.paintText = new Paint();
        this.paintText.setTextAlign(Paint.Align.CENTER);
        this.paintText.setTextSize(textSize);
        this.paintText.setColor(white);
        this.paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        this.paintText.setAntiAlias(ANTI_ALIAS);

        this.paintEdgeTree = new Paint();
        this.paintEdgeTree.setColor(context.getResources().getColor(R.color.graph_tree));
        this.paintEdgeTree.setStrokeWidth(edgeWidth);
        this.paintEdgeTree.setAntiAlias(ANTI_ALIAS);

        this.paintEdgeBack = new Paint();
        this.paintEdgeBack.setColor(context.getResources().getColor(R.color.graph_back));
        this.paintEdgeBack.setStrokeWidth(edgeWidth);
        this.paintEdgeBack.setAntiAlias(ANTI_ALIAS);

        this.paintEdgeCross = new Paint();
        this.paintEdgeCross.setColor(context.getResources().getColor(R.color.graph_cross));
        this.paintEdgeCross.setStrokeWidth(edgeWidth);
        this.paintEdgeCross.setAntiAlias(ANTI_ALIAS);

        this.paintEdgeForward = new Paint();
        this.paintEdgeForward.setColor(context.getResources().getColor(R.color.graph_forward));
        this.paintEdgeForward.setStrokeWidth(edgeWidth);
        this.paintEdgeForward.setAntiAlias(ANTI_ALIAS);

    }

    private void drawGrid() {
        Rect rect = new Rect();

        for(int i=0; i<xCount+1; i++){
            int left = (int) (i*xSize);
            int right = left + 1;
            int top = 0;
            int bottom = (int) Y;
            rect.set(left, top, right, bottom);
            canvasGraphTree.drawRect(rect, paintGrid);
        }

        for(int i=0; i<yCount+1; i++){
            int top = (int) (i*ySize);
            int bottom = top + 1;
            int left = 0;
            int right = (int) X;
            rect.set(left, top, right, bottom);
            canvasGraphTree.drawRect(rect, paintGrid);
        }

        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                String text = "(" + r + "," + c + ")";
                Rect rect1 = getRect(r, c);
                int x = (int) (rect1.left + (rect1.width() * coordinatesOffset));
                int y = (int) (rect1.top + (rect1.height() * coordinatesOffset));
                Rect rectText = new Rect();
                paintGridCoordinates.getTextBounds(text, 0, text.length(), rectText);
                canvasGraphTree.drawText(text, x, y , paintGridCoordinates);
            }
        }

    }

    // Re-Draws the complete graph
    public void drawGraph(){

        clearCanvasGraphTree();

        // Sort Edges in reverse order, so that tree edges are drawn first
        Collections.sort(graphTree.edgePros, Collections.<EdgePro>reverseOrder());

        for(EdgePro edgePro: graphTree.edgePros){
            boolean showCurrentEdge = false;

            if(showTreeEdge && edgePro.edgeClass == EdgeClass.TREE){
                showCurrentEdge = true;
            }
            else if(showBackEdge && edgePro.edgeClass == EdgeClass.BACK){
                showCurrentEdge = true;
            }
            else if(showForwardEdge && edgePro.edgeClass == EdgeClass.FORWARD){
                showCurrentEdge = true;
            }
            else if(showCrossEdge && edgePro.edgeClass == EdgeClass.CROSS){
                showCurrentEdge = true;
            }

            if(showCurrentEdge) {
                int[] vertex1 = getCoordinates(edgePro.src);
                int[] vertex2 = getCoordinates(edgePro.des);

                Rect rect1 = getRect(vertex1[0], vertex1[1]);
                Rect rect2 = getRect(vertex2[0], vertex2[1]);

                drawEdgeGraph(rect1, rect2, edgePro, graphTree.weighted);
            }
        }

        for(Map.Entry<Integer, Pair<Integer, Integer>> entry: graphTree.vertexMap.entrySet()){
            Pair<Integer, Integer> value = entry.getValue();
            Rect rect = getRect(value.first, value.second);
            drawNodeGraph(rect, entry.getKey());
        }

        refreshGraph();
    }

    // Draws a single Node
    public void drawNodeGraph(Rect rect, int name) {
        __drawNode(canvasGraphTree, rect, name, paintVertex, paintText);
    }

    // Draws a single Node
    public void __drawNode(Canvas canvas, Rect rect, int name, Paint paintV, Paint paintT) {
        int x = rect.centerX();
        int y = rect.centerY();

        String text = String.valueOf(name);

        canvas.drawCircle(x, y, nodeRadius, paintV);

        Rect rectText = new Rect();
        paintT.getTextBounds(text, 0, text.length(), rectText);
        canvas.drawText(text, x, y - (paintT.descent() + paintT.ascent()) / 2, paintText);
    }

    // Draws a single EdgeOld
    public void drawEdgeGraph(Rect rect1, Rect rect2, EdgePro edgePro, boolean weighted) {

        switch(edgePro.edgeClass){
            case TREE:
                __drawEdge(canvasGraphTree, rect1, rect2, paintEdgeTree, paintEdgeTree);
                break;
            case BACK:
                __drawEdge(canvasGraphTree, rect1, rect2, paintEdgeBack, paintEdgeBack);
                break;
            case CROSS:
                __drawEdge(canvasGraphTree, rect1, rect2, paintEdgeCross, paintEdgeCross);
                break;
            case FORWARD:
                __drawEdge(canvasGraphTree, rect1, rect2, paintEdgeForward, paintEdgeForward);
                break;
        }

    }

    public void __drawEdge(Canvas canvas, Rect rect1, Rect rect2, Paint paintE, Paint paintEA) {
        double[] lineCoordinates = getLineCoordinates(rect1, rect2);

        float lx1 = (float) lineCoordinates[0];
        float ly1 = (float) lineCoordinates[1];

        float lx2 = (float) lineCoordinates[2];
        float ly2 = (float) lineCoordinates[3];

        canvas.drawLine(lx1, ly1, lx2, ly2, paintE);
        arrow12(lx1, ly1, lx2, ly2, canvas, paintEA);
    }

    public void arrow12(float x, float y, float x1, float y1, Canvas canvas, Paint paintEA) {
        __arrow12(canvas, x, y, x1, y1, paintEA);
    }

    private void __arrow12(Canvas canvas, float x, float y, float x1, float y1, Paint paintEA) {
        double degree = Util.calculateDegree(x, x1, y, y1);

        float endX1 = (float) (x1 + ((arrowLength) * Math.cos(Math.toRadians((degree-topAngle)+90))));
        float endY1 = (float) (y1 + ((arrowLength) * Math.sin(Math.toRadians(((degree-topAngle)+90)))));

        float endX2 = (float) (x1 + ((arrowLength) * Math.cos(Math.toRadians((degree-bottomAngle)+180))));
        float endY2 = (float) (y1 + ((arrowLength) * Math.sin(Math.toRadians(((degree-bottomAngle)+180)))));

        canvas.drawLine(x1, y1, endX1, endY1, paintEA);
        canvas.drawLine(x1, y1, endX2, endY2, paintEA);
    }

    // Adds Vertex element to grid element and calls drawNode
    public void addVertex(int row, int col, int name) {
        // Change its state and add vertex reference
        boardElements[row][col].occupied = true;
        boardElements[row][col].value = name;
    }

    public int[] getCoordinates(int key){
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(boardElements[r][c].value == key){
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

    public void refreshGraph(){
        __refresh(iv_graphtree);
    }

    private void __refresh(ImageView imageView){
        imageView.invalidate();
    }

    private void clearCanvasGraphTree(){
        canvasGraphTree.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

}
