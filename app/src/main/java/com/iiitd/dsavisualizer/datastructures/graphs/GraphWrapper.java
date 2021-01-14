package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.view.MotionEvent;

import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

public class GraphWrapper {
    public Board board;
    public Graph graph;

    public GraphWrapper(Context context, CustomCanvas customCanvas, boolean directed, boolean weighted) {
        graph = new Graph(directed, weighted);
        board = new Board(context, customCanvas);
    }

    public boolean addVertex(MotionEvent motionEvent, int nodeNumber){
        float x = (motionEvent.getX() / board.xSize);
        float y =  (motionEvent.getY() / board.ySize);

        int row = (int) y;
        int col = (int) x;

        return addVertex(row, col, nodeNumber);
    }

    public boolean addVertex(MotionEvent motionEvent){
        float x = (motionEvent.getX() / board.xSize);
        float y =  (motionEvent.getY() / board.ySize);

        int row = (int) y;
        int col = (int) x;

        int newVertexNumber = graph.getNewVertexNumber();
        return addVertex(row, col, newVertexNumber);
    }

    private boolean addVertex(int row, int col, int nodeNumber){
        if(!board.getState(row, col)){
            graph.addVertex(nodeNumber, row, col);
            board.addVertex(row, col, nodeNumber);
            update();
            return true;
        }

        return false;
    }

    public boolean removeVertex(MotionEvent motionEvent){

        float x = (motionEvent.getX() / board.xSize);
        float y =  (motionEvent.getY() / board.ySize);

        int row = (int) y;
        int col = (int) x;

        if(board.getState(row, col)){
            int nodeNumber = board.data[row][col].data;
            graph.removeVertex(nodeNumber, row, col);
            board.removeVertex(row, col);
            update();
            return true;
        }

        return false;
    }

    public boolean addEdge(int src, int des){
        return addEdge(src, des, 1);
    }

    public boolean addEdge(int src, int des, int weight){

        if(!graph.directed){
            graph.addEdge(des, src, weight);//reverse edge
        }

        graph.addEdge(src, des, weight);
        update();

        return true;
    }

    public boolean removeEdge(int src, int des){

        if(!graph.directed){
            graph.removeEdge(des, src);//reverse edge
        }

        graph.removeEdge(src, des);
        update();

        return true;
    }

    public void update(){
        board.update(graph);
    }

}
