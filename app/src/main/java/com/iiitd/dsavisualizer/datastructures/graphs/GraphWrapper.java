package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.util.Pair;
import android.view.MotionEvent;

import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class GraphWrapper {
    public boolean directed;
    public boolean weighted;
    public Board board;
    public Graph graph;

    private final int MAX_BOUNDS = 1000;

    public GraphWrapper(Context context, CustomCanvas customCanvas, boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
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
            boolean addVertex = graph.addVertex(nodeNumber, row, col);
            if(addVertex){
                board.addVertex(row, col, nodeNumber);
                update();
                return true;
            }

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

        if(graph.checkContainsVertices(src, des)){
            if(!graph.directed){
                graph.addEdge(des, src, weight);//reverse edge
            }

            graph.addEdge(src, des, weight);

            update();

            return true;
        }

        return false;
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

    public boolean customInput(ArrayList<Vertex> vertices, ArrayList<Edge> edges){

        // Reset the graph
        reset();

        int xCount = board.xCount;
        int yCount = board.yCount;

        int vertexCount = vertices.size();
        if(vertexCount > board.maxVertices){
            return false;
        }

        for(Vertex vertex : vertices){
            if(vertex.row == -1 || vertex.col == -1) {
                Pair<Integer, Integer> randomAvailableNode = board.getRandomAvailableNode();
                vertex.row = randomAvailableNode.first;
                vertex.col = randomAvailableNode.second;
            }
            else if(vertex.row > yCount || vertex.col > xCount){
                Pair<Integer, Integer> randomAvailableNode = board.getRandomAvailableNode();
                vertex.row = randomAvailableNode.first;
                vertex.col = randomAvailableNode.second;
            }

            addVertex(vertex.row, vertex.col, vertex.data);
        }

        for(Edge edge : edges){
            addEdge(edge.src, edge.des, edge.weight);
        }

        update();

        //check distances
        double score = board.score(graph);
        System.out.println("Score = " + score);


        return true;

    }

    public void reset(){
        this.graph = new Graph(directed, weighted);
        board.reset(graph);
    }

    public void minimizeGraph(ArrayList<Vertex> vertices){

        int maxX = -1;
        int maxY = -1;

        for(Vertex vertex : vertices){
            maxX = Math.max(maxX, vertex.col);
            maxY = Math.max(maxY, vertex.row);
        }


        // compress
        int[] row = new int[maxY+1];
        int[] col = new int[maxX+1];

        for(Vertex pair : vertices){
            if(pair.row != -1 && pair.col != -1) {
                row[pair.row] = 1;
                col[pair.col] = 1;
            }
        }

        //rows
        {
            int gap = 0;
            for (int i = row.length-1; i >= 0; i--) {
                if (row[i] == 0) {
                    gap++;
                } else {
                    if (gap > 0) {
                        for(Vertex vertex : vertices){
                            if(vertex.row != -1 && vertex.row > i)
                                vertex.row -= gap;
                        }
                    }

                    gap = 0;
                }
            }
        }

        //cols
        {
            int gap = 0;
            for (int i = col.length - 1; i >= 0; i--) {
                if (col[i] == 0) {
                    gap++;
                } else {
                    if (gap > 0) {
                        for(Vertex vertex : vertices){
                            if(vertex.col != -1 && vertex.col > i){
                                vertex.col -= gap;
                            }
                        }
                    }

                    gap = 0;
                }
            }
        }

    }

    public boolean checkBounds(ArrayList<Vertex> data){

        if(data.size() == 0)
            return true;

        int xCount = board.xCount;
        int yCount = board.yCount;

        int maxX = -1;
        int maxY = -1;

        boolean boundCheckedOnce = false;

        for(Vertex vertex : data){
            if(vertex.row != -1 && vertex.col != -1) {
                maxX = Math.max(maxX, vertex.col);
                maxY = Math.max(maxY, vertex.row);

                boundCheckedOnce = true;
            }
        }

        if(boundCheckedOnce)
            return maxX < xCount && maxY < yCount;
        else
            return true;

    }

    public boolean checkPerformanceBounds(ArrayList<Vertex> data){

        if(data.size() == 0)
            return false;

        int maxX = -1;
        int maxY = -1;


        for(Vertex vertex : data){
            if(vertex.row != -1 && vertex.col != -1) {
                maxX = Math.max(maxX, vertex.col);
                maxY = Math.max(maxY, vertex.row);

            }
        }

        return !(maxX < 1000 && maxY < 1000);
    }

    public boolean checkIfMinimizable(ArrayList<Vertex> data) {

        if (data.size() == 0)
            return true;

        HashSet<Integer> rows = new HashSet<>();
        HashSet<Integer> cols = new HashSet<>();


        int xCount = board.xCount;
        int yCount = board.yCount;

        for (Vertex vertex : data) {
            if(vertex.row != -1 && vertex.col != -1) {
                rows.add(vertex.row);
                cols.add(vertex.col);
            }
        }

        //  yCount = rows, xCount = cols
        return rows.size() < yCount && cols.size() < xCount;
    }

}
