package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Pair;
import android.widget.ImageView;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;
import com.iiitd.dsavisualizer.utility.Util;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

// Used by GraphActivity Class
public class Board {

    Context context;

    // Constants
    private final int topAngle = 45;               // in degrees
    private final int bottomAngle = 45;            // in degrees
    private final int nodeSize = 7;                // in mm
    private final float circleRatio = 0.66f;       // in ratio [0,1]
    private final float edgeArrowRatio = 0.24f;    // in ratio [0,1]
    private float nodeRadius;                // in pixels [Radius of one node]
    private float coordinatesOffset;         // in ratio [0,1]
    private int nodeTextSize;                    // in sp
    private int coordinatesTextSize;         // in sp
    private int edgeWidth;                   // in dp
    private int edgeArrowWidth;              // in dp
    private int arrowLength;                 // in pixels
    private int edgeWeightTextSize;      // in pixels
    
    // Board Variables
    public float X;                                // Width of Board
    public float Y;                                // Height of Board
    public int xCount;                             // No. of columns
    public int yCount;                             // No. of rows
    public float xSize;                            // One Column Width
    public float ySize;                            // One Row Height
    public BoardElement[][] boardElements;         // Contains data about board elements
    public int maxVertices;                        // Max No. of Vertices possible
    public CustomCanvas customCanvas;              // Custom Canvas holds all canvases
    public boolean isLargeGraph;                   //

    // Paint Variables
    private Paint paintGrid;                       // Grid
    private Paint paintGridCoordinates;            // Grid Coordinates
    private Paint paintVertex;                     // Vertex
    private Paint paintVertexText;                 // Vertex Text
    private Paint paintEdge;                       // Edge
    private Paint paintEdgeArrows;                 // Edge Arrows
    private Paint paintEdgeWeight;                 // Edge Weight
    private Paint paintVertexAnim;                 // Animation Vertex
    private Paint paintVertexTextAnim;             // Animation Vertex Text
    private Paint paintEdgeAnim;                   // Animation Edge
    private Paint paintEdgeArrowsAnim;             // Animation Edge Arrows
    private Paint paintEdgeWeightAnim;             // Animation Edge Weight


    public Board(Context context, CustomCanvas customCanvas, boolean isLargeGraph) {
        this.context = context;
        this.customCanvas = customCanvas;
        this.isLargeGraph = isLargeGraph;

        int px = (int) UtilUI.dpToPx(context, GraphSettings.getNodeSize(isLargeGraph));

        this.yCount = GraphSettings.getNoOfRows(isLargeGraph);
        this.xCount = GraphSettings.getNoOfCols(isLargeGraph);

        this.xSize = px;
        this.ySize = px;

        this.X = xCount * xSize;
        this.Y = yCount * ySize;

        this.maxVertices = yCount * xCount;

        System.out.println("----------------------------------------");
        System.out.println("Board Width(X)     = " + X      + " | " + "Board Height(Y)    = " + Y);
        System.out.println("No of Cols(xCount) = " + xCount + " | " + "No of Rows(yCount) = " + yCount);
        System.out.println("Col width(xSize)   = " + xSize  + " | " + "Row height(ySize)  = " + ySize);
        System.out.println("Max Count          = " + yCount * xCount );
        System.out.println("----------------------------------------");

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

        this.nodeTextSize = (int) UtilUI.spToPx(context, GraphSettings.getNodeTextSize(isLargeGraph));
        this.coordinatesTextSize = (int) UtilUI.spToPx(context, GraphSettings.getCoordinatesTextSize(isLargeGraph));
        this.edgeWidth = (int) UtilUI.dpToPx(context, GraphSettings.getEdgeWidth(isLargeGraph));
        this.edgeArrowWidth = (int) UtilUI.dpToPx(context, GraphSettings.getEdgeArrowWidth(isLargeGraph));
        this.edgeWeightTextSize = (int) UtilUI.spToPx(context, GraphSettings.getEdgeWeightTextSize(isLargeGraph));

        // Initializes all Paint Variables
        initPaints();

        // Draw Grid on Grid ImageView
        drawGrid();
    }

    // Initializes all Paint Variables
    private void initPaints(){
        int shade = UtilUI.getCurrentThemeColor(context, R.attr.shade);
        int light = UtilUI.getCurrentThemeColor(context, R.attr.light);
        int base = UtilUI.getCurrentThemeColor(context, R.attr.base);
        int medium = UtilUI.getCurrentThemeColor(context, R.attr.medium);
        int dark = UtilUI.getCurrentThemeColor(context, R.attr.dark);
        int white = Color.WHITE;

        // Grid Lines
        this.paintGrid = new Paint();
        this.paintGrid.setColor(light);

        // Grid Coordinates
        this.paintGridCoordinates = new Paint();
        this.paintGridCoordinates.setTextAlign(Paint.Align.RIGHT);
        this.paintGridCoordinates.setTextSize(coordinatesTextSize);
        this.paintGridCoordinates.setColor(light);

        // Vertex
        this.paintVertex = new Paint();
        this.paintVertex.setColor(base);

        // Vertex Text
        this.paintVertexText = new Paint();
        this.paintVertexText.setTextAlign(Paint.Align.CENTER);
        this.paintVertexText.setTextSize(nodeTextSize);
        this.paintVertexText.setColor(white);
        this.paintVertexText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Edge
        this.paintEdge = new Paint();
        this.paintEdge.setColor(medium);
        this.paintEdge.setStrokeWidth(edgeWidth);

        // Edge Arrows
        this.paintEdgeArrows = new Paint();
        this.paintEdgeArrows.setColor(medium);
        this.paintEdgeArrows.setStrokeWidth(edgeArrowWidth);

        // Edge Weight
        this.paintEdgeWeight = new Paint();
        this.paintEdgeWeight.setTextAlign(Paint.Align.CENTER);
        this.paintEdgeWeight.setTextSize(edgeWeightTextSize);
        this.paintEdgeWeight.setColor(medium);
        this.paintEdgeWeight.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Animation Vertex
        this.paintVertexAnim = new Paint();
        this.paintVertexAnim.setColor(dark);

        // Animation Vertex Text
        this.paintVertexTextAnim = new Paint();
        this.paintVertexTextAnim.setTextAlign(Paint.Align.CENTER);
        this.paintVertexTextAnim.setTextSize(nodeTextSize);
        this.paintVertexTextAnim.setColor(white);
        this.paintVertexTextAnim.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Animation Edge
        this.paintEdgeAnim = new Paint();
        this.paintEdgeAnim.setColor(dark);
        this.paintEdgeAnim.setStrokeWidth(edgeWidth);

        // Animation Edge Arrows
        this.paintEdgeArrowsAnim = new Paint();
        this.paintEdgeArrowsAnim.setColor(dark);
        this.paintEdgeArrowsAnim.setStrokeWidth(edgeArrowWidth);

        // Animation Edge Weight
        this.paintEdgeWeightAnim = new Paint();
        this.paintEdgeWeightAnim.setTextAlign(Paint.Align.CENTER);
        this.paintEdgeWeightAnim.setTextSize(edgeWeightTextSize);
        this.paintEdgeWeightAnim.setColor(dark);
        this.paintEdgeWeightAnim.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    // Draw the complete Grid
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
                int x = (int) (rect1.left + (rect1.width() * coordinatesOffset));
                int y = (int) (rect1.top + (rect1.height() * coordinatesOffset));
                Rect rectText = new Rect();
                paintGridCoordinates.getTextBounds(text, 0, text.length(), rectText);
                customCanvas.canvasCoordinates.drawText(text, x, y , paintGridCoordinates);
            }
        }
    }

    // Re-Draws the Complete Graph
    public void update(Graph graph){
        System.out.println("RE-DRAWING CANVAS");

        // Clears Canvas
        clearGraph(false);

        // Vertices
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(boardElements[r][c].occupied){
                    drawVertex(boardElements[r][c].value, false);
                }
            }
        }

        // Edges
        for(Map.Entry<Integer, ArrayList<Edge>> vertex : graph.edgeListMap.entrySet() ){
            for (Edge edge : vertex.getValue()) {
                drawEdge(edge, graph.directed, graph.weighted, false);
            }
        }

        refresh(false);
    }

    // Draws a Vertex
    public void drawVertex(int vertexValue, boolean isAnim){
        // Canvas and Paint Variables
        Canvas canvas;
        Paint pVertex;
        Paint pVertexText;

        if(isAnim){
            canvas = customCanvas.canvasAnimation;
            pVertex = paintVertexAnim;
            pVertexText = paintVertexTextAnim;
        }
        else{
            canvas = customCanvas.canvasGraph;
            pVertex = paintVertex;
            pVertexText = paintVertexText;
        }

        Rect rect = getRect(vertexValue);
        int x = rect.centerX();
        int y = rect.centerY();

        String text = String.valueOf(vertexValue);

        canvas.drawCircle(x, y, nodeRadius, pVertex);

        Rect rectText = new Rect();
        pVertexText.getTextBounds(text, 0, text.length(), rectText);
        canvas.drawText(text, x, y - (pVertexText.descent() + pVertexText.ascent()) / 2, pVertexText);
    }

    // Draws an Edge
    public void drawEdge(Edge edge, boolean isDirected, boolean isWeighted, boolean isAnim){
        // Canvas and Paint Variables
        Canvas canvas;
        Paint pEdge;
        Paint pEdgeArrow;
        Paint pEdgeWeight;

        if(isAnim){
            canvas = customCanvas.canvasAnimation;
            pEdge = paintEdgeAnim;
            pEdgeArrow = paintEdgeArrowsAnim;
            pEdgeWeight = paintEdgeWeightAnim;
        }
        else{
            canvas = customCanvas.canvasGraph;
            pEdge = paintEdge;
            pEdgeArrow = paintEdgeArrows;
            pEdgeWeight = paintEdgeWeight;
        }

        // Math Variables
        Rect srcRect = getRect(edge.src);
        Rect desRect = getRect(edge.des);

        double[] lineCoordinates = getLineCoordinates(srcRect, desRect);

        float lx1 = (float) lineCoordinates[0];
        float ly1 = (float) lineCoordinates[1];
        float lx2 = (float) lineCoordinates[2];
        float ly2 = (float) lineCoordinates[3];

        double degree = Util.getAngle(lx1, ly1, lx2, ly2);
        System.out.println(edge.src + " -> " + edge.des + " = " + degree + " | " + edge.isFirstEdge);

        float x = lx1 + (lx2 - lx1)/2;
        float y = ly1 + (ly2 - ly1)/2;

        // Directed Graph
        if(isDirected){
            if(isWeighted){
//                canvas.save();
//                canvas.rotate((float) degree, x, y);
//                canvas.drawText(String.valueOf(edge.weight), x, y-20, pEdgeWeight);
//                canvas.restore();

                drawEdgeWeight(canvas, pEdgeWeight, edge.weight, (float) degree, lineCoordinates, false);
            }

            canvas.drawLine(lx1, ly1, lx2, ly2, pEdge);
            drawArrow(lx1, ly1, lx2, ly2, canvas, pEdgeArrow);
        }
        // Undirected Graph
        else{
            // If firstEdge, then do it same as directed, except drawing arrows
            if (edge.isFirstEdge) {
                if (isWeighted) {
//                    canvas.save();
//                    canvas.rotate((float) degree, x, y);
//                    canvas.drawText(String.valueOf(edge.weight), x, y - 20, pEdgeWeight);
//                    canvas.restore();
                    drawEdgeWeight(canvas, pEdgeWeight, edge.weight, (float) degree, lineCoordinates, false);
                }

                canvas.drawLine(lx1, ly1, lx2, ly2, pEdge);
            }
            // If !first edge && is for animation then reverse the edge and recalculate values
            else if(isAnim){
//                degree = Util.getAngle(lx2, ly2, lx1, ly1);

                x = lx2 + (lx1 - lx2)/2;
                y = ly2 + (ly1 - ly2)/2;

                if (isWeighted) {
//                    canvas.save();
//                    canvas.rotate((float) degree, x, y);
//                    canvas.drawText(String.valueOf(edge.weight), x, y - 20, pEdgeWeight);
//                    canvas.restore();
                    drawEdgeWeight(canvas, pEdgeWeight, edge.weight, (float) degree, lineCoordinates, true);
                }

                canvas.drawLine(lx2, ly2, lx1, ly1, pEdge);
            }
        }

    }

    public double getAngleBetweenVertices(int src, int des){
        Rect srcRect = getRect(src);
        Rect desRect = getRect(des);

        double[] lineCoordinates = getLineCoordinates(srcRect, desRect);

        float lx1 = (float) lineCoordinates[0];
        float ly1 = (float) lineCoordinates[1];
        float lx2 = (float) lineCoordinates[2];
        float ly2 = (float) lineCoordinates[3];

        double degree = Util.getAngle(lx1, ly1, lx2, ly2);

        return degree;
    }

//    private void drawEdgeWeight(Canvas canvas, Paint paint, int weight, float degree, float lx1, float ly1, float lx2, float ly2 ){
    private void drawEdgeWeight(Canvas canvas, Paint paint, int weight, float degree, double[] lineCoordinates, boolean reverseEdge){
        float lx1 = (float) lineCoordinates[0];
        float ly1 = (float) lineCoordinates[1];
        float lx2 = (float) lineCoordinates[2];
        float ly2 = (float) lineCoordinates[3];

        float x = lx1 + (lx2 - lx1)/2;
        float y = ly1 + (ly2 - ly1)/2;

        if( (degree > 315 && degree <=360) || ( degree >= 0 && degree <= 45)){
            if(degree == 0 || degree == 360)
                degree = 0;
            y = y-20;
        }
        else if(degree > 45 && degree <= 135){
            if(degree == 90)
                degree = 0;
            x = x+20;
        }
        else if(degree > 135 && degree <= 225){
            if(degree == 180)
                degree = 0;
            y = y+20;
        }
        else if(degree > 225 && degree <= 315){
            if(degree == 270)
                degree = 0;
            x = x-20;
        }

        canvas.save();
        canvas.rotate(degree, x, y);
        canvas.drawText(String.valueOf(weight), x, y, paint);
        canvas.restore();
    }

    // Draws arrow lines for src -> des Edge
    public void drawArrow(float x, float y, float x1, float y1, Canvas canvas, Paint paint) {
        double degree = Util.calculateDegree(x, x1, y, y1);

        float endX1 = (float) (x1 + ((arrowLength) * Math.cos(Math.toRadians((degree-topAngle)+90))));
        float endY1 = (float) (y1 + ((arrowLength) * Math.sin(Math.toRadians(((degree-topAngle)+90)))));

        float endX2 = (float) (x1 + ((arrowLength) * Math.cos(Math.toRadians((degree-bottomAngle)+180))));
        float endY2 = (float) (y1 + ((arrowLength) * Math.sin(Math.toRadians(((degree-bottomAngle)+180)))));

        canvas.drawLine(x1, y1, endX1, endY1, paint);
        canvas.drawLine(x1, y1, endX2, endY2, paint);
    }

    // Adds a Vertex and calls drawVertex [prevents re-drawing the complete graph]
    public void addVertex(int row, int col, int name) {
        boardElements[row][col].occupied = true;
        boardElements[row][col].value = name;

        drawVertex(boardElements[row][col].value, false);
    }

    // Draws a Vertex
    public void drawVertexWeight(int vertexValue, int vertexWeight, boolean isAnim){
        // Canvas and Paint Variables
        Canvas canvas;
        Paint pVertexWeightText;

        if(isAnim){
            canvas = customCanvas.canvasAnimation;
            pVertexWeightText = paintEdgeWeightAnim;
        }
        else{
            canvas = customCanvas.canvasGraph;
            pVertexWeightText = paintEdgeWeight;
        }

        Rect rect = getRect(vertexValue);
        int x = rect.centerX();
        int y = rect.top - 10;

        String text = String.valueOf(vertexWeight);
        if(vertexWeight == Integer.MAX_VALUE)
            text = DecimalFormatSymbols.getInstance().getInfinity();

        System.out.println(text);
        Rect rectText = new Rect();
        pVertexWeightText.getTextBounds(text, 0, text.length(), rectText);
        canvas.drawText(text, x, y - (pVertexWeightText.descent() + pVertexWeightText.ascent()) / 2, pVertexWeightText);

        //Highlighted box
        System.out.println(rectText);
        Paint paint =new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(rectText, paint);
    }

    // Removes a Vertex
    public void removeVertex(int row, int col) {
        boardElements[row][col].occupied = false;
        boardElements[row][col].value = -1;
    }

    // Returns state of the grid element, whether it is being used or not
    public boolean getState(int row, int col){
        return boardElements[row][col].occupied;
    }

    // Returns coordinates for the given key, if key is not present null is returned
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
    public Rect getRect(int row, int col) {
        int left = (int) (col * xSize);
        int top = (int) (row * ySize);
        int right = (int) (left + xSize);
        int bottom = (int) (top + ySize);

        return new Rect(left, top, right, bottom);
    }

    // Returns Rect for given key value in graph's board, NPE if key is not present in graph
    public Rect getRect(int key) {
        int[] coordinates = getCoordinates(key);
        return getRect(coordinates[0], coordinates[1]);
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

    // Returns a Random empty grid element slot for a vertex
    public Pair<Integer, Integer> getRandomAvailableNode(){
        ArrayList<Pair<Integer, Integer>> available = new ArrayList<>();
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(!boardElements[r][c].occupied){
                    available.add(new Pair<>(r, c));
                }
            }
        }

        if(available.size() == 0)
            return null;

        return available.get(new Random().nextInt(available.size()));
    }

    // Resets the graph
    public void reset(Graph graph){
        this.boardElements = new BoardElement[yCount][xCount];
        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                boardElements[r][c] = new BoardElement();
            }
        }

        update(graph);
    }

    // Clears the graph and calls refresh
    public void clearGraph(boolean isAnim){
        Canvas canvas;

        if(isAnim){
            canvas = customCanvas.canvasAnimation;
        }
        else{
            canvas = customCanvas.canvasGraph;
        }

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        refresh(isAnim);
    }

    // Refreshes the graph by calling invalidate()
    public void refresh(boolean isAnim){
        ImageView imageView;

        if(isAnim){
            imageView = customCanvas.imageViewAnimation;
        }
        else{
            imageView = customCanvas.imageViewGraph;
        }

        imageView.invalidate();
    }

    /*
    Below functions not used currently, DEBUGGING STAGE
    */
    public int[] getCurrentLimits(){
        int row_min = yCount-1;
        int row_max = 0;
        int col_min = xCount-1;
        int col_max = 0;

        for (int r = 0; r < yCount; r++) {
            for (int c = 0; c < xCount; c++) {
                if(boardElements[r][c].occupied){
                    row_min = Math.min(row_min, r);
                    row_max = Math.max(row_max, r);

                    col_min = Math.min(col_min, c);
                    col_max = Math.max(col_max, c);
                }
            }
        }

        return new int[]{row_min, row_max, col_min, col_max};
    }

    public float getZoomRatio(){
        int[] currentLimits = getCurrentLimits();

        int width = currentLimits[3] - currentLimits[2] + 1;
        int height = currentLimits[1] - currentLimits[0] + 1;

        System.out.println("row_min = " + currentLimits[0]);
        System.out.println("row_max = " + currentLimits[1]);
        System.out.println("col_min = " + currentLimits[2]);
        System.out.println("col_max = " + currentLimits[3]);


        System.out.println("width = " + width);
        System.out.println("height = " + height);


        float widthRatio = (float) xCount / width;
        float heightRatio = (float) yCount / height;

        System.out.println("&&" + widthRatio);
        System.out.println("&&" + heightRatio);

        System.out.println("zoom" + Math.min(widthRatio,  heightRatio));

        return Math.min(widthRatio,  heightRatio);
    }

    public Rect getZoomCentre(){
        int[] currentLimits = getCurrentLimits();

        int width = currentLimits[3] - currentLimits[2] + 1;
        int height = currentLimits[1] - currentLimits[0] + 1;

        int widthMid = currentLimits[2] + width/2;
        int heightMid = currentLimits[0] + height /2;

        System.out.println("widthMid = " + widthMid);
        System.out.println("heightMid = " + heightMid);

        Rect rect = getRect(widthMid, heightMid);
        System.out.println(rect.centerX());
        System.out.println(rect.centerY());

        return rect;
    }

}
